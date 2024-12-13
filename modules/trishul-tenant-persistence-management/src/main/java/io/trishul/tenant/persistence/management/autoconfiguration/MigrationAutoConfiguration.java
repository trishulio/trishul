package io.trishul.tenant.persistence.management.autoconfiguration;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.trishul.base.types.util.random.RandomGenerator;
import io.trishul.data.datasource.configuration.model.DataSourceConfiguration;
import io.trishul.data.datasource.configuration.provider.DataSourceConfigurationProvider;
import io.trishul.data.datasource.manager.DataSourceManager;
import io.trishul.data.datasource.query.runner.DataSourceQueryRunner;
import io.trishul.dialect.JdbcDialect;
import io.trishul.model.util.random.RandomGeneratorImpl;
import io.trishul.secrets.SecretsManager;
import io.trishul.tenant.persistence.datasource.configuration.provider.TenantDataSourceConfigurationProvider;
import io.trishul.tenant.persistence.datasource.manager.TenantDataSourceManager;
import io.trishul.tenant.persistence.management.migration.manager.MigrationManager;
import io.trishul.tenant.persistence.management.migration.manager.SequentialMigrationManager;
import io.trishul.tenant.persistence.management.migration.register.FlywayTenantMigrationRegister;
import io.trishul.tenant.persistence.management.migration.register.MigrationRegister;
import io.trishul.tenant.persistence.management.migration.register.TenantRegister;
import io.trishul.tenant.persistence.management.migration.register.TenantSchemaRegister;
import io.trishul.tenant.persistence.management.migration.register.TenantUserRegister;
import io.trishul.tenant.persistence.management.migration.register.UnifiedTenantRegister;

@Configuration
public class MigrationAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(RandomGenerator.class)
    public RandomGenerator randomGenerator() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstanceStrong();
        return new RandomGeneratorImpl(random);
    }

    @Bean
    @ConditionalOnMissingBean(DataSourceQueryRunner.class)
    public DataSourceQueryRunner dsQueryRunner(DataSourceManager dsManager) {
        return new DataSourceQueryRunner(dsManager);
    }

    @Bean
    @ConditionalOnMissingBean(TenantRegister.class)
    public TenantRegister tenantRegister(DataSourceQueryRunner dsQueryRunner, DataSourceConfigurationProvider<UUID> tenantDsConfigProvider, DataSourceConfiguration adminDsConfig, SecretsManager<String, String> secretMgr, JdbcDialect dialect, RandomGenerator randomGen) {
        TenantUserRegister userReg = new TenantUserRegister(dsQueryRunner, tenantDsConfigProvider, adminDsConfig, secretMgr, dialect, randomGen)(dsQueryRunner, (TenantDataSourceConfigurationProvider) tenantDsConfigProvider, adminDsConfig, secretMgr, dialect, randomGen);
        TenantSchemaRegister schemaReg = new TenantSchemaRegister((TenantDataSourceConfigurationProvider) tenantDsConfigProvider, dsQueryRunner, dialect);

        return new UnifiedTenantRegister(userReg, schemaReg);
    }

    @Bean
    @ConditionalOnMissingBean(MigrationManager.class)
    public MigrationManager migrationMgr(TenantRegister tenantRegister, MigrationRegister migrationReg) {
        return new SequentialMigrationManager(tenantRegister, migrationReg);
    }

    @Bean
    @ConditionalOnMissingBean(MigrationRegister.class)
    public MigrationRegister migrationReg(TenantDataSourceManager dsMgr, DataSourceConfigurationProvider<UUID> tenantDsConfigProvider) {
        return new FlywayTenantMigrationRegister(dsMgr, tenantDsConfigProvider);
    }
}
