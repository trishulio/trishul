package io.trishul.data.datasource.configuration.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ImmutableGlobalDataSourceConfigurationTest {
  private GlobalDataSourceConfiguration dsConfig;

  @BeforeEach
  void init() throws URISyntaxException {
    dsConfig = new ImmutableGlobalDataSourceConfiguration(new URI("jdbc://localhost/"), "dbName",
        MigrationConfiguration.from("MIGRATION_PATH"), "SCHEMA_PREFIX", 10, true);
  }

  @Test
  void testGetUrl_ReturnsUrl() throws URISyntaxException {
    assertEquals(new URI("jdbc://localhost/"), dsConfig.getUrl());
  }

  @Test
  void testGetDbName_ReturnsDbName() {
    assertEquals("dbName", dsConfig.getDbName());
  }

  @Test
  void testGetMigrationConfigurations_ReturnsMigrationConfigurations() {
    assertEquals(1, dsConfig.getMigrationConfigurations().length);
    assertEquals("MIGRATION_PATH",
        dsConfig.getMigrationConfigurations()[0].getMigrationScriptPath());
  }

  @Test
  void testGetSchemaPrefix_ReturnsSchemaPrefix() {
    assertEquals("SCHEMA_PREFIX", dsConfig.getSchemaPrefix());
  }

  @Test
  void testGetPoolSize_ReturnsPoolSize() {
    assertEquals(10, dsConfig.getPoolSize());
  }

  @Test
  void testIsAutoCommit_ReturnsAutoCommit() {
    assertEquals(true, dsConfig.isAutoCommit());
  }
}
