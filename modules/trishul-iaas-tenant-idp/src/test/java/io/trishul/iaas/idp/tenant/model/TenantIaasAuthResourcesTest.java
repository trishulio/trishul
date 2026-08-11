package io.trishul.iaas.idp.tenant.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

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

  @Test
  void testAccessRole() throws Exception {
    TenantIaasAuthResources accessor = new TenantIaasAuthResources();
    IaasRole value = new IaasRole();
    assertSame(accessor, accessor.setRole(value));
    assertEquals(value, accessor.getRole());
  }

  @Test
  void testSetRole_NullValues() {
    resources.setRole(new IaasRole("ROLE"));
    resources.setRole(null);
    assertNull(resources.getRole());
  }
}
