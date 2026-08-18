package sh.trishul.iaas.tenant.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.iaas.access.policy.model.IaasPolicy;
import sh.trishul.iaas.access.role.model.IaasRole;
import sh.trishul.iaas.idp.tenant.model.IaasIdpTenant;
import sh.trishul.iaas.idp.tenant.model.TenantIaasAuthResources;
import sh.trishul.iaas.idp.tenant.model.TenantIaasIdpResources;
import sh.trishul.iaas.tenant.object.store.TenantIaasVfsResources;
import sh.trishul.object.store.model.IaasObjectStore;

class TenantIaasResourcesTest {
  private TenantIaasResources resources;

  @BeforeEach
  void init() {
    resources = new TenantIaasResources(new TenantIaasAuthResources(new IaasRole("ROLE")),
        new TenantIaasIdpResources(new IaasIdpTenant("T1")),
        new TenantIaasVfsResources(new IaasObjectStore("OBJECT_STORE"), new IaasPolicy("POLICY")));
  }

  @Test
  void testAllArgConstructor() {
    assertEquals(new TenantIaasAuthResources(new IaasRole("ROLE")), resources.getAuthResources());
    assertEquals(new TenantIaasIdpResources(new IaasIdpTenant("T1")), resources.getIdpResources());
    assertEquals(
        new TenantIaasVfsResources(new IaasObjectStore("OBJECT_STORE"), new IaasPolicy("POLICY")),
        resources.getVfsResources());
  }

  @Test
  void testGetSetAuthResources() {
    assertSame(resources,
        resources.setAuthResources(new TenantIaasAuthResources(new IaasRole("ROLE"))));
    assertEquals(new TenantIaasAuthResources(new IaasRole("ROLE")), resources.getAuthResources());
  }

  @Test
  void testGetSetIdpResources() {
    assertSame(resources,
        resources.setIdpResources(new TenantIaasIdpResources(new IaasIdpTenant("T1"))));
    assertEquals(new TenantIaasIdpResources(new IaasIdpTenant("T1")), resources.getIdpResources());
  }

  @Test
  void testGetSetVfsResources() {
    assertSame(resources, resources.setVfsResources(
        new TenantIaasVfsResources(new IaasObjectStore("OBJECT_STORE"), new IaasPolicy("POLICY"))));
    assertEquals(
        new TenantIaasVfsResources(new IaasObjectStore("OBJECT_STORE"), new IaasPolicy("POLICY")),
        resources.getVfsResources());
  }
}
