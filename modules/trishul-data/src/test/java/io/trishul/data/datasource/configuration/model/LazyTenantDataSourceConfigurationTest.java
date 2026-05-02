package io.trishul.data.datasource.configuration.model;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import io.trishul.secrets.SecretsManager;

class LazyTenantDataSourceConfigurationTest {
  private DataSourceConfiguration config;

  private SecretsManager<String, String> mSecretsManager;

  @BeforeEach
  void init() throws URISyntaxException {
    mSecretsManager = mock(SecretsManager.class);

    config = new LazyTenantDataSourceConfiguration(
        "FQ_TENANT_ID", new ImmutableGlobalDataSourceConfiguration(new URI("jdbc://localhost/"),
            "dbName", MigrationConfiguration.from("MIGRATION_PATH"), "SCHEMA_", 10, false),
        mSecretsManager);
  }

  @Test
  void testGetUserName_ReturnsUsernameField() {
    assertEquals("FQ_TENANT_ID", config.getUserName());
  }

  @Test
  void testGetPassword_ReturnsPasswordFromSecretsManager() throws IOException {
    doReturn("PASSWORD").when(mSecretsManager).get("FQ_TENANT_ID");

    assertEquals("PASSWORD", config.getPassword());
  }

  @Test
  void testGetSchemaName_ReturnsSchemaNameField() {
    assertEquals("FQ_TENANT_ID", config.getSchemaName());
  }

  @Test
  void testGetPoolSize_ReturnsGlobalPoolSize() {
    assertEquals(10, config.getPoolSize());
  }

  @Test
  void testIsAutoCommit_ReturnsGlobalAutoCommit() {
    assertFalse(config.isAutoCommit());
  }

  @Test
  void testGetUrl_ReturnsGlobalUrl() throws URISyntaxException {
    assertEquals(new URI("jdbc://localhost/"), config.getUrl());
  }

  @Test
  void testGetDbName_ReturnsGlobalDbName() {
    assertEquals("dbName", config.getDbName());
  }

  @Test
  void testGetMigrationScript_ReturnsGlobalMigrationScriptPath() {
    assertEquals(1, config.getMigrationConfigurations().length);
    assertEquals("MIGRATION_PATH", config.getMigrationConfigurations()[0].getMigrationScriptPath());
  }

  @Test
  void testGetSchemaPrefix_ReturnsGlobalSchemaPrefix() {
    assertEquals("SCHEMA_", config.getSchemaPrefix());
  }
}
