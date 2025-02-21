package io.trishul.data.datasource.configuration.model;

import java.util.Arrays;

public class MigrationConfiguration {
  private final String migrationHistoryTableName;
  private final String migrationScriptPath;

  public MigrationConfiguration(String migrationHistoryTableName, String migrationScriptPath) {
    this.migrationHistoryTableName = migrationHistoryTableName;
    this.migrationScriptPath = migrationScriptPath;
  }

  public String getMigrationHistoryTableName() {
    return migrationHistoryTableName;
  }

  public String getMigrationScriptPath() {
    return migrationScriptPath;
  }

  public static MigrationConfiguration[] from(String migrationConfigurationsStr) {
    return Arrays.stream(migrationConfigurationsStr.split(";")).map(configStr -> {
      String[] config = configStr.split(":");
      String schemaHistoryTableName, schemaMigrationScriptPath;

      if (config.length <= 0 || config.length > 2) {
        throw new IllegalArgumentException("Invalid schema migration script config: " + configStr);
      } else if (config.length == 1) {
        schemaHistoryTableName = null;
        schemaMigrationScriptPath = config[0];
      } else {
        schemaHistoryTableName = config[0];
        schemaMigrationScriptPath = config[1];
      }

      return new MigrationConfiguration(schemaHistoryTableName, schemaMigrationScriptPath);
    }).toArray(MigrationConfiguration[]::new); // Correctly cast to MigrationConfiguration[]
  }
}
