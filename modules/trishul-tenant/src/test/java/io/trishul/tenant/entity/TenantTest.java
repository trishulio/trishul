package io.trishul.tenant.entity;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.UUID;
import org.json.JSONException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

class TenantTest {
  private Tenant tenant;

  @BeforeEach
  void init() {
    tenant = new Tenant();
  }

  @Test
  void testAllArgConstructor() {
    tenant = new Tenant(UUID.fromString("89efec46-fd0b-4fec-bcde-7f4bcef4f8e9"), "TENANT_1",
        URI.create("http://localhost"), true, LocalDateTime.of(2000, 1, 1, 0, 0),
        LocalDateTime.of(2001, 1, 1, 0, 0));

    assertEquals(UUID.fromString("89efec46-fd0b-4fec-bcde-7f4bcef4f8e9"), tenant.getId());
    assertEquals("TENANT_1", tenant.getName());
    assertEquals(URI.create("http://localhost"), tenant.getUrl());
    assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), tenant.getCreatedAt());
    assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), tenant.getLastUpdated());
  }

  @Test
  void testSetId_NoArgs_SetsRandomUuid_WhenIdIsNull() {
    assertNull(tenant.getId());
    tenant.setId();

    // assertNotNull(tenant.getId()); // TODO: Reenable it. failure during
    // compilations
  }

  @Test
  void testSetId_NoArgs_DoesNothing_WhenIdIsSet() {
    tenant.setId(UUID.fromString("89efec46-fd0b-4fec-bcde-7f4bcef4f8e9"));

    tenant.setId();
    assertEquals(UUID.fromString("89efec46-fd0b-4fec-bcde-7f4bcef4f8e9"), tenant.getId());
  }

  @Test
  void testGetSetId() {
    UUID id = UUID.fromString("89efec46-fd0b-4fec-bcde-7f4bcef4f8e9");
    tenant.setId(id);
    assertSame(id, tenant.getId());
  }

  @Test
  void testGetSetName() {
    String name = "testName";
    tenant.setName(name);
    assertSame(name, tenant.getName());
  }

  @Test
  void testGetSetUrl() {
    URI url = URI.create("http://localhost");
    tenant.setUrl(url);
    assertSame(url, tenant.getUrl());
  }

  @Test
  void testGetSetCreated() {
    LocalDateTime created = LocalDateTime.now();
    tenant.setCreatedAt(created);
    assertSame(created, tenant.getCreatedAt());
  }

  @Test
  void testGetSetLastUpdated() {
    LocalDateTime lastUpdated = LocalDateTime.now();
    tenant.setLastUpdated(lastUpdated);
    assertSame(lastUpdated, tenant.getLastUpdated());
  }

  @Test
  void testToString_ReturnsJsonifiedString() throws JSONException {
    tenant = new Tenant(UUID.fromString("89efec46-fd0b-4fec-bcde-7f4bcef4f8e9"), "TENANT_1",
        URI.create("http://localhost"), true, LocalDateTime.of(2000, 1, 1, 0, 0),
        LocalDateTime.of(2001, 1, 1, 0, 0));

    final String json
        = "{\"id\":\"89efec46-fd0b-4fec-bcde-7f4bcef4f8e9\",\"name\":\"TENANT_1\",\"url\":\"http://localhost\",\"createdAt\":\"2000-01-01T00:00:00\",\"lastUpdated\":\"2001-01-01T00:00:00\", \"version\": null, \"isReady\": true}";
    JSONAssert.assertEquals(json, tenant.toString(), JSONCompareMode.NON_EXTENSIBLE);
  }
}
