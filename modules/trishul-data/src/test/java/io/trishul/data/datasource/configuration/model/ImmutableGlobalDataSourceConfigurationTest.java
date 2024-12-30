package io.trishul.data.datasource.configuration.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ImmutableGlobalDataSourceConfigurationTest {
  private GlobalDataSourceConfiguration dsConfig;

  @BeforeEach
  public void init() throws URISyntaxException {
    dsConfig = new ImmutableGlobalDataSourceConfiguration(new URI("jdbc://localhost/"), "dbName",
        "MIGRATION_PATH", "SCHEMA_PREFIX", 10, true);
  }

  @Test
  public void testGetUrl_ReturnsUrl() throws URISyntaxException {
    assertEquals(new URI("jdbc://localhost/"), dsConfig.getUrl());
  }

  @Test
  public void testGetDbName_ReturnsDbName() {
    assertEquals("dbName", dsConfig.getDbName());
  }

  @Test
  public void testGetMigrationScriptPath_ReturnsMigrationScriptPath() {
    assertEquals("MIGRATION_PATH", dsConfig.getMigrationScriptPath());
  }

  @Test
  public void testGetSchemaPrefix_ReturnsSchemaPrefix() {
    assertEquals("SCHEMA_PREFIX", dsConfig.getSchemaPrefix());
  }

  @Test
  public void testGetPoolSize_ReturnsPoolSize() {
    assertEquals(10, dsConfig.getPoolSize());
  }

  @Test
  public void testIsAutoCommit_ReturnsAutoCommit() {
    assertEquals(true, dsConfig.isAutoCommit());
  }
}
