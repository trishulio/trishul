package io.trishul.data.datasource.configuration.model;

import java.net.URI;

public interface GlobalDataSourceConfiguration {
  URI getUrl();

  String getDbName();

  MigrationConfiguration[] getMigrationConfigurations();

  String getSchemaPrefix();

  int getPoolSize();

  boolean isAutoCommit();
}
