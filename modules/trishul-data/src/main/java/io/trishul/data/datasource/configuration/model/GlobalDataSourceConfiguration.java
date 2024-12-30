package io.trishul.data.datasource.configuration.model;

import java.net.URI;

public interface GlobalDataSourceConfiguration {
  URI getUrl();

  String getDbName();

  String getMigrationScriptPath();

  String getSchemaPrefix();

  int getPoolSize();

  boolean isAutoCommit();
}
