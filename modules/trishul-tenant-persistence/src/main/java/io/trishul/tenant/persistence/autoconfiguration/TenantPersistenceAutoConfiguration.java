// TODO: Figure out if this is a tenant module class or data module class
package io.trishul.tenant.persistence.autoconfiguration;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import io.trishul.data.datasource.manager.DataSourceManager;
import io.trishul.tenant.entity.TenantIdProvider;
import io.trishul.tenant.persistence.config.PackageScanConfig;
import io.trishul.tenant.persistence.connection.provider.pool.TenantConnectionProviderPool;
import io.trishul.tenant.persistence.datasource.manager.TenantDataSourceManager;
import io.trishul.tenant.persistence.resolver.TenantIdentifierResolver;
import jakarta.persistence.EntityManagerFactory;

@Configuration
@AutoConfigureAfter({DataSourceAutoConfiguration.class})
@EnableTransactionManagement
public class TenantPersistenceAutoConfiguration {
  @Bean
  @ConditionalOnMissingBean(MultiTenantConnectionProvider.class)
  public MultiTenantConnectionProvider<String> multiTenantConnectionProvider(
      TenantDataSourceManager tenantDataSourceManager, DataSource adminDataSource) {
    MultiTenantConnectionProvider<String> multiTenantConnectionProvider
        = new TenantConnectionProviderPool(tenantDataSourceManager, adminDataSource);
    return multiTenantConnectionProvider;
  }

  @Bean
  @ConditionalOnMissingBean(CurrentTenantIdentifierResolver.class)
  public CurrentTenantIdentifierResolver<String> currentTenantIdentifierResolver(
      TenantIdProvider tenantIdProvider) {
    CurrentTenantIdentifierResolver<String> currentTenantIdentifierResolver
        = new TenantIdentifierResolver(tenantIdProvider);
    return currentTenantIdentifierResolver;
  }

  @Bean
  @ConditionalOnMissingBean(JpaVendorAdapter.class)
  public JpaVendorAdapter jpaVendorAdapter() {
    return new HibernateJpaVendorAdapter();
  }

  @Bean(name = "entityManagerFactory") // entityManagerFactory name is required for Spring
  @ConditionalOnMissingBean(LocalContainerEntityManagerFactoryBean.class)
  public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(
      JpaVendorAdapter jpaVendorAdapter, DataSourceManager dataSourceManager,
      MultiTenantConnectionProvider<String> multiTenantConnectionProvider,
      CurrentTenantIdentifierResolver<String> currentTenantIdentifierResolver,
      PackageScanConfig packageScanConfig) {
    LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean
        = new LocalContainerEntityManagerFactoryBean();
    localContainerEntityManagerFactoryBean.setDataSource(dataSourceManager.getAdminDataSource());
    localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
    localContainerEntityManagerFactoryBean.setPackagesToScan(
        ArrayUtils.add(packageScanConfig.getEntityPackagesToScan(), "io.trishul"));

    Map<String, Object> jpaProperties = new HashMap<>();
    jpaProperties.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
    jpaProperties.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
    jpaProperties.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER,
        currentTenantIdentifierResolver);

    localContainerEntityManagerFactoryBean.setJpaPropertyMap(jpaProperties);
    return localContainerEntityManagerFactoryBean;
  }

  @Bean(name = "transactionManager") // transactionManager name is required for Spring
  @ConditionalOnMissingBean(PlatformTransactionManager.class)
  public PlatformTransactionManager platformTransactionManager(
      LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBeanBean) {
    EntityManagerFactory localContainerEntityManagerFactoryBean
        = localContainerEntityManagerFactoryBeanBean.getObject();
    if (localContainerEntityManagerFactoryBean == null) {
      throw new IllegalStateException("EntityManagerFactory returned null");
    }

    PlatformTransactionManager platformTransactionManager
        = new JpaTransactionManager(localContainerEntityManagerFactoryBean);

    return platformTransactionManager;
  }
}
