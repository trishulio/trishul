package io.trishul.tenant.persistence.management.migration.register;

import io.trishul.data.datasource.configuration.model.DataSourceConfiguration;
import io.trishul.data.datasource.configuration.model.MigrationConfiguration;
import io.trishul.data.datasource.configuration.provider.DataSourceConfigurationProvider;
import io.trishul.tenant.entity.TenantData;
import io.trishul.tenant.persistence.datasource.manager.TenantDataSourceManager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlywayTenantMigrationRegister implements MigrationRegister {
  private static final Logger log = LoggerFactory.getLogger(FlywayTenantMigrationRegister.class);

  private final FluentConfigProvider provider;
  private final TenantDataSourceManager dsMgr;
  private final DataSourceConfigurationProvider<UUID> dataSourceConfigProvider;

  public FlywayTenantMigrationRegister(TenantDataSourceManager dsMgr,
      DataSourceConfigurationProvider<UUID> dataSourceConfigProvider) {
    this(() -> Flyway.configure(), dsMgr, dataSourceConfigProvider);
  }

  protected FlywayTenantMigrationRegister(FluentConfigProvider provider,
      TenantDataSourceManager dsMgr,
      DataSourceConfigurationProvider<UUID> dataSourceConfigProvider) {
    this.provider = provider;
    this.dsMgr = dsMgr;
    this.dataSourceConfigProvider = dataSourceConfigProvider;
  }

  @Override
  public void migrate(TenantData tenant) {
    DataSourceConfiguration config = this.dataSourceConfigProvider.getConfiguration(tenant.getId());

    for (MigrationConfiguration migrationConfig : config.getMigrationConfigurations()) {
      try {
        FluentConfiguration flywayConfig = provider.config().schemas(config.getSchemaName())
            .baselineOnMigrate(true).dataSource(dsMgr.getDataSource(tenant.getId()))
            .locations(migrationConfig.getMigrationScriptPath());

        String schemaHistoryTableName = migrationConfig.getMigrationHistoryTableName();
        if (schemaHistoryTableName != null) {
          flywayConfig.table(schemaHistoryTableName);
        }

        log.debug("Trying the migration config: scriptPath={}, historyTable={}, tenantId={}",
            flywayConfig.getLocations()[0], flywayConfig.getTable(), tenant.getId());

        Flyway fw = flywayConfig.load();
        fw.migrate();
      } catch (SQLException | IOException e) {
        log.error("Failed to get the data-source for tenant: {}", tenant.getId());
        throw new RuntimeException(e);

      } catch (FlywayException e) {
        log.error("Failed to migrate tenant: {}", tenant.getId());
        throw new RuntimeException(e);
      }
    }
  }
}


interface FluentConfigProvider {
  FluentConfiguration config();
}
