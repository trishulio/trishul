package sh.trishul.tenant.persistence.management.migration.register;

import java.util.UUID;
import sh.trishul.data.datasource.configuration.model.DataSourceConfiguration;
import sh.trishul.data.datasource.configuration.provider.DataSourceConfigurationProvider;
import sh.trishul.data.datasource.query.runner.DataSourceQueryRunner;
import sh.trishul.dialect.JdbcDialect;
import sh.trishul.tenant.entity.TenantData;
import sh.trishul.tenant.persistence.datasource.configuration.provider.TenantDataSourceConfigurationProvider;

public class TenantSchemaRegister implements TenantRegister {
  private final DataSourceConfigurationProvider<UUID> configProvider;
  private final DataSourceQueryRunner runner;
  private final JdbcDialect dialect;

  public TenantSchemaRegister(TenantDataSourceConfigurationProvider configProvider,
      DataSourceQueryRunner queryRunner, JdbcDialect dialect) {
    this.configProvider = configProvider;
    this.runner = queryRunner;
    this.dialect = dialect;
  }

  @Override
  public void add(TenantData tenant) {
    DataSourceConfiguration config = configProvider.getConfiguration(tenant.getId());

    runner.query(config, conn -> {
      dialect.createSchemaIfNotExists(conn, config.getSchemaName());
      conn.commit();
    });
  }

  @Override
  public void put(TenantData tenant) {
    if (!exists(tenant)) {
      add(tenant);
    }
  }

  @Override
  public void remove(TenantData tenant) {
    DataSourceConfiguration config = configProvider.getConfiguration(tenant.getId());

    runner.query(config, conn -> {
      dialect.dropSchema(conn, config.getSchemaName());
      conn.commit();
    });
  }

  @Override
  public boolean exists(TenantData tenant) {
    DataSourceConfiguration config = configProvider.getConfiguration(tenant.getId());

    return runner.query(config, conn -> {
      return dialect.schemaExists(conn, config.getSchemaName());
    });
  }
}
