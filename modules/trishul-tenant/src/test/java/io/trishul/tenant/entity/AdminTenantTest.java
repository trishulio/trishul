package io.trishul.tenant.entity;

import java.net.MalformedURLException;
import java.net.URI;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdminTenantTest {
  private TenantData adminTenant;

  @BeforeEach
  void init() throws MalformedURLException {
    adminTenant = new AdminTenant(UUID.fromString("00000000-0000-0000-0000-000000000001"), "ADMIN",
        URI.create("http://localhost/").toURL());
  }

  @Test
  void testGetId() {
    assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000001"), adminTenant.getId());
  }

  @Test
  void testGetSetIsReady() {
    assertTrue(adminTenant.getIsReady());
  }

  @Test
  void testGetName() {
    assertEquals("ADMIN", adminTenant.getName());
  }

  @Test
  void testGetUrl() throws MalformedURLException {
    assertEquals(URI.create("http://localhost/").toURL(), adminTenant.getUrl());
  }
}
