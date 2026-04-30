package io.trishul.data.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;
import io.trishul.data.datasource.builder.HikariDataSourceBuilder;
import io.trishul.data.datasource.configuration.builder.DataSourceBuilder;
import io.trishul.data.datasource.configuration.manager.DataSourceConfigurationManager;
import io.trishul.data.datasource.manager.CachingDataSourceManager;
import io.trishul.data.datasource.manager.DataSourceManager;
import io.trishul.dialect.JdbcDialect;
import io.trishul.dialect.postgres.PostgresJdbcDialect;
import io.trishul.tenant.persistence.datasource.manager.TenantDataSourceManager;
import io.trishul.tenant.persistence.datasource.manager.TenantDataSourceManagerWrapper;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DataManagementAutoConfigurationTest {
  private DataManagementAutoConfiguration config;

  @BeforeEach
  void init() {
    config = new DataManagementAutoConfiguration();
  }

  @Test
  void testDataSourceConfigurationManager_ReturnsNonNullInstance() {
    DataSourceConfigurationManager mgr = config.dataSourceConfigurationManager();
    assertNotNull(mgr);
  }

  @Test
  void testDataSourceConfigurationManager_ReturnsInstanceOfDataSourceConfigurationManager() {
    DataSourceConfigurationManager mgr = config.dataSourceConfigurationManager();
    assertTrue(mgr instanceof DataSourceConfigurationManager);
  }

  @Test
  void testJdbcDialect_ReturnsNonNullInstance() {
    JdbcDialect dialect = config.jdbcDialect();
    assertNotNull(dialect);
  }

  @Test
  void testJdbcDialect_ReturnsInstanceOfPostgresJdbcDialect() {
    JdbcDialect dialect = config.jdbcDialect();
    assertTrue(dialect instanceof PostgresJdbcDialect);
  }

  @Test
  void testDataSourceManager_ReturnsSchemaBasedDataSourceManager() {
    DataSourceManager mgr = config.dataSourceManager(null, null);
    assertTrue(mgr instanceof CachingDataSourceManager);
  }

  @Test
  void testDataSourceManager_ReturnsNonNullInstance() {
    DataSourceManager mgr = config.dataSourceManager(null, null);
    assertNotNull(mgr);
  }

  @Test
  void testDsBuilder_ReturnsRoutingDataSourceBuilder() {
    DataSourceBuilder builder = config.dataSourceBuilder();
    assertTrue(builder instanceof HikariDataSourceBuilder);
  }

  @Test
  void testDsBuilder_ReturnsNonNullInstance() {
    DataSourceBuilder builder = config.dataSourceBuilder();
    assertNotNull(builder);
  }

  @Test
  void testTenantDsManager_ReturnsContextHolderDsManager() {
    TenantDataSourceManager mgr = config.tenantDataSourceManager(null, null);
    assertTrue(mgr instanceof TenantDataSourceManagerWrapper);
  }

  @Test
  void testTenantDsManager_ReturnsNonNullInstance() {
    TenantDataSourceManager mgr = config.tenantDataSourceManager(null, null);
    assertNotNull(mgr);
  }

  @Test
  void testJdbcTemplate_ReturnsJdbcTemplate() {
    DataSourceManager dataSourceManagerMock = Mockito.mock(CachingDataSourceManager.class);
    DataSource dataSourceMock = Mockito.mock(DataSource.class);

    Mockito.when(dataSourceManagerMock.getAdminDataSource()).thenReturn(dataSourceMock);

    JdbcTemplate result = config.jdbcTemplate(dataSourceManagerMock);
    assertNotNull(result, "JdbcTemplate should not be null");
  }

  @Test
  void testJdbcTemplate_ReturnsInstanceOfJdbcTemplate() {
    DataSourceManager dataSourceManagerMock = Mockito.mock(CachingDataSourceManager.class);
    DataSource dataSourceMock = Mockito.mock(DataSource.class);

    Mockito.when(dataSourceManagerMock.getAdminDataSource()).thenReturn(dataSourceMock);

    JdbcTemplate result = config.jdbcTemplate(dataSourceManagerMock);
    assertTrue(result instanceof JdbcTemplate);
  }

  @Test
  void testTransactionTemplate_ReturnsTransactionTemplate() {
    DataSourceManager dataSourceManagerMock = Mockito.mock(CachingDataSourceManager.class);
    DataSource dataSourceMock = Mockito.mock(DataSource.class);

    Mockito.when(dataSourceManagerMock.getAdminDataSource()).thenReturn(dataSourceMock);

    TransactionTemplate result = config.transactionTemplate(dataSourceManagerMock);
    assertNotNull(result, "TransactionTemplate should not be null");
  }

  @Test
  void testTransactionTemplate_ReturnsInstanceOfTransactionTemplate() {
    DataSourceManager dataSourceManagerMock = Mockito.mock(CachingDataSourceManager.class);
    DataSource dataSourceMock = Mockito.mock(DataSource.class);

    Mockito.when(dataSourceManagerMock.getAdminDataSource()).thenReturn(dataSourceMock);

    TransactionTemplate result = config.transactionTemplate(dataSourceManagerMock);
    assertTrue(result instanceof TransactionTemplate);
  }
}
