package io.trishul.data.datasource.configuration.model;

import io.trishul.model.base.pojo.BaseModel;
import java.net.URI;

public class ImmutableGlobalDataSourceConfiguration extends BaseModel
    implements GlobalDataSourceConfiguration {
  private final URI url;
  private final String dbName;
  private final MigrationConfiguration[] migrationConfigurations;
  private final String schemaPrefix;
  private final int poolSize;
  private final boolean autoCommit;

  public ImmutableGlobalDataSourceConfiguration(URI url, String dbName,
      MigrationConfiguration[] migrationConfigurations, String schemaPrefix, int poolSize,
      boolean autoCommit) {
    this.url = url;
    this.dbName = dbName;
    this.schemaPrefix = schemaPrefix;
    this.migrationConfigurations = migrationConfigurations;
    this.poolSize = poolSize;
    this.autoCommit = autoCommit;
  }

  @Override
  public URI getUrl() {
    return url;
  }

  @Override
  public String getDbName() {
    return dbName;
  }

  @Override
  public MigrationConfiguration[] getMigrationConfigurations() {
    return migrationConfigurations;
  }

  @Override
  public String getSchemaPrefix() {
    return schemaPrefix;
  }

  @Override
  public int getPoolSize() {
    return poolSize;
  }

  @Override
  public boolean isAutoCommit() {
    return autoCommit;
  }
}
