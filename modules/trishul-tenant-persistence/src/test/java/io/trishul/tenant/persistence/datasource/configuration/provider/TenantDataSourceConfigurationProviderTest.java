package io.company.brewcraft.data;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.AdminTenant;
import io.company.brewcraft.model.Tenant;
import io.company.brewcraft.security.store.SecretsManager;

public class TenantDataSourceConfigurationProviderTest {
    private DataSourceConfigurationProvider<UUID> dsProvider;

    private DataSourceConfiguration mAdminConfig;
    private Tenant mAdminTenant;
    private GlobalDataSourceConfiguration mGlobalDsConfig;
    private DataSourceConfigurationManager mConfigMgr;
    private SecretsManager<String, String> mSecretsMgr;

    @BeforeEach
    public void init() throws URISyntaxException {
        mAdminTenant = new AdminTenant(UUID.fromString("00000000-0000-0000-0000-000000000000"), "ADMIN");
        mGlobalDsConfig = new ImmutableGlobalDataSourceConfiguration(new URI("jdbc://url/"), "dbName", "MIGRATION_PATH", "SCHEMA_", 10, false);
        mAdminConfig = new LazyTenantDataSourceConfiguration("00000000-0000-0000-0000-000000000000", mGlobalDsConfig, mSecretsMgr);
        mConfigMgr = new DataSourceConfigurationManager();
        mSecretsMgr = mock(SecretsManager.class);

        dsProvider = new TenantDataSourceConfigurationProvider(mAdminConfig, mAdminTenant, mGlobalDsConfig, mConfigMgr, mSecretsMgr);
    }

    @Test
    public void testGetConfiguration_ReturnsAdminConfig_WhenIdMatchesAdminId() {
        DataSourceConfiguration config = dsProvider.getConfiguration(UUID.fromString("00000000-0000-0000-0000-000000000000"));
        assertSame(mAdminConfig, config);
    }

    @Test
    public void testGetConfiguration_ReturnsLazyTenantConfig_WhenIdIsNotTenantId() throws URISyntaxException {
        DataSourceConfiguration config = dsProvider.getConfiguration(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        DataSourceConfiguration expected = new LazyTenantDataSourceConfiguration(
            "SCHEMA_00000000_0000_0000_0000_000000000001",
            new ImmutableGlobalDataSourceConfiguration(new URI("jdbc://url/"), "dbName", "MIGRATION_PATH", "SCHEMA_", 10, false),
            mSecretsMgr
        );

        assertNotSame(mAdminConfig, config);
        assertEquals(expected, config);
    }

    @Test
    public void testGetAdminConfiguration_ReturnsAdminDsConfiguration() {
        DataSourceConfiguration config = dsProvider.getAdminConfiguration();

        assertEquals(mAdminConfig, config);
    }
}
