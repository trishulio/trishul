package io.trishul.tenant.entity;

import java.net.MalformedURLException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.net.URL;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AdminTenantTest {
  private TenantData adminTenant;

  @BeforeEach
  public void init() throws MalformedURLException {
    adminTenant = new AdminTenant(UUID.fromString("00000000-0000-0000-0000-000000000001"), "ADMIN",
        new URL("http://localhost/"));
  }

  @Test
  public void testGetId() {
    assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000001"), adminTenant.getId());
  }


  @Test
  public void testGetSetIsReady() {
    assertTrue(adminTenant.getIsReady());
  }

  @Test
  public void testGetName() {
    assertEquals("ADMIN", adminTenant.getName());
  }

  @Test
  public void testGetUrl() {
    assertEquals(null, adminTenant.getUrl());
  }
}
