package io.trishul.tenant.persistence.management.migration.register;

import io.trishul.data.datasource.configuration.model.DataSourceConfiguration;
import io.trishul.data.datasource.configuration.provider.DataSourceConfigurationProvider;
import io.trishul.tenant.entity.Tenant;
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

    public FlywayTenantMigrationRegister(
            TenantDataSourceManager dsMgr,
            DataSourceConfigurationProvider<UUID> dataSourceConfigProvider) {
        this(() -> Flyway.configure(), dsMgr, dataSourceConfigProvider);
    }

    protected FlywayTenantMigrationRegister(
            FluentConfigProvider provider,
            TenantDataSourceManager dsMgr,
            DataSourceConfigurationProvider<UUID> dataSourceConfigProvider) {
        this.provider = provider;
        this.dsMgr = dsMgr;
        this.dataSourceConfigProvider = dataSourceConfigProvider;
    }

    @Override
    public void migrate(Tenant tenant) {
        DataSourceConfiguration config =
                this.dataSourceConfigProvider.getConfiguration(tenant.getId());

        try {
            Flyway fw =
                    provider.config()
                            .locations(config.getMigrationScriptPath())
                            .schemas(config.getSchemaName())
                            .dataSource(dsMgr.getDataSource(tenant.getId()))
                            .load();
            fw.migrate();
        } catch (SQLException | IOException e) {
            log.error("Failed to get the data-source for tenant: {}", tenant.getId());
            throw new RuntimeException(e);

        } catch (FlywayException e) {
            log.error("Failed to migrate tenant: {}", tenant.getId());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isMigrated(Tenant tenant) {
        DataSourceConfiguration config =
                this.dataSourceConfigProvider.getConfiguration(tenant.getId());

        try {
            Flyway fw =
                    provider.config()
                            .locations(config.getMigrationScriptPath())
                            .schemas(config.getSchemaName())
                            .dataSource(dsMgr.getDataSource(tenant.getId()))
                            .load();

            return fw.info().applied().length == fw.info().all().length;

        } catch (SQLException | IOException e) {
            log.error("Failed to get the data-source for tenant: {}", tenant.getId());
            throw new RuntimeException(e);
        }
    }
}

interface FluentConfigProvider {
    FluentConfiguration config();
}
