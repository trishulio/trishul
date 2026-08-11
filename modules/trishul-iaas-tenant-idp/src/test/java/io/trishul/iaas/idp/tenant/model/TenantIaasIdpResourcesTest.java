package io.trishul.iaas.idp.tenant.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TenantIaasIdpResourcesTest {
  private TenantIaasIdpResources resources;

  @BeforeEach
  void init() {
    resources = new TenantIaasIdpResources();
  }

  @Test
  void testNoArgConstructor() {
    assertNull(resources.getIaasIdpTenant());
  }

  @Test
  void testAllArgConstructor() {
    resources = new TenantIaasIdpResources(new IaasIdpTenant());
    assertEquals(new IaasIdpTenant(), resources.getIaasIdpTenant());
  }

  @Test
  void testGetSetIdpTenant() {
    resources.setIaasIdpTenant(new IaasIdpTenant("ROLE"));
    assertEquals(new IaasIdpTenant("ROLE"), resources.getIaasIdpTenant());
  }

  @Test
  void testAccessIaasIdpTenant() throws Exception {
    TenantIaasIdpResources accessor = new TenantIaasIdpResources();
    IaasIdpTenant value = new IaasIdpTenant();
    assertSame(accessor, accessor.setIaasIdpTenant(value));
    assertEquals(value, accessor.getIaasIdpTenant());
  }

  @Test
  void testSetIaasIdpTenant_NullValues() {
    resources.setIaasIdpTenant(new IaasIdpTenant("ROLE"));
    resources.setIaasIdpTenant(null);
    assertNull(resources.getIaasIdpTenant());
  }

}
