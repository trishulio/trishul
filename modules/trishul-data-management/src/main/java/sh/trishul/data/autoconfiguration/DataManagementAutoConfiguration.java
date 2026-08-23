package sh.trishul.data.autoconfiguration;

import java.net.URI;
import java.util.UUID;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import sh.trishul.data.datasource.configuration.manager.DataSourceConfigurationManager;
import sh.trishul.data.datasource.configuration.model.DataSourceConfiguration;
import sh.trishul.data.datasource.configuration.model.GlobalDataSourceConfiguration;
import sh.trishul.data.datasource.configuration.model.ImmutableGlobalDataSourceConfiguration;
import sh.trishul.data.datasource.configuration.model.LazyTenantDataSourceConfiguration;
import sh.trishul.data.datasource.configuration.model.MigrationConfiguration;
import sh.trishul.data.datasource.configuration.provider.DataSourceConfigurationProvider;
import sh.trishul.data.datasource.manager.CachingDataSourceManager;
import sh.trishul.data.datasource.manager.DataSourceManager;
import sh.trishul.dialect.JdbcDialect;
import sh.trishul.dialect.postgres.PostgresJdbcDialect;
import sh.trishul.dialect.postgres.PostgresJdbcDialectSql;
import sh.trishul.secrets.SecretsManager;
import sh.trishul.tenant.entity.TenantData;
import sh.trishul.tenant.persistence.datasource.configuration.provider.TenantDataSourceConfigurationProvider;
import sh.trishul.tenant.persistence.datasource.manager.TenantDataSourceManager;
import sh.trishul.tenant.persistence.datasource.manager.TenantDataSourceManagerWrapper;

@Configuration
public class DataManagementAutoConfiguration {
  @Bean
  @ConditionalOnMissingBean(DataSourceConfigurationManager.class)
  public DataSourceConfigurationManager dataSourceConfigurationManager() {
    return new DataSourceConfigurationManager();
  }

  @Bean
  @Qualifier("adminDs")
  @ConditionalOnMissingBean(DataSourceConfiguration.class)
  public DataSourceConfiguration adminDataSourceConfiguration(
      @Value("${spring.datasource.url}") String jdbcUrl,
      @Value("${spring.datasource.username}") String username,
      @Value("${spring.datasource.password}") String password,
      @Value("${app.config.ds.db-name}") String dbName,
      @Value("${app.config.tenant.admin.ds.schema.prefix}") String schemaPrefix,
      @Value("${app.config.tenant.admin.ds.schema.migration.configs}") String schemaMigrationScriptConfigsStr,
      @Value("${spring.datasource.hikari.maximumPoolSize}") int poolSize,
      @Value("${spring.datasource.hikari.auto-commit}") boolean autoCommit,
      SecretsManager<String, String> secretsManager,
      DataSourceConfigurationManager dataSourceConfigurationManager, TenantData adminTenant) {
    URI uri = URI.create(jdbcUrl);
    MigrationConfiguration[] migrationConfigs
        = MigrationConfiguration.from(schemaMigrationScriptConfigsStr);
    GlobalDataSourceConfiguration globalConfig = new ImmutableGlobalDataSourceConfiguration(uri,
        dbName, migrationConfigs, schemaPrefix, poolSize, autoCommit);
    String fqName = dataSourceConfigurationManager.getFqName(schemaPrefix, adminTenant.getId());

    return new LazyTenantDataSourceConfiguration(username, password, fqName, globalConfig);
  }

  @Bean
  @ConditionalOnMissingBean(DataSourceConfigurationProvider.class)
  public DataSourceConfigurationProvider<UUID> tenantDsConfigProvider(
      DataSourceConfiguration adminDataSourceConfiguration, TenantData adminTenant,
      DataSourceConfigurationManager dsConfigMgr, SecretsManager<String, String> secretsManager,
      @Value("${spring.datasource.url}") String jdbcUrl,
      @Value("${app.config.ds.db-name}") String dbName,
      @Value("${app.config.tenant.ds.schema.prefix}") String schemaPrefix,
      @Value("${app.config.tenant.ds.schema.migration.configs}") String schemaMigrationScriptConfigsStr,
      @Value("${app.config.tenant.ds.pool.size}") int poolSize,
      @Value("${app.config.tenant.ds.db.auto-commit}") boolean autoCommit) {
    String cleanedUrl = jdbcUrl.replaceAll("[?&]currentSchema=[^&]*", "");
    if (cleanedUrl.contains("?&")) {
      cleanedUrl = cleanedUrl.replace("?&", "?");
    }
    if (cleanedUrl.endsWith("?")) {
      cleanedUrl = cleanedUrl.substring(0, cleanedUrl.length() - 1);
    }
    URI uri = URI.create(cleanedUrl);
    MigrationConfiguration[] migrationConfigs
        = MigrationConfiguration.from(schemaMigrationScriptConfigsStr);
    GlobalDataSourceConfiguration globalTenantDsConfig = new ImmutableGlobalDataSourceConfiguration(
        uri, dbName, migrationConfigs, schemaPrefix, poolSize, autoCommit);

    return new TenantDataSourceConfigurationProvider(adminDataSourceConfiguration, adminTenant,
        globalTenantDsConfig, dsConfigMgr, secretsManager);
  }

  @Bean
  @ConditionalOnMissingBean(JdbcDialect.class)
  public JdbcDialect jdbcDialect() {
    PostgresJdbcDialectSql sql = new PostgresJdbcDialectSql();
    return new PostgresJdbcDialect(sql);
  }

  @Bean
  @ConditionalOnMissingBean(DataSourceManager.class)
  public DataSourceManager dataSourceManager(DataSource adminDs) {
    return new CachingDataSourceManager(adminDs);
  }

  @Bean
  @ConditionalOnMissingBean(TenantDataSourceManager.class)
  public TenantDataSourceManager tenantDataSourceManager(DataSourceManager dataSourceManager,
      DataSourceConfigurationProvider<UUID> tenantDsConfigProvider, TenantData adminTenant) {
    TenantDataSourceManager mgr = new TenantDataSourceManagerWrapper(dataSourceManager,
        (TenantDataSourceConfigurationProvider) tenantDsConfigProvider, adminTenant.getId());
    return mgr;
  }

  @Bean
  @ConditionalOnMissingBean(JdbcTemplate.class)
  public JdbcTemplate jdbcTemplate(DataSourceManager dataSourceManager) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceManager.getAdminDataSource());
    return jdbcTemplate;
  }

  @Bean
  @ConditionalOnMissingBean(TransactionTemplate.class)
  public TransactionTemplate transactionTemplate(DataSourceManager dataSourceManager) {
    PlatformTransactionManager platformTransactionManager
        = new DataSourceTransactionManager(dataSourceManager.getAdminDataSource());
    TransactionTemplate transactionTemplate = new TransactionTemplate(platformTransactionManager);
    return transactionTemplate;
  }
}
