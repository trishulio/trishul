package io.trishul.tenant.persistence.management.autoconfiguration;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
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
import io.trishul.tenant.entity.AdminTenant;
import io.trishul.tenant.entity.TenantData;
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
public class TenantPersistenceManagementAutoConfiguration {
  @Bean
  @ConditionalOnMissingBean(TenantData.class)
  public TenantData adminTenant(@Value("${app.config.tenant.admin.id}") String id,
      @Value("${app.config.tenant.admin.name}") String name) throws MalformedURLException {
    UUID adminId = UUID.fromString(id);

    return new AdminTenant(adminId, name, new URL("http://localhost/"));
  }

  @Bean
  @ConditionalOnMissingBean(RandomGenerator.class)
  public RandomGenerator randomGenerator() throws NoSuchAlgorithmException {
    SecureRandom random = SecureRandom.getInstanceStrong();
    return new RandomGeneratorImpl(random);
  }

  @Bean
  @ConditionalOnMissingBean(DataSourceQueryRunner.class)
  public DataSourceQueryRunner dataSourceQueryRunner(DataSourceManager dsManager) {
    return new DataSourceQueryRunner(dsManager);
  }

  @Bean
  @ConditionalOnMissingBean(TenantRegister.class)
  public TenantRegister tenantRegister(DataSourceQueryRunner dataSourceQueryRunner,
      DataSourceConfigurationProvider<UUID> tenantDsConfigProvider,
      DataSourceConfiguration adminDataSourceConfiguration,
      SecretsManager<String, String> secretMgr, JdbcDialect dialect, RandomGenerator randomGen) {
    TenantUserRegister userReg = new TenantUserRegister(dataSourceQueryRunner,
        (TenantDataSourceConfigurationProvider) tenantDsConfigProvider,
        adminDataSourceConfiguration, secretMgr, dialect, randomGen);
    TenantSchemaRegister schemaReg
        = new TenantSchemaRegister((TenantDataSourceConfigurationProvider) tenantDsConfigProvider,
            dataSourceQueryRunner, dialect);

    return new UnifiedTenantRegister(userReg, schemaReg);
  }

  @Bean
  @ConditionalOnMissingBean(MigrationManager.class)
  public MigrationManager migrationManager(TenantRegister tenantRegister,
      MigrationRegister migrationRegister) {
    return new SequentialMigrationManager(tenantRegister, migrationRegister);
  }

  @Bean
  @ConditionalOnMissingBean(MigrationRegister.class)
  public MigrationRegister migrationRegister(TenantDataSourceManager dsMgr,
      DataSourceConfigurationProvider<UUID> tenantDsConfigProvider) {
    return new FlywayTenantMigrationRegister(dsMgr, tenantDsConfigProvider);
  }
}
