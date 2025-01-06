package io.trishul.tenant.persistence.management.migration.register;

import io.trishul.data.datasource.configuration.model.DataSourceConfiguration;
import io.trishul.data.datasource.configuration.provider.DataSourceConfigurationProvider;
import io.trishul.data.datasource.query.runner.DataSourceQueryRunner;
import io.trishul.dialect.JdbcDialect;
import io.trishul.tenant.entity.TenantData;
import io.trishul.tenant.persistence.datasource.configuration.provider.TenantDataSourceConfigurationProvider;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TenantSchemaRegister implements TenantRegister {
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(TenantSchemaRegister.class);

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
