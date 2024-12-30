package io.trishul.tenant.persistence.datasource.configuration.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import io.trishul.data.datasource.configuration.manager.DataSourceConfigurationManager;
import io.trishul.data.datasource.configuration.model.DataSourceConfiguration;
import io.trishul.data.datasource.configuration.model.GlobalDataSourceConfiguration;
import io.trishul.data.datasource.configuration.model.ImmutableGlobalDataSourceConfiguration;
import io.trishul.data.datasource.configuration.model.LazyTenantDataSourceConfiguration;
import io.trishul.data.datasource.configuration.provider.DataSourceConfigurationProvider;
import io.trishul.secrets.SecretsManager;
import io.trishul.tenant.entity.AdminTenant;
import io.trishul.tenant.entity.TenantData;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TenantDataSourceConfigurationProviderTest {
  private DataSourceConfigurationProvider<UUID> dsProvider;

  private DataSourceConfiguration mAdminConfig;
  private TenantData mAdminTenant;
  private GlobalDataSourceConfiguration mGlobalDsConfig;
  private DataSourceConfigurationManager mConfigMgr;
  private SecretsManager<String, String> mSecretsMgr;

  @BeforeEach
  public void init() throws URISyntaxException, MalformedURLException {
    mAdminTenant = new AdminTenant(UUID.fromString("00000000-0000-0000-0000-000000000000"), "ADMIN",
        new URL("http://localhost/"));
    mGlobalDsConfig = new ImmutableGlobalDataSourceConfiguration(new URI("jdbc://url/"), "dbName",
        "MIGRATION_PATH", "SCHEMA_", 10, false);
    mAdminConfig = new LazyTenantDataSourceConfiguration("00000000-0000-0000-0000-000000000000",
        mGlobalDsConfig, mSecretsMgr);
    mConfigMgr
        = new DataSourceConfigurationManager();interface StringSecretsManager extends SecretsManager<String,String>{}
    mSecretsMgr = mock(StringSecretsManager.class);

    dsProvider = new TenantDataSourceConfigurationProvider(mAdminConfig, mAdminTenant,
        mGlobalDsConfig, mConfigMgr, mSecretsMgr);
  }

  @Test
  public void testGetConfiguration_ReturnsAdminConfig_WhenIdMatchesAdminId() {
    DataSourceConfiguration config
        = dsProvider.getConfiguration(UUID.fromString("00000000-0000-0000-0000-000000000000"));
    assertSame(mAdminConfig, config);
  }

  @Test
  public void testGetConfiguration_ReturnsLazyTenantConfig_WhenIdIsNotTenantId()
      throws URISyntaxException {
    DataSourceConfiguration config
        = dsProvider.getConfiguration(UUID.fromString("00000000-0000-0000-0000-000000000001"));

    DataSourceConfiguration expected
        = new LazyTenantDataSourceConfiguration("SCHEMA_00000000_0000_0000_0000_000000000001",
            new ImmutableGlobalDataSourceConfiguration(new URI("jdbc://url/"), "dbName",
                "MIGRATION_PATH", "SCHEMA_", 10, false),
            mSecretsMgr);

    assertNotSame(mAdminConfig, config);
    assertEquals(expected, config);
  }

  @Test
  public void testGetAdminConfiguration_ReturnsAdminDsConfiguration() {
    DataSourceConfiguration config = dsProvider.getAdminConfiguration();

    assertEquals(mAdminConfig, config);
  }
}
