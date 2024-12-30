package io.trishul.data.datasource.configuration.model;

import io.trishul.model.base.pojo.BaseModel;
import io.trishul.secrets.SecretsManager;
import java.io.IOException;
import java.net.URI;

// TODO: Rename this to a datasource that automatically lazily fetches password, nothing to do with
// tenant
public class LazyTenantDataSourceConfiguration extends BaseModel
    implements DataSourceConfiguration {
  private final GlobalDataSourceConfiguration globalConfig;

  private final String userName;
  private String password;
  private final String schemaName;

  private final SecretsManager<String, String> secretsManager;

  public LazyTenantDataSourceConfiguration(String fqTenantId,
      GlobalDataSourceConfiguration globalConfig, SecretsManager<String, String> secretsManager) {
    this.userName = fqTenantId;
    // Note: For simplicity, we are keeping the tenant's DB user's name and the
    // schema-name to be the same. No logic is/should depend on this equality.
    this.schemaName = fqTenantId;

    this.secretsManager = secretsManager;
    this.globalConfig = globalConfig;
  }

  @Override
  public String getUserName() {
    return this.userName;
  }

  @Override
  public String getPassword() {
    if (this.password == null) {
      try {
        // Note: See the TenantDataSourceRegister to understand why schemaName is used
        // (instead of userName) as key to store the password in secretsManager
        this.password = this.secretsManager.get(getSchemaName());
      } catch (IOException e) {
        throw new RuntimeException(
            String.format("Failed to load credentials for schema: '%s' because %s", getSchemaName(),
                e.getMessage()),
            e);
      }
    }

    return this.password;
  }

  @Override
  public String getSchemaName() {
    return this.schemaName;
  }

  @Override
  public int getPoolSize() {
    return this.globalConfig.getPoolSize();
  }

  @Override
  public boolean isAutoCommit() {
    return this.globalConfig.isAutoCommit();
  }

  @Override
  public URI getUrl() {
    return this.globalConfig.getUrl();
  }

  @Override
  public String getDbName() {
    return this.globalConfig.getDbName();
  }

  @Override
  public String getMigrationScriptPath() {
    return this.globalConfig.getMigrationScriptPath();
  }

  @Override
  public String getSchemaPrefix() {
    return this.globalConfig.getSchemaPrefix();
  }
}
