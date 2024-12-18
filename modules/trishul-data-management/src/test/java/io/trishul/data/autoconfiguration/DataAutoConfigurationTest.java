package io.trishul.data.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.trishul.data.datasource.builder.HikariDataSourceBuilder;
import io.trishul.data.datasource.configuration.builder.DataSourceBuilder;
import io.trishul.data.datasource.manager.CachingDataSourceManager;
import io.trishul.data.datasource.manager.DataSourceManager;
import io.trishul.tenant.persistence.datasource.manager.TenantDataSourceManager;
import io.trishul.tenant.persistence.datasource.manager.TenantDataSourceManagerWrapper;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
