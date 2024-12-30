package io.trishul.iaas.tenant.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import io.trishul.iaas.access.policy.model.IaasPolicy;
import io.trishul.iaas.access.role.model.IaasRole;
import io.trishul.iaas.idp.tenant.model.BaseIaasIdpTenant;
import io.trishul.iaas.idp.tenant.model.IaasIdpTenant;
import io.trishul.iaas.idp.tenant.model.TenantIaasAuthDeleteResult;
import io.trishul.iaas.idp.tenant.model.TenantIaasAuthResources;
import io.trishul.iaas.idp.tenant.model.TenantIaasIdpDeleteResult;
import io.trishul.iaas.idp.tenant.model.TenantIaasIdpResources;
import io.trishul.iaas.idp.tenant.model.TenantIaasIdpTenantMapper;
import io.trishul.iaas.idp.tenant.model.UpdateIaasIdpTenant;
import io.trishul.iaas.tenant.idp.management.service.TenantIaasAuthService;
import io.trishul.iaas.tenant.idp.management.service.TenantIaasIdpService;
import io.trishul.iaas.tenant.object.store.TenantIaasVfsDeleteResult;
import io.trishul.iaas.tenant.object.store.TenantIaasVfsResources;
import io.trishul.iaas.tenant.object.store.service.service.TenantIaasVfsService;
import io.trishul.iaas.tenant.resource.TenantIaasResources;
import io.trishul.object.store.model.IaasObjectStore;
import io.trishul.tenant.entity.Tenant;

public class TenantIaasServiceTest {
  private TenantIaasService service;

  private TenantIaasAuthService mAuthService;
  private TenantIaasIdpService mIdpService;
  private TenantIaasVfsService mVfsService;

  @BeforeEach
  public void init() {
    mAuthService = mock(TenantIaasAuthService.class);
    mIdpService = mock(TenantIaasIdpService.class);
    mVfsService = mock(TenantIaasVfsService.class);

    this.service = new TenantIaasService(mAuthService, mIdpService, mVfsService,
        TenantIaasIdpTenantMapper.INSTANCE);
  }

  @Test
  public void testGet_GetsAndReturnsListTenantIaasResourcesFromIndividualResources() {
    Set<String> iaasIdpTenantIds
        = Set.of("00000000-0000-0000-0000-000000000001", "00000000-0000-0000-0000-000000000002");

    List<TenantIaasAuthResources> authResources
        = List.of(new TenantIaasAuthResources(new IaasRole("T1_ROLE")),
            new TenantIaasAuthResources(new IaasRole("T2_ROLE")));
    doReturn(authResources).when(mAuthService).get(iaasIdpTenantIds);

    List<TenantIaasIdpResources> idpResources
        = List.of(new TenantIaasIdpResources(new IaasIdpTenant("IDP_T1")),
            new TenantIaasIdpResources(new IaasIdpTenant("IDP_T2")));
    doReturn(idpResources).when(mIdpService).get(iaasIdpTenantIds);

    List<TenantIaasVfsResources> vfsResources = List.of(
        new TenantIaasVfsResources(new IaasObjectStore("T1_OBJECT_STORE"),
            new IaasPolicy("T1_POLICY")),
        new TenantIaasVfsResources(new IaasObjectStore("T2_OBJECT_STORE"),
            new IaasPolicy("T2_POLICY")));
    doReturn(vfsResources).when(mVfsService).get(iaasIdpTenantIds);

    Set<UUID> tenantIds = Set.of(UUID.fromString("00000000-0000-0000-0000-000000000001"),
        UUID.fromString("00000000-0000-0000-0000-000000000002"));
    List<TenantIaasResources> resources = service.get(tenantIds);

    List<TenantIaasResources> expected = List.of(
        new TenantIaasResources(new TenantIaasAuthResources(new IaasRole("T1_ROLE")),
            new TenantIaasIdpResources(new IaasIdpTenant("IDP_T1")),
            new TenantIaasVfsResources(new IaasObjectStore("T1_OBJECT_STORE"),
                new IaasPolicy("T1_POLICY"))),
        new TenantIaasResources(new TenantIaasAuthResources(new IaasRole("T2_ROLE")),
            new TenantIaasIdpResources(new IaasIdpTenant("IDP_T2")), new TenantIaasVfsResources(
                new IaasObjectStore("T2_OBJECT_STORE"), new IaasPolicy("T2_POLICY"))));

    assertEquals(expected, resources);
  }

