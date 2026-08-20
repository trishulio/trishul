package sh.trishul.tenant.persistence.management.migration.register;

import java.sql.Connection;
import java.util.UUID;
import sh.trishul.base.types.util.random.RandomGenerator;
import sh.trishul.data.datasource.configuration.model.DataSourceConfiguration;
import sh.trishul.data.datasource.configuration.provider.DataSourceConfigurationProvider;
import sh.trishul.data.datasource.query.runner.DataSourceQueryRunner;
import sh.trishul.dialect.JdbcDialect;
import sh.trishul.secrets.SecretsManager;
import sh.trishul.tenant.entity.TenantData;
import sh.trishul.tenant.persistence.datasource.configuration.provider.TenantDataSourceConfigurationProvider;

public class TenantUserRegister implements TenantRegister {
  public static final int PASSWORD_LENGTH = 48;

  private final DataSourceQueryRunner runner;
  private final DataSourceConfigurationProvider<UUID> configMgr;
  private final DataSourceConfiguration adminDataSourceConfiguration;
  private final SecretsManager<String, String> secretMgr;
  private final JdbcDialect dialect;
  private final RandomGenerator randGen;

  public TenantUserRegister(DataSourceQueryRunner dataSourceQueryRunner,
      TenantDataSourceConfigurationProvider tenantDsConfigProvider,
      DataSourceConfiguration adminDataSourceConfiguration,
      SecretsManager<String, String> secretMgr, JdbcDialect dialect, RandomGenerator randGen) {
    this.runner = dataSourceQueryRunner;
    this.configMgr = tenantDsConfigProvider;
    this.adminDataSourceConfiguration = adminDataSourceConfiguration;
    this.secretMgr = secretMgr;
    this.dialect = dialect;
    this.randGen = randGen;
  }

  @Override
  public void add(TenantData tenant) {
    DataSourceConfiguration config = this.configMgr.getConfiguration(tenant.getId());

    runner.query(conn -> {
      String password = this.randGen.string(PASSWORD_LENGTH);

      dialect.createUser(conn, config.getUserName(), password);
      dialect.grantPrivilege(conn, "CONNECT", "DATABASE", config.getDbName(), config.getUserName());
      dialect.grantPrivilege(conn, "CREATE", "DATABASE", config.getDbName(), config.getUserName());

      // Note: The reason schemaName is used instead of the tenant-id (as convention)
      // is because fully-qualified schema-name is unique per tenant. Since we're
      // using a global app-wide secret's manager that may store other secrets, there
      // may be another secret under the tenant-id key that we may override.
      // If the key here changes, also change the retrieval logic in the
      // LazyTenantDataSourceConfiguration's getPassword method.
      secretMgr.put(config.getSchemaName(), password);
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
    DataSourceConfiguration config = this.configMgr.getConfiguration(tenant.getId());

    runner.query(conn -> {
      dialect.reassignOwnedByTo(conn, config.getUserName(),
          adminDataSourceConfiguration.getUserName());
      dialect.dropOwnedBy(conn, config.getUserName());
      dialect.dropUser(conn, config.getUserName());
      conn.commit();

      secretMgr.remove(config.getSchemaName());
    });
  }

  @Override
  public boolean exists(TenantData tenant) {
    DataSourceConfiguration config = this.configMgr.getConfiguration(tenant.getId());

    return runner.query((Connection conn) -> dialect.userExists(conn, config.getUserName()));
  }
}
