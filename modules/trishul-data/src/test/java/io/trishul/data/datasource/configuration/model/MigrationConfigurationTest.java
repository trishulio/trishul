package io.trishul.data.datasource.configuration.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class MigrationConfigurationTest {

  @Test
  void testConstructor_SetsFields() {
    MigrationConfiguration config = new MigrationConfiguration("history_table", "db/migration");

    assertEquals("history_table", config.getMigrationHistoryTableName());
    assertEquals("db/migration", config.getMigrationScriptPath());
  }

  @Test
  void testGetMigrationHistoryTableName_ReturnsHistoryTableName() {
    MigrationConfiguration config = new MigrationConfiguration("flyway_schema_history", "scripts");

    String result = config.getMigrationHistoryTableName();

    assertEquals("flyway_schema_history", result);
  }

  @Test
  void testGetMigrationScriptPath_ReturnsMigrationScriptPath() {
    MigrationConfiguration config = new MigrationConfiguration("table", "db/migrations/v1");

    String result = config.getMigrationScriptPath();

    assertEquals("db/migrations/v1", result);
  }

  @Test
  void testGetMigrationHistoryTableName_ReturnsNull_WhenSetToNull() {
    MigrationConfiguration config = new MigrationConfiguration(null, "scripts");

    assertNull(config.getMigrationHistoryTableName());
  }

  @Test
  void testFrom_ReturnsSingleConfig_WhenSinglePathProvided() {
    MigrationConfiguration[] configs = MigrationConfiguration.from("db/migration");

    assertNotNull(configs);
    assertEquals(1, configs.length);
    assertNull(configs[0].getMigrationHistoryTableName());
    assertEquals("db/migration", configs[0].getMigrationScriptPath());
  }

  @Test
  void testFrom_ParsesTableNameAndPath_WhenColonSeparated() {
    MigrationConfiguration[] configs = MigrationConfiguration.from("schema_history:db/migration");

    assertNotNull(configs);
    assertEquals(1, configs.length);
    assertEquals("schema_history", configs[0].getMigrationHistoryTableName());
    assertEquals("db/migration", configs[0].getMigrationScriptPath());
  }

  @Test
  void testFrom_ReturnsMultipleConfigs_WhenSemicolonSeparated() {
    MigrationConfiguration[] configs = MigrationConfiguration.from("path1;path2;path3");

    assertNotNull(configs);
    assertEquals(3, configs.length);
    assertEquals("path1", configs[0].getMigrationScriptPath());
    assertEquals("path2", configs[1].getMigrationScriptPath());
    assertEquals("path3", configs[2].getMigrationScriptPath());
  }

  @Test
  void testFrom_ParsesMultipleConfigsWithTableNames() {
    MigrationConfiguration[] configs = MigrationConfiguration.from("table1:path1;table2:path2");

    assertNotNull(configs);
    assertEquals(2, configs.length);
    assertEquals("table1", configs[0].getMigrationHistoryTableName());
    assertEquals("path1", configs[0].getMigrationScriptPath());
    assertEquals("table2", configs[1].getMigrationHistoryTableName());
    assertEquals("path2", configs[1].getMigrationScriptPath());
  }

  @Test
  void testFrom_HandlesMixedFormats() {
    MigrationConfiguration[] configs
        = MigrationConfiguration.from("table1:path1;path2;table3:path3");

    assertNotNull(configs);
    assertEquals(3, configs.length);

    assertEquals("table1", configs[0].getMigrationHistoryTableName());
    assertEquals("path1", configs[0].getMigrationScriptPath());

    assertNull(configs[1].getMigrationHistoryTableName());
    assertEquals("path2", configs[1].getMigrationScriptPath());

    assertEquals("table3", configs[2].getMigrationHistoryTableName());
    assertEquals("path3", configs[2].getMigrationScriptPath());
  }

  @Test
  void testFrom_ThrowsIllegalArgumentException_WhenMoreThanTwoColonParts() {
    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
        () -> MigrationConfiguration.from("part1:part2:part3"));

    assertNotNull(ex.getMessage());
    assertEquals("Invalid schema migration script config: part1:part2:part3", ex.getMessage());
  }

  @Test
  void testFrom_ThrowsIllegalArgumentException_WhenInvalidConfigInMultiple() {
    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
        () -> MigrationConfiguration.from("valid:path;a:b:c"));

    assertNotNull(ex.getMessage());
    assertEquals("Invalid schema migration script config: a:b:c", ex.getMessage());
  }

  @Test
  void testFrom_ReturnsArrayWithCorrectType() {
    MigrationConfiguration[] configs = MigrationConfiguration.from("path");

    assertNotNull(configs);
    assertEquals(MigrationConfiguration[].class, configs.getClass());
  }

  @Test
  void testFrom_HandlesEmptyTableName_WhenColonAtStart() {
    // ":path" splits to ["", "path"]
    MigrationConfiguration[] configs = MigrationConfiguration.from(":path");

    assertNotNull(configs);
    assertEquals(1, configs.length);
    assertEquals("", configs[0].getMigrationHistoryTableName());
    assertEquals("path", configs[0].getMigrationScriptPath());
  }

  @Test
  void testFrom_HandlesTrailingColon_AsPathOnly() {
    // "table:" splits to ["table"] (Java trims trailing empty strings)
    // So it's treated as a path-only config
    MigrationConfiguration[] configs = MigrationConfiguration.from("table:");

    assertNotNull(configs);
    assertEquals(1, configs.length);
    assertNull(configs[0].getMigrationHistoryTableName());
    assertEquals("table", configs[0].getMigrationScriptPath());
  }
}
