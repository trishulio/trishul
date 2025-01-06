// TODO: Figure out if this is a tenant module class or data module class
package io.trishul.tenant.persistence.autoconfiguration;

import io.trishul.data.datasource.manager.DataSourceManager;
import io.trishul.tenant.entity.TenantIdProvider;
import io.trishul.tenant.persistence.connection.provider.pool.TenantConnectionProviderPool;
import io.trishul.tenant.persistence.datasource.manager.TenantDataSourceManager;
import io.trishul.tenant.persistence.resolver.TenantIdentifierResolver;
import jakarta.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
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

  @Bean
  @ConditionalOnMissingBean(LocalContainerEntityManagerFactoryBean.class)
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(
      JpaVendorAdapter jpaVendorAdapter, DataSourceManager dataSourceManager,
      MultiTenantConnectionProvider<String> multiTenantConnectionProvider,
      CurrentTenantIdentifierResolver<String> currentTenantIdentifierResolver) {
    LocalContainerEntityManagerFactoryBean entityManagerFactory
        = new LocalContainerEntityManagerFactoryBean();
    entityManagerFactory.setDataSource(dataSourceManager.getAdminDataSource());
    entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
    entityManagerFactory.setPackagesToScan("io.company.brewcraft.model"); // TODO: need to be
                                                                          // integrated with some
    // interface
    // that an application will implement

    Map<String, Object> jpaProperties = new HashMap<>();
    jpaProperties.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
    jpaProperties.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
    jpaProperties.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER,
        currentTenantIdentifierResolver);

    entityManagerFactory.setJpaPropertyMap(jpaProperties);
    return entityManagerFactory;
  }

  @Bean
  @ConditionalOnMissingBean(PlatformTransactionManager.class)
  public PlatformTransactionManager transactionManager(
      LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
    EntityManagerFactory entityManagerFactory = entityManagerFactoryBean.getObject();
    if (entityManagerFactory == null) {
      throw new IllegalStateException("EntityManagerFactory returned null");
    }

    PlatformTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory);

    return transactionManager;
  }
}