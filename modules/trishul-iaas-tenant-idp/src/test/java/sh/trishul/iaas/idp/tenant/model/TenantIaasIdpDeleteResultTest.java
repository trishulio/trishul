package sh.trishul.iaas.idp.tenant.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

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

  @Test
  void testAccessIdpTenant() throws Exception {
    TenantIaasIdpDeleteResult accessor = new TenantIaasIdpDeleteResult(0L);
    assertSame(accessor, accessor.setIdpTenant(123L));
    assertEquals(123L, accessor.getIdpTenant());
  }

}
