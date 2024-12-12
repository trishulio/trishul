package io.company.brewcraft;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.company.brewcraft.data.CachingDataSourceManager;
import io.company.brewcraft.data.DataAutoConfiguration;
import io.company.brewcraft.data.DataSourceBuilder;
import io.company.brewcraft.data.DataSourceManager;
import io.company.brewcraft.data.HikariDataSourceBuilder;
import io.company.brewcraft.data.TenantDataSourceManager;
import io.company.brewcraft.data.TenantDataSourceManagerWrapper;

public class DataAutoConfigurationTest {
    private DataAutoConfiguration config;

    @BeforeEach
    public void init() {
        config = new DataAutoConfiguration();
    }

    @Test
    public void testDataSourceManager_ReturnsSchemaBasedDataSourceManager() {
        DataSourceManager mgr = config.dataSourceManager(null, null);
        assertTrue(mgr instanceof CachingDataSourceManager);
    }

    @Test
    public void testDsBuilder_ReturnsRoutingDataSourceBuilder() {
        DataSourceBuilder builder = config.dsBuilder();
        assertTrue(builder instanceof HikariDataSourceBuilder);
    }

    @Test
    public void testTenantDsManager_ReturnsContextHolderDsManager() {
        TenantDataSourceManager mgr = config.tenantDsManager(null, null);
        assertTrue(mgr instanceof TenantDataSourceManagerWrapper);
    }

    @Test
    public void testJdbcTemplate_ReturnsJdbcTemplate() {
        DataSourceManager dataSourceManagerMock = Mockito.mock(CachingDataSourceManager.class);
        DataSource dataSourceMock = Mockito.mock(DataSource.class);

        Mockito.when(dataSourceManagerMock.getAdminDataSource()).thenReturn(dataSourceMock);

        config.jdbcTemplate(dataSourceManagerMock);
    }

    @Test
    public void testTransactionTemplate_ReturnsTransactionTemplate() {
        DataSourceManager dataSourceManagerMock = Mockito.mock(CachingDataSourceManager.class);
        DataSource dataSourceMock = Mockito.mock(DataSource.class);

        Mockito.when(dataSourceManagerMock.getAdminDataSource()).thenReturn(dataSourceMock);

        config.transactionTemplate(dataSourceManagerMock);
    }
}
