package io.trishul.data.autoconfiguration;

import io.trishul.data.datasource.builder.HikariDataSourceBuilder;
import io.trishul.data.datasource.configuration.builder.DataSourceBuilder;
import io.trishul.data.datasource.configuration.manager.DataSourceConfigurationManager;
import io.trishul.data.datasource.configuration.model.DataSourceConfiguration;
import io.trishul.data.datasource.configuration.model.GlobalDataSourceConfiguration;
import io.trishul.data.datasource.configuration.model.ImmutableGlobalDataSourceConfiguration;
import io.trishul.data.datasource.configuration.model.LazyTenantDataSourceConfiguration;
import io.trishul.data.datasource.configuration.provider.DataSourceConfigurationProvider;
import io.trishul.data.datasource.manager.CachingDataSourceManager;
import io.trishul.data.datasource.manager.DataSourceManager;
import io.trishul.dialect.JdbcDialect;
import io.trishul.dialect.postgres.PostgresJdbcDialect;
import io.trishul.dialect.postgres.PostgresJdbcDialectSql;
import io.trishul.secrets.SecretsManager;
import io.trishul.tenant.entity.Tenant;
import io.trishul.tenant.persistence.datasource.configuration.provider.TenantDataSourceConfigurationProvider;
import io.trishul.tenant.persistence.datasource.manager.TenantDataSourceManager;
import io.trishul.tenant.persistence.datasource.manager.TenantDataSourceManagerWrapper;
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
import io.trishul.tenant.entity.TenantData;

@Configuration
public class DataAutoConfiguration {
  @Bean
  @ConditionalOnMissingBean(DataSourceConfigurationManager.class)
  public DataSourceConfigurationManager dataSourceConfigurationManager() {
    return new DataSourceConfigurationManager();
  }

  @Bean
  @Qualifier("adminDs")
  @ConditionalOnMissingBean(DataSourceConfiguration.class)
  public DataSourceConfiguration adminDs(@Value("${spring.datasource.url}") String jdbcUrl,
      @Value("${app.config.ds.db-name}") String dbName,
      @Value("${app.config.tenant.admin.ds.schema.prefix}") String schemaPrefix,
      @Value("${app.config.tenant.admin.ds.schema.migration}") String schemaMigrationScriptPath,
      @Value("${spring.datasource.hikari.maximumPoolSize}") int poolSize,
      @Value("${spring.datasource.hikari.auto-commit}") boolean autoCommit,
      SecretsManager<String, String> secretsMgr,
      DataSourceConfigurationManager dataSourceConfigurationManager, TenantData adminTenant) {
    URI uri = URI.create(jdbcUrl);
    GlobalDataSourceConfiguration globalConfig = new ImmutableGlobalDataSourceConfiguration(uri,
        dbName, schemaMigrationScriptPath, schemaPrefix, poolSize, autoCommit);
    String fqName = dataSourceConfigurationManager.getFqName(schemaPrefix, adminTenant.getId());

    return new LazyTenantDataSourceConfiguration(fqName, globalConfig, secretsMgr);
  }

  @Bean
  @ConditionalOnMissingBean(DataSourceConfigurationProvider.class)
  public DataSourceConfigurationProvider<UUID> tenantDsConfigProvider(
      DataSourceConfiguration adminDsConfig, TenantData adminTenant,
      DataSourceConfigurationManager dsConfigMgr, SecretsManager<String, String> secretsManager,
      @Value("${spring.datasource.url}") String jdbcUrl,
      @Value("${app.config.ds.db-name}") String dbName,
      @Value("${app.config.tenant.ds.schema.prefix}") String schemaPrefix,
      @Value("${app.config.tenant.ds.schema.migration}") String schemaMigrationScriptPath,
      @Value("${app.config.tenant.ds.pool.size}") int poolSize,
      @Value("${app.config.tenant.ds.db.auto-commit}") boolean autoCommit) {
    URI uri = URI.create(jdbcUrl);
    GlobalDataSourceConfiguration globalTenantDsConfig = new ImmutableGlobalDataSourceConfiguration(
        uri, dbName, schemaMigrationScriptPath, schemaPrefix, poolSize, autoCommit);

    return new TenantDataSourceConfigurationProvider(adminDsConfig, adminTenant,
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
  public DataSourceManager dataSourceManager(DataSource adminDs, DataSourceBuilder dsBuilder) {
    DataSourceManager mgr = new CachingDataSourceManager(adminDs, dsBuilder);
    return mgr;
  }

  @Bean
  @ConditionalOnMissingBean(DataSourceBuilder.class)
  public DataSourceBuilder dsBuilder() {
    DataSourceBuilder builder = new HikariDataSourceBuilder();
    return builder;
  }

  @Bean
  @ConditionalOnMissingBean(TenantDataSourceManager.class)
  public TenantDataSourceManager tenantDsManager(DataSourceManager dataSourceManager,
      DataSourceConfigurationProvider<UUID> tenantDsConfigProvider) {
    TenantDataSourceManager mgr = new TenantDataSourceManagerWrapper(dataSourceManager,
        (TenantDataSourceConfigurationProvider) tenantDsConfigProvider);
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
    PlatformTransactionManager transactionManager
        = new DataSourceTransactionManager(dataSourceManager.getAdminDataSource());
    TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
    return transactionTemplate;
  }
}
