package sh.trishul.tenant.persistence.management.autoconfiguration;

import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sh.trishul.base.types.util.random.RandomGenerator;
import sh.trishul.data.datasource.configuration.model.DataSourceConfiguration;
import sh.trishul.data.datasource.configuration.provider.DataSourceConfigurationProvider;
import sh.trishul.data.datasource.manager.DataSourceManager;
import sh.trishul.data.datasource.query.runner.DataSourceQueryRunner;
import sh.trishul.dialect.JdbcDialect;
import sh.trishul.model.util.random.RandomGeneratorImpl;
import sh.trishul.secrets.SecretsManager;
import sh.trishul.tenant.entity.AdminTenant;
import sh.trishul.tenant.entity.TenantData;
import sh.trishul.tenant.persistence.datasource.configuration.provider.TenantDataSourceConfigurationProvider;
import sh.trishul.tenant.persistence.datasource.manager.TenantDataSourceManager;
import sh.trishul.tenant.persistence.management.migration.manager.MigrationManager;
import sh.trishul.tenant.persistence.management.migration.manager.SequentialMigrationManager;
import sh.trishul.tenant.persistence.management.migration.register.FlywayTenantMigrationRegister;
import sh.trishul.tenant.persistence.management.migration.register.MigrationRegister;
import sh.trishul.tenant.persistence.management.migration.register.TenantRegister;
import sh.trishul.tenant.persistence.management.migration.register.TenantSchemaRegister;
import sh.trishul.tenant.persistence.management.migration.register.TenantUserRegister;
import sh.trishul.tenant.persistence.management.migration.register.UnifiedTenantRegister;

@Configuration
public class TenantPersistenceManagementAutoConfiguration {
  @Bean
  @ConditionalOnMissingBean(TenantData.class)
  public TenantData adminTenant(@Value("${app.config.tenant.admin.id}") String id,
      @Value("${app.config.tenant.admin.name}") String name) {
    UUID adminId = UUID.fromString(id);

    return new AdminTenant(adminId, name, URI.create("http://localhost/"));
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
