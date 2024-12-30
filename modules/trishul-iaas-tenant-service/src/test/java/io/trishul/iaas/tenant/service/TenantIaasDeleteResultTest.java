package io.trishul.iaas.tenant.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import io.trishul.iaas.idp.tenant.model.TenantIaasAuthDeleteResult;
import io.trishul.iaas.idp.tenant.model.TenantIaasIdpDeleteResult;
import io.trishul.iaas.tenant.object.store.TenantIaasVfsDeleteResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TenantIaasDeleteResultTest {
  private TenantIaasDeleteResult result;

  @BeforeEach
  public void init() {
    result = new TenantIaasDeleteResult(new TenantIaasAuthDeleteResult(10),
        new TenantIaasIdpDeleteResult(11), new TenantIaasVfsDeleteResult(12, 13));
  }

  @Test
  public void testGetSetAuth() {
    result.setAuth(new TenantIaasAuthDeleteResult(1));
    assertEquals(new TenantIaasAuthDeleteResult(1), result.getAuth());
  }

  @Test
  public void testGetSetIdp() {
    result.setIdp(new TenantIaasIdpDeleteResult(2));
    assertEquals(new TenantIaasIdpDeleteResult(2), result.getIdp());
  }

  @Test
  public void testGetSetVfs() {
    result.setVfs(new TenantIaasVfsDeleteResult(3, 4));
    assertEquals(new TenantIaasVfsDeleteResult(3, 4), result.getVfs());
  }
}
