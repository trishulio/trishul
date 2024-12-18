package io.trishul.data.datasource.configuration.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import io.trishul.secrets.SecretsManager;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LazyTenantDataSourceConfigurationTest {
    private DataSourceConfiguration config;

    private SecretsManager<String, String> mSecretsMgr;

    @BeforeEach
    public void init() throws URISyntaxException {
        mSecretsMgr = mock(SecretsManager.class);

        config =
                new LazyTenantDataSourceConfiguration(
                        "FQ_TENANT_ID",
                        new ImmutableGlobalDataSourceConfiguration(
                                new URI("jdbc://localhost/"),
                                "dbName",
                                "MIGRATION_PATH",
                                "SCHEMA_",
                                10,
                                false),
                        mSecretsMgr);
    }

    @Test
    public void testGetUserName_ReturnsUsernameField() {
        assertEquals("FQ_TENANT_ID", config.getUserName());
    }

    @Test
    public void testGetPassword_ReturnsPasswordFromSecretsManager() throws IOException {
        doReturn("PASSWORD").when(mSecretsMgr).get("FQ_TENANT_ID");

        assertEquals("PASSWORD", config.getPassword());
    }

    @Test
    public void testGetSchemaName_ReturnsSchemaNameField() {
        assertEquals("FQ_TENANT_ID", config.getSchemaName());
    }

    @Test
    public void testGetPoolSize_ReturnsGlobalPoolSize() {
        assertEquals(10, config.getPoolSize());
    }

    @Test
    public void testIsAutoCommit_ReturnsGlobalAutoCommit() {
        assertFalse(config.isAutoCommit());
    }

    @Test
    public void testGetUrl_ReturnsGlobalUrl() throws URISyntaxException {
        assertEquals(new URI("jdbc://localhost/"), config.getUrl());
    }

    @Test
    public void testGetDbName_ReturnsGlobalDbName() {
        assertEquals("dbName", config.getDbName());
    }

    @Test
    public void testGetMigrationScript_ReturnsGlobalMigrationScriptPath() {
        assertEquals("MIGRATION_PATH", config.getMigrationScriptPath());
    }

    @Test
    public void testGetSchemaPrefix_ReturnsGlobalSchemaPrefix() {
        assertEquals("SCHEMA_", config.getSchemaPrefix());
    }
}
