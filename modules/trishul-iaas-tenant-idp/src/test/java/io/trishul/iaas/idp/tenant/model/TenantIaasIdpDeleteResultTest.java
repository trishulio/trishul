package io.trishul.iaas.idp.tenant.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TenantIaasIdpDeleteResultTest {
  private TenantIaasIdpDeleteResult result;

  @BeforeEach
  void init() {
    result = new TenantIaasIdpDeleteResult(10);
  }

  @Test
  void testAllArgConstructor() {
    assertEquals(10, result.getIdpTenant());
  }

  @Test
  void testGetSetIdpTenant() {
    result.setIdpTenant(1);
    assertEquals(1, result.getIdpTenant());
  }
}
