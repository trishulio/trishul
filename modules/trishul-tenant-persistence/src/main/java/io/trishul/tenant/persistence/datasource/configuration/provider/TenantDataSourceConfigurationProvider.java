package io.trishul.tenant.persistence.datasource.configuration.provider;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.trishul.data.datasource.configuration.manager.DataSourceConfigurationManager;
import io.trishul.data.datasource.configuration.model.DataSourceConfiguration;
import io.trishul.data.datasource.configuration.model.GlobalDataSourceConfiguration;
import io.trishul.data.datasource.configuration.model.LazyTenantDataSourceConfiguration;
import io.trishul.data.datasource.configuration.provider.DataSourceConfigurationProvider;
import io.trishul.secrets.SecretsManager;
import io.trishul.tenant.entity.TenantData;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import javax.annotation.Nonnull;

public class TenantDataSourceConfigurationProvider
    implements DataSourceConfigurationProvider<UUID> {
  private final LoadingCache<UUID, DataSourceConfiguration> cache;
  private final DataSourceConfiguration adminDataSourceConfiguration;

  public TenantDataSourceConfigurationProvider(DataSourceConfiguration adminDataSourceConfiguration,
      TenantData adminTenant, GlobalDataSourceConfiguration globalTenantDsConfig,
      DataSourceConfigurationManager dsConfigMgr, SecretsManager<String, String> secretsManager) {
    this.adminDataSourceConfiguration = adminDataSourceConfiguration;

    this.cache = CacheBuilder.newBuilder().build(new CacheLoader<UUID, DataSourceConfiguration>() {
      @Override
      public DataSourceConfiguration load(@Nonnull UUID tenantId) throws Exception {
        DataSourceConfiguration config = adminDataSourceConfiguration;

        if (!adminTenant.getId().equals(tenantId)) {
          String fqName = dsConfigMgr.getFqName(globalTenantDsConfig.getSchemaPrefix(), tenantId);
          config
              = new LazyTenantDataSourceConfiguration(fqName, globalTenantDsConfig, secretsManager);
        }

        return config;
      }
    });
  }

  @Override
  public DataSourceConfiguration getConfiguration(UUID tenantId) {
    try {
      return this.cache.get(tenantId);
    } catch (ExecutionException e) {
      throw new RuntimeException(
          String.format("Failed to load datasource configuration for tenantId: '%s' because: %s",
              tenantId, e.getMessage()),
          e);
    }
  }

  @Override
  public DataSourceConfiguration getAdminConfiguration() {
    return this.adminDataSourceConfiguration;
  }
}
