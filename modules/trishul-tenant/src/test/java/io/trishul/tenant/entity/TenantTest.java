package io.trishul.tenant.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class TenantTest {
    private Tenant tenant;

    @BeforeEach
    public void init() {
        tenant = new Tenant();
    }

    @Test
    public void testAllArgConstructor() throws MalformedURLException {
        tenant =
                new Tenant(
                        UUID.fromString("89efec46-fd0b-4fec-bcde-7f4bcef4f8e9"),
                        "TENANT_1",
                        new URL("http://localhost"),
                        true,
                        LocalDateTime.of(2000, 1, 1, 0, 0),
                        LocalDateTime.of(2001, 1, 1, 0, 0));

        assertEquals(UUID.fromString("89efec46-fd0b-4fec-bcde-7f4bcef4f8e9"), tenant.getId());
        assertEquals("TENANT_1", tenant.getName());
        assertEquals(new URL("http://localhost"), tenant.getUrl());
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), tenant.getCreatedAt());
        assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), tenant.getLastUpdated());
    }

    @Test
    public void testSetId_NoArgs_SetsRandomUuid_WhenIdIsNull() {
        assertNull(tenant.getId());
        tenant.setId();

        // assertNotNull(tenant.getId()); // TODO: Reenable it. failure during
        // compilations
    }

    @Test
    public void testSetId_NoArgs_DoesNothing_WhenIdIsSet() {
        tenant.setId(UUID.fromString("89efec46-fd0b-4fec-bcde-7f4bcef4f8e9"));

        tenant.setId();
        assertEquals(UUID.fromString("89efec46-fd0b-4fec-bcde-7f4bcef4f8e9"), tenant.getId());
    }

    @Test
    public void testGetSetId() {
        UUID id = UUID.fromString("89efec46-fd0b-4fec-bcde-7f4bcef4f8e9");
        tenant.setId(id);
        assertSame(id, tenant.getId());
    }

    @Test
    public void testGetSetName() {
        String name = "testName";
        tenant.setName(name);
        assertSame(name, tenant.getName());
    }

    @Test
    public void testGetSetUrl() throws MalformedURLException {
        URL url = new URL("http://localhost");
        tenant.setUrl(url);
        assertSame(url, tenant.getUrl());
    }

    @Test
    public void testGetSetCreated() {
        LocalDateTime created = LocalDateTime.now();
        tenant.setCreatedAt(created);
        assertSame(created, tenant.getCreatedAt());
    }

    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.now();
        tenant.setLastUpdated(lastUpdated);
        assertSame(lastUpdated, tenant.getLastUpdated());
    }

    @Test
    public void testToString_ReturnsJsonifiedString() throws JSONException, MalformedURLException {
        tenant =
                new Tenant(
                        UUID.fromString("89efec46-fd0b-4fec-bcde-7f4bcef4f8e9"),
                        "TENANT_1",
                        new URL("http://localhost"),
                        true,
                        LocalDateTime.of(2000, 1, 1, 0, 0),
                        LocalDateTime.of(2001, 1, 1, 0, 0));

        final String json =
                "{\"id\":\"89efec46-fd0b-4fec-bcde-7f4bcef4f8e9\",\"name\":\"TENANT_1\",\"url\":\"http://localhost\",\"createdAt\":\"2000-01-01T00:00:00\",\"lastUpdated\":\"2001-01-01T00:00:00\", \"version\": null, \"isReady\": true}";
        JSONAssert.assertEquals(json, tenant.toString(), JSONCompareMode.NON_EXTENSIBLE);
    }
}
