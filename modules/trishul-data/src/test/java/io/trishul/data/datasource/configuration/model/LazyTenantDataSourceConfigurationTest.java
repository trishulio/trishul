package io.trishul.data.datasource.configuration.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import io.trishul.secrets.SecretsManager;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
  void testIsAutoCommit_ReturnsGlobalAutoCommit() throws URISyntaxException {
    assertFalse(config.isAutoCommit());

    LazyTenantDataSourceConfiguration otherConfig = new LazyTenantDataSourceConfiguration(
        "FQ_TENANT_ID", new ImmutableGlobalDataSourceConfiguration(new URI("jdbc://localhost/"),
            "dbName", MigrationConfiguration.from("MIGRATION_PATH"), "SCHEMA_", 10, true),
        mSecretsManager);
    assertTrue(otherConfig.isAutoCommit());
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

  @Test
  void testGetPassword_ReturnsStaticPasswordWhenInitializedWithStaticConstructor() {
    DataSourceConfiguration staticConfig
        = new LazyTenantDataSourceConfiguration("user", "static_password", "schema",
            new ImmutableGlobalDataSourceConfiguration(null, null, null, null, 0, false));
    assertEquals("static_password", staticConfig.getPassword());
    assertEquals("user", staticConfig.getUserName());
    assertEquals("schema", staticConfig.getSchemaName());
  }

  @Test
  void testGetPassword_CachesPassword() throws IOException {
    doReturn("PASSWORD").when(mSecretsManager).get("FQ_TENANT_ID");

    assertEquals("PASSWORD", config.getPassword());
    assertEquals("PASSWORD", config.getPassword());

    // verify secretsManager.get is only called once
    verify(mSecretsManager, times(1)).get("FQ_TENANT_ID");
  }

  @Test
  void testGetPassword_ThrowsRuntimeException_WhenSecretsManagerThrowsIOException()
      throws IOException {
    doThrow(new IOException("Secrets error")).when(mSecretsManager).get("FQ_TENANT_ID");

    assertThrows(RuntimeException.class, () -> config.getPassword());
  }

  @Test
  void testEqualsAndHashCode() throws URISyntaxException {
    GlobalDataSourceConfiguration global = new ImmutableGlobalDataSourceConfiguration(
        new URI("jdbc://localhost/"), "db", null, "SCHEMA_", 10, false);
    LazyTenantDataSourceConfiguration c1
        = new LazyTenantDataSourceConfiguration("user", "pass", "schema", global);
    LazyTenantDataSourceConfiguration c2
        = new LazyTenantDataSourceConfiguration("user", "pass", "schema", global);
    LazyTenantDataSourceConfiguration c3
        = new LazyTenantDataSourceConfiguration("other", "pass", "schema", global);

    assertEquals(c1, c2);
    assertEquals(c1.hashCode(), c2.hashCode());
    assertNotEquals(c1.hashCode(), c3.hashCode());
    assertNotEquals(0, c1.hashCode());
    assertNotEquals(c1, c3);
    assertNotEquals(c1, null);
    assertNotEquals(c1, "some_string");
    assertEquals(c1, c1);
  }
}
