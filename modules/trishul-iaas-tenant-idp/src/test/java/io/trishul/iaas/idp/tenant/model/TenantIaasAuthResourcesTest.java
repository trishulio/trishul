package io.trishul.iaas.idp.tenant.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import io.trishul.iaas.access.role.model.IaasRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TenantIaasAuthResourcesTest {
  private TenantIaasAuthResources resources;

  @BeforeEach
  void init() {
    resources = new TenantIaasAuthResources();
  }

  @Test
  void testNoArgConstructor() {
    assertNull(resources.getRole());
  }

  @Test
  void testAllArgConstructor() {
    resources = new TenantIaasAuthResources(new IaasRole());
    assertEquals(new IaasRole(), resources.getRole());
  }

  @Test
  void testGetSetRole() {
    resources.setRole(new IaasRole("ROLE"));
    assertEquals(new IaasRole("ROLE"), resources.getRole());
  }
}
