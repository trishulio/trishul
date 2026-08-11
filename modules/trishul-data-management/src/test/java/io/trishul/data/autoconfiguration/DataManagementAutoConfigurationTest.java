package io.trishul.data.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.trishul.data.datasource.configuration.manager.DataSourceConfigurationManager;
import io.trishul.data.datasource.configuration.model.DataSourceConfiguration;
import io.trishul.data.datasource.configuration.provider.DataSourceConfigurationProvider;
import io.trishul.data.datasource.manager.CachingDataSourceManager;
import io.trishul.data.datasource.manager.DataSourceManager;
import io.trishul.dialect.JdbcDialect;
import io.trishul.dialect.postgres.PostgresJdbcDialect;
import io.trishul.secrets.SecretsManager;
import io.trishul.tenant.entity.TenantData;
import io.trishul.tenant.persistence.datasource.manager.TenantDataSourceManager;
import io.trishul.tenant.persistence.datasource.manager.TenantDataSourceManagerWrapper;
import java.util.UUID;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

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
    DataSourceManager mgr = config.dataSourceManager(null);
    assertTrue(mgr instanceof CachingDataSourceManager);
  }

  @Test
  void testDataSourceManager_ReturnsNonNullInstance() {
    DataSourceManager mgr = config.dataSourceManager(null);
    assertNotNull(mgr);
  }

  @Test
  void testTenantDsManager_ReturnsContextHolderDsManager() {
    TenantData adminTenant = Mockito.mock(TenantData.class);
    Mockito.when(adminTenant.getId()).thenReturn(UUID.randomUUID());
    TenantDataSourceManager mgr = config.tenantDataSourceManager(null, null, adminTenant);
    assertTrue(mgr instanceof TenantDataSourceManagerWrapper);
  }

  @Test
  void testTenantDsManager_ReturnsNonNullInstance() {
    TenantData adminTenant = Mockito.mock(TenantData.class);
    Mockito.when(adminTenant.getId()).thenReturn(UUID.randomUUID());
    TenantDataSourceManager mgr = config.tenantDataSourceManager(null, null, adminTenant);
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

  @Test
  void testAdminDataSourceConfiguration_ReturnsNonNullInstance() {
    SecretsManager<String, String> secretsManager = Mockito.mock(SecretsManager.class);
    DataSourceConfigurationManager dsConfigMgr = Mockito.mock(DataSourceConfigurationManager.class);
    TenantData adminTenant = Mockito.mock(TenantData.class);
    UUID adminTenantId = UUID.randomUUID();
    Mockito.when(adminTenant.getId()).thenReturn(adminTenantId);
    Mockito.when(dsConfigMgr.getFqName("prefix", adminTenantId)).thenReturn("fqName");

    DataSourceConfiguration result
        = config.adminDataSourceConfiguration("jdbc:postgresql://localhost:5432/db", "user", "pass",
            "db", "prefix", "script:script", 10, true, secretsManager, dsConfigMgr, adminTenant);

    assertNotNull(result);
  }

  @Test
  void testTenantDsConfigProvider_ReturnsNonNullInstance() {
    DataSourceConfiguration adminConfig = Mockito.mock(DataSourceConfiguration.class);
    TenantData adminTenant = Mockito.mock(TenantData.class);
    DataSourceConfigurationManager dsConfigMgr = Mockito.mock(DataSourceConfigurationManager.class);
    SecretsManager<String, String> secretsManager = Mockito.mock(SecretsManager.class);

    DataSourceConfigurationProvider<UUID> result
        = config.tenantDsConfigProvider(adminConfig, adminTenant, dsConfigMgr, secretsManager,
            "jdbc:postgresql://localhost:5432/db", "db", "prefix", "script:script", 10, true);

    assertNotNull(result);
  }

  @Test
  void testTenantDsConfigProvider_CleansCurrentSchemaFromJdbcUrl() {
    DataSourceConfiguration adminConfig = Mockito.mock(DataSourceConfiguration.class);
    TenantData adminTenant = Mockito.mock(TenantData.class);
    Mockito.when(adminTenant.getId())
        .thenReturn(UUID.fromString("00000000-0000-0000-0000-000000000000"));

    DataSourceConfigurationManager dsConfigMgr = Mockito.mock(DataSourceConfigurationManager.class);
    Mockito.when(dsConfigMgr.getFqName(any(), any())).thenReturn("fqName");

    SecretsManager<String, String> secretsManager = Mockito.mock(SecretsManager.class);

    DataSourceConfigurationProvider<UUID> provider = config.tenantDsConfigProvider(adminConfig,
        adminTenant, dsConfigMgr, secretsManager,
        "jdbc:postgresql://localhost:5432/db?currentSchema=wezeon_admin_00000000_0000_0000_0000_000000000000",
        "db", "prefix", "script:script", 10, true);

    UUID tenantId = UUID.fromString("11111111-1111-1111-1111-111111111111");
    DataSourceConfiguration tenantConfig = provider.getConfiguration(tenantId);

    assertEquals("jdbc:postgresql://localhost:5432/db", tenantConfig.getUrl().toString());
  }

  @Test
  void testTenantDsConfigProvider_CleansQuestAndAmpersandFromJdbcUrl() {
    DataSourceConfiguration adminConfig = mock(DataSourceConfiguration.class);
    TenantData adminTenant = mock(TenantData.class);
    when(adminTenant.getId()).thenReturn(UUID.fromString("00000000-0000-0000-0000-000000000000"));

    DataSourceConfigurationManager dsConfigMgr = mock(DataSourceConfigurationManager.class);
    when(dsConfigMgr.getFqName(any(), any())).thenReturn("fqName");

    SecretsManager<String, String> secretsManager = mock(SecretsManager.class);

    DataSourceConfigurationProvider<UUID> provider = config.tenantDsConfigProvider(adminConfig,
        adminTenant, dsConfigMgr, secretsManager, "jdbc:postgresql://localhost:5432/db?&param=val",
        "db", "prefix", "script:script", 10, true);

    UUID tenantId = UUID.fromString("11111111-1111-1111-1111-111111111111");
    DataSourceConfiguration tenantConfig = provider.getConfiguration(tenantId);

    assertEquals("jdbc:postgresql://localhost:5432/db?param=val", tenantConfig.getUrl().toString());
  }

  @Test
  void testTenantDsConfigProvider_CleansUrlEndingWithQuestionMark() {
    DataSourceConfiguration adminConfig = mock(DataSourceConfiguration.class);
    TenantData adminTenant = mock(TenantData.class);
    when(adminTenant.getId()).thenReturn(UUID.fromString("00000000-0000-0000-0000-000000000000"));

    DataSourceConfigurationManager dsConfigMgr = mock(DataSourceConfigurationManager.class);
    when(dsConfigMgr.getFqName(any(), any())).thenReturn("fqName");

    SecretsManager<String, String> secretsManager = mock(SecretsManager.class);

    DataSourceConfigurationProvider<UUID> provider
        = config.tenantDsConfigProvider(adminConfig, adminTenant, dsConfigMgr, secretsManager,
            "jdbc:postgresql://localhost:5432/db?", "db", "prefix", "script:script", 10, true);

    UUID tenantId = UUID.fromString("11111111-1111-1111-1111-111111111111");
    DataSourceConfiguration tenantConfig = provider.getConfiguration(tenantId);

    assertEquals("jdbc:postgresql://localhost:5432/db", tenantConfig.getUrl().toString());
  }
}
