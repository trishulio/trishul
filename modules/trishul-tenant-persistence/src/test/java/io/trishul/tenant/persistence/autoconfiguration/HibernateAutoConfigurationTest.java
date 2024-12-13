package io.trishul.tenant.persistence.autoconfiguration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.MultiTenancyStrategy;
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

import io.trishul.data.datasource.manager.DataSourceManager;
import io.trishul.tenant.entity.Tenant;
import io.trishul.tenant.persistence.connection.provider.pool.TenantConnectionProviderPool;
import io.trishul.tenant.persistence.resolver.TenantIdentifierResolver;

public class HibernateAutoConfigurationTest {
    private LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBeanMock;

    private EntityManagerFactory entityManagerFactoryMock;

    private JpaVendorAdapter jpaVendorAdapterMock;

    private DataSourceManager dataSourceManageMock;

    private MultiTenantConnectionProvider multiTenantConnectionProviderMock;

    private CurrentTenantIdentifierResolver currentTenantIdentifierResolverMock;

    private DataSource dataSourceMock;

    private HibernateAutoConfiguration hibernateAutoConfiguration;

    @BeforeEach
    public void init() {
        localContainerEntityManagerFactoryBeanMock = mock(LocalContainerEntityManagerFactoryBean.class);
        entityManagerFactoryMock = mock(EntityManagerFactory.class);
        jpaVendorAdapterMock = mock(HibernateJpaVendorAdapter.class);
        dataSourceManageMock = mock(DataSourceManager.class);
        multiTenantConnectionProviderMock = mock(TenantConnectionProviderPool.class);
        currentTenantIdentifierResolverMock = mock(TenantIdentifierResolver.class);
        dataSourceMock = mock(DataSource.class);

        hibernateAutoConfiguration = new HibernateAutoConfiguration();
    }

    @Test
    public void testMultiTenantConnectionProvider_ReturnsInstanceOfTenantConnectionProvider() {
        MultiTenantConnectionProvider multiTenantConnectionProvider = hibernateAutoConfiguration.multiTenantConnectionProvider(null, null);

        assertTrue(multiTenantConnectionProvider instanceof TenantConnectionProviderPool);
    }

    @Test
    public void testCurrentTenantIdentifierResolver_ReturnsTenantIdentifierResolver() {
        CurrentTenantIdentifierResolver currentTenantIdentifierResolver = hibernateAutoConfiguration.currentTenantIdentifierResolver(null);

        assertTrue(currentTenantIdentifierResolver instanceof TenantIdentifierResolver);
    }

    @Test
    public void testPlatformTransactionManager_ReturnsJpaTransactionManager() {
        when(localContainerEntityManagerFactoryBeanMock.getObject()).thenReturn(entityManagerFactoryMock);

        PlatformTransactionManager platformTransactionManager = hibernateAutoConfiguration.transactionManager(localContainerEntityManagerFactoryBeanMock);

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

        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = hibernateAutoConfiguration.entityManagerFactory(jpaVendorAdapterMock, dataSourceManageMock, multiTenantConnectionProviderMock, currentTenantIdentifierResolverMock);

        assertSame(dataSourceMock, localContainerEntityManagerFactoryBean.getDataSource());
        assertSame(jpaVendorAdapterMock, localContainerEntityManagerFactoryBean.getJpaVendorAdapter());

        Map<String, Object> jpaPropertyMap = localContainerEntityManagerFactoryBean.getJpaPropertyMap();
        assertEquals(4, jpaPropertyMap.size());
        assertEquals(MultiTenancyStrategy.SCHEMA.toString(), jpaPropertyMap.get(Environment.MULTI_TENANT));
        assertEquals("org.hibernate.dialect.PostgreSQLDialect", jpaPropertyMap.get(Environment.DIALECT));
        assertEquals(multiTenantConnectionProviderMock, jpaPropertyMap.get(Environment.MULTI_TENANT_CONNECTION_PROVIDER));
        assertEquals(currentTenantIdentifierResolverMock, jpaPropertyMap.get(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER));
    }
}