  @Test
  public void testAdd_AddsAndReturnsListTenantIaasResourcesFromIndividualResources() {
    List<TenantIaasAuthResources> authResources
        = List.of(new TenantIaasAuthResources(new IaasRole("T1_ROLE")),
            new TenantIaasAuthResources(new IaasRole("T2_ROLE")));
    doReturn(authResources).when(mAuthService)
        .add(List.of(new IaasIdpTenant("00000000-0000-0000-0000-000000000001"),
            new IaasIdpTenant("00000000-0000-0000-0000-000000000002")));

    List<BaseIaasIdpTenant<?>> idpTenants
        = List.of(new IaasIdpTenant("00000000-0000-0000-0000-000000000001"),
            new IaasIdpTenant("00000000-0000-0000-0000-000000000002"));
    idpTenants.get(0).setIaasRole(new IaasRole("T1_ROLE"));
    idpTenants.get(1).setIaasRole(new IaasRole("T2_ROLE"));

    List<TenantIaasIdpResources> idpResources
        = List.of(new TenantIaasIdpResources(new IaasIdpTenant("IDP_T1")),
            new TenantIaasIdpResources(new IaasIdpTenant("IDP_T2")));
    doReturn(idpResources).when(mIdpService).add(idpTenants);

    List<TenantIaasVfsResources> vfsResources = List.of(
        new TenantIaasVfsResources(new IaasObjectStore("T1_OBJECT_STORE"),
            new IaasPolicy("T1_POLICY")),
        new TenantIaasVfsResources(new IaasObjectStore("T2_OBJECT_STORE"),
            new IaasPolicy("T2_POLICY")));
    doReturn(vfsResources).when(mVfsService).add(idpTenants);

    List<Tenant> tenants
        = List.of(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")),
            new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000002")));
    List<TenantIaasResources> resources = service.add(tenants);

    List<TenantIaasResources> expected = List.of(
        new TenantIaasResources(new TenantIaasAuthResources(new IaasRole("T1_ROLE")),
            new TenantIaasIdpResources(new IaasIdpTenant("IDP_T1")),
            new TenantIaasVfsResources(new IaasObjectStore("T1_OBJECT_STORE"),
                new IaasPolicy("T1_POLICY"))),
        new TenantIaasResources(new TenantIaasAuthResources(new IaasRole("T2_ROLE")),
            new TenantIaasIdpResources(new IaasIdpTenant("IDP_T2")), new TenantIaasVfsResources(
                new IaasObjectStore("T2_OBJECT_STORE"), new IaasPolicy("T2_POLICY"))));

    assertEquals(expected, resources);
  }

  @Test
  public void testPut_PutsAndReturnsListTenantIaasResourcesFromIndividualResources() {
    List<TenantIaasAuthResources> authResources
        = List.of(new TenantIaasAuthResources(new IaasRole("T1_ROLE")),
            new TenantIaasAuthResources(new IaasRole("T2_ROLE")));
    doReturn(authResources).when(mAuthService)
        .put(List.of(new IaasIdpTenant("00000000-0000-0000-0000-000000000001"),
            new IaasIdpTenant("00000000-0000-0000-0000-000000000002")));

    List<UpdateIaasIdpTenant<?>> idpTenants
        = List.of(new IaasIdpTenant("00000000-0000-0000-0000-000000000001"),
            new IaasIdpTenant("00000000-0000-0000-0000-000000000002"));
    idpTenants.get(0).setIaasRole(new IaasRole("T1_ROLE"));
    idpTenants.get(1).setIaasRole(new IaasRole("T2_ROLE"));

    List<TenantIaasIdpResources> idpResources
        = List.of(new TenantIaasIdpResources(new IaasIdpTenant("IDP_T1")),
            new TenantIaasIdpResources(new IaasIdpTenant("IDP_T2")));
    doReturn(idpResources).when(mIdpService).put(idpTenants);

    List<TenantIaasVfsResources> vfsResources = List.of(
        new TenantIaasVfsResources(new IaasObjectStore("T1_OBJECT_STORE"),
            new IaasPolicy("T1_POLICY")),
        new TenantIaasVfsResources(new IaasObjectStore("T2_OBJECT_STORE"),
            new IaasPolicy("T2_POLICY")));
    doReturn(vfsResources).when(mVfsService).put(idpTenants);

    List<Tenant> tenants
        = List.of(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")),
            new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000002")));
    List<TenantIaasResources> resources = service.put(tenants);

    List<TenantIaasResources> expected = List.of(
        new TenantIaasResources(new TenantIaasAuthResources(new IaasRole("T1_ROLE")),
            new TenantIaasIdpResources(new IaasIdpTenant("IDP_T1")),
            new TenantIaasVfsResources(new IaasObjectStore("T1_OBJECT_STORE"),
                new IaasPolicy("T1_POLICY"))),
        new TenantIaasResources(new TenantIaasAuthResources(new IaasRole("T2_ROLE")),
            new TenantIaasIdpResources(new IaasIdpTenant("IDP_T2")), new TenantIaasVfsResources(
                new IaasObjectStore("T2_OBJECT_STORE"), new IaasPolicy("T2_POLICY"))));

    assertEquals(expected, resources);
  }

  @Test
  public void testDelete_DeletesAndReturnsCombinedDeleteResult() {
    Set<String> idpTenantIds
        = Set.of("00000000-0000-0000-0000-000000000001", "00000000-0000-0000-0000-000000000002");

    doReturn(new TenantIaasAuthDeleteResult(3)).when(mAuthService).delete(idpTenantIds);
    doReturn(new TenantIaasIdpDeleteResult(4)).when(mIdpService).delete(idpTenantIds);
    doReturn(new TenantIaasVfsDeleteResult(5, 6)).when(mVfsService).delete(idpTenantIds);

    Set<UUID> tenantIds = Set.of(UUID.fromString("00000000-0000-0000-0000-000000000001"),
        UUID.fromString("00000000-0000-0000-0000-000000000002"));
    TenantIaasDeleteResult result = service.delete(tenantIds);

    TenantIaasDeleteResult expected = new TenantIaasDeleteResult(new TenantIaasAuthDeleteResult(3),
        new TenantIaasIdpDeleteResult(4), new TenantIaasVfsDeleteResult(5, 6));
    assertEquals(expected, result);
  }
}
