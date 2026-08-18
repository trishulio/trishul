package sh.trishul.iaas.idp.tenant.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

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

  @Test
  void testAccessRoles() throws Exception {
    TenantIaasAuthDeleteResult accessor = new TenantIaasAuthDeleteResult(0L);
    assertSame(accessor, accessor.setRoles(123L));
    assertEquals(123L, accessor.getRoles());
  }

}
