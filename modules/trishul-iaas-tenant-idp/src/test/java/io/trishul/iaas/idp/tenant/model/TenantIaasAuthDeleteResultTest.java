package io.trishul.iaas.idp.tenant.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TenantIaasAuthDeleteResultTest {
  private TenantIaasAuthDeleteResult result;

  @BeforeEach
  void init() {
    result = new TenantIaasAuthDeleteResult(10);
  }

  @Test
  void testAllArgConstructor() {
    assertEquals(10, result.getRoles());
  }

  @Test
  void testGetSetRoles() {
    result.setRoles(1);
    assertEquals(1, result.getRoles());
  }
}
