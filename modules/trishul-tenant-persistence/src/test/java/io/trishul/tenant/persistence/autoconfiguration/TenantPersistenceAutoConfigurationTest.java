package io.trishul.tenant.persistence.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import io.trishul.data.datasource.manager.DataSourceManager;
import io.trishul.tenant.persistence.connection.provider.pool.TenantConnectionProviderPool;
import io.trishul.tenant.persistence.resolver.TenantIdentifierResolver;
import jakarta.persistence.EntityManagerFactory;
import java.util.Map;
import javax.sql.DataSource;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

public class TenantPersistenceAutoConfigurationTest {
  private LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBeanMock;

  private EntityManagerFactory mEntityManagerFactory;

  private JpaVendorAdapter jpaVendorAdapterMock;

  private DataSourceManager dataSourceManageMock;

  private MultiTenantConnectionProvider<String> multiTenantConnectionProviderMock;

  private CurrentTenantIdentifierResolver<String> currentTenantIdentifierResolverMock;

  private DataSource dataSourceMock;

  private TenantPersistenceAutoConfiguration hibernateAutoConfiguration;

  @BeforeEach
  public void init() {
    localContainerEntityManagerFactoryBeanMock = mock(LocalContainerEntityManagerFactoryBean.class);
    mEntityManagerFactory = mock(EntityManagerFactory.class);
    jpaVendorAdapterMock = mock(HibernateJpaVendorAdapter.class);
    dataSourceManageMock = mock(DataSourceManager.class);
    multiTenantConnectionProviderMock = mock(TenantConnectionProviderPool.class);
    currentTenantIdentifierResolverMock = mock(TenantIdentifierResolver.class);
    dataSourceMock = mock(DataSource.class);

    hibernateAutoConfiguration = new TenantPersistenceAutoConfiguration();
  }

  @Test
  public void testMultiTenantConnectionProvider_ReturnsInstanceOfTenantConnectionProvider() {
    MultiTenantConnectionProvider<String> multiTenantConnectionProvider
        = hibernateAutoConfiguration.multiTenantConnectionProvider(null, null);

    assertTrue(multiTenantConnectionProvider instanceof TenantConnectionProviderPool);
  }

  @Test
  public void testCurrentTenantIdentifierResolver_ReturnsTenantIdentifierResolver() {
    CurrentTenantIdentifierResolver<String> currentTenantIdentifierResolver
        = hibernateAutoConfiguration.currentTenantIdentifierResolver(null);

    assertTrue(currentTenantIdentifierResolver instanceof TenantIdentifierResolver);
  }

  @Test
  public void testPlatformTransactionManager_ReturnsJpaTransactionManager() {
    when(localContainerEntityManagerFactoryBeanMock.getObject())
        .thenReturn(mEntityManagerFactory);

    PlatformTransactionManager platformTransactionManager
        = hibernateAutoConfiguration.platformTransactionManager(localContainerEntityManagerFactoryBeanMock);

    assertTrue(platformTransactionManager instanceof JpaTransactionManager);
  }

  @Test
  public void testJpaVendorAdapter_ReturnsHibernateJpaVendorAdapter() {
    JpaVendorAdapter jpaVendorAdapter = hibernateAutoConfiguration.jpaVendorAdapter();

    assertTrue(jpaVendorAdapter instanceof HibernateJpaVendorAdapter);
  }

  @Test
  public void testLocalContainerEntityManagerFactoryBean() {
    when(dataSourceManageMock.getAdminDataSource()).thenReturn(dataSourceMock);

    LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean
        = hibernateAutoConfiguration.localContainerEntityManagerFactoryBean(jpaVendorAdapterMock,
            dataSourceManageMock, multiTenantConnectionProviderMock,
            currentTenantIdentifierResolverMock);

    assertSame(dataSourceMock, localContainerEntityManagerFactoryBean.getDataSource());
    assertSame(jpaVendorAdapterMock, localContainerEntityManagerFactoryBean.getJpaVendorAdapter());

    Map<String, Object> jpaPropertyMap = localContainerEntityManagerFactoryBean.getJpaPropertyMap();
    assertEquals(3, jpaPropertyMap.size());
    assertEquals("org.hibernate.dialect.PostgreSQLDialect",
        jpaPropertyMap.get(Environment.DIALECT));
    assertEquals(multiTenantConnectionProviderMock,
        jpaPropertyMap.get(Environment.MULTI_TENANT_CONNECTION_PROVIDER));
    assertEquals(currentTenantIdentifierResolverMock,
        jpaPropertyMap.get(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER));
  }
}
