package sh.trishul.iaas.tenant.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.iaas.idp.tenant.model.TenantIaasAuthDeleteResult;
import sh.trishul.iaas.idp.tenant.model.TenantIaasIdpDeleteResult;
import sh.trishul.iaas.tenant.object.store.TenantIaasVfsDeleteResult;

class TenantIaasDeleteResultTest {
  private TenantIaasDeleteResult result;

  @BeforeEach
  void init() {
    result = new TenantIaasDeleteResult(new TenantIaasAuthDeleteResult(10),
        new TenantIaasIdpDeleteResult(11), new TenantIaasVfsDeleteResult(12, 13));
  }

  @Test
  void testGetSetAuth() {
    assertSame(result, result.setAuth(new TenantIaasAuthDeleteResult(1)));
    assertEquals(new TenantIaasAuthDeleteResult(1), result.getAuth());
  }

  @Test
  void testGetSetIdp() {
    assertSame(result, result.setIdp(new TenantIaasIdpDeleteResult(2)));
    assertEquals(new TenantIaasIdpDeleteResult(2), result.getIdp());
  }

  @Test
  void testGetSetVfs() {
    assertSame(result, result.setVfs(new TenantIaasVfsDeleteResult(3, 4)));
    assertEquals(new TenantIaasVfsDeleteResult(3, 4), result.getVfs());
  }
}
