package io.trishul.iaas.tenant.object.store.service.service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import org.mockito.InOrder;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import io.trishul.iaas.access.policy.model.IaasPolicy;
import io.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachment;
import io.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachmentId;
import io.trishul.iaas.access.role.model.IaasRole;
import io.trishul.iaas.access.service.policy.service.IaasPolicyService;
import io.trishul.iaas.access.service.role.policy.attachment.service.IaasRolePolicyAttachmentService;
import io.trishul.iaas.idp.tenant.model.IaasIdpTenant;
import io.trishul.iaas.tenant.object.store.TenantIaasVfsResourceMapper;
import io.trishul.iaas.tenant.object.store.TenantIaasVfsResources;
import io.trishul.iaas.tenant.object.store.builder.TenantObjectStoreResourceBuilder;
import io.trishul.object.store.model.IaasObjectStore;
import io.trishul.object.store.service.IaasObjectStoreService;
import io.trishul.object.store.service.cors.config.service.IaasObjectStoreAccessConfigService;
import io.trishul.object.store.service.cors.config.service.IaasObjectStoreCorsConfigService;

public class TenantIaasVfsServiceTest {
  private TenantIaasVfsService service;

  private TenantIaasVfsResourceMapper mResMapper;
  private IaasPolicyService mPolicyService;
  private IaasObjectStoreService mObjectStoreService;
  private IaasRolePolicyAttachmentService mAttachmentService;
  private IaasObjectStoreCorsConfigService mIaasBucketCrossOriginConfigService;
  private IaasObjectStoreAccessConfigService mIaasPublicAccessBlockService;
  private TenantObjectStoreResourceBuilder mBuilder;

  @BeforeEach
  public void init() {
    mResMapper = mock(TenantIaasVfsResourceMapper.class);
    mPolicyService = mock(IaasPolicyService.class);
    mObjectStoreService = mock(IaasObjectStoreService.class);
    mAttachmentService = mock(IaasRolePolicyAttachmentService.class);
    mIaasBucketCrossOriginConfigService = mock(IaasObjectStoreCorsConfigService.class);
    mIaasPublicAccessBlockService = mock(IaasObjectStoreAccessConfigService.class);
    mBuilder = mock(TenantObjectStoreResourceBuilder.class);

    this.service = new TenantIaasVfsService(mResMapper, mPolicyService, mObjectStoreService,
        mAttachmentService, mIaasBucketCrossOriginConfigService, mIaasPublicAccessBlockService,
        mBuilder);

    doAnswer(inv -> new IaasRolePolicyAttachment(inv.getArgument(0, IaasRole.class),
        inv.getArgument(1, IaasPolicy.class))).when(mBuilder).buildAttachment(any(IaasRole.class),
            any(IaasPolicy.class));
    doAnswer(inv -> {
      List<IaasObjectStore> objectStores = inv.getArgument(0, List.class);
      Iterator<IaasPolicy> policies = inv.getArgument(1, List.class).iterator();

      return objectStores.stream()
          .map(objectStore -> new TenantIaasVfsResources(objectStore, policies.next())).toList();
    }).when(mResMapper).fromComponents(anyList(), anyList());
  }

  @Test
  public void testGet_ReturnsListOfVfsResourcesBuiltFromAllServiceResponse_WhenIdpTenantsAreNotNull() {
    Set<String> iaasIdpTenantIds = Set.of("T1", "T2");

    doReturn("POLICY_ID_1").when(mBuilder).getVfsPolicyId("T1");
    doReturn("POLICY_ID_2").when(mBuilder).getVfsPolicyId("T2");
    doReturn("OBJECT_STORE_1").when(mBuilder).getObjectStoreId("T1");
    doReturn("OBJECT_STORE_2").when(mBuilder).getObjectStoreId("T2");

    List<IaasPolicy> mPolicies
        = List.of(new IaasPolicy("VFS_POLICY_1"), new IaasPolicy("VFS_POLICY_2"));
    doReturn(mPolicies).when(mPolicyService).getAll(Set.of("POLICY_ID_1", "POLICY_ID_2"));

    List<IaasObjectStore> mObjectStores
        = List.of(new IaasObjectStore("OBJECT_STORE_1"), new IaasObjectStore("OBJECT_STORE_2"));
    doReturn(mObjectStores).when(mObjectStoreService)
        .getAll(Set.of("OBJECT_STORE_1", "OBJECT_STORE_2"));

    List<TenantIaasVfsResources> expected = List.of(
        new TenantIaasVfsResources(new IaasObjectStore("OBJECT_STORE_1"),
            new IaasPolicy("VFS_POLICY_1")),
        new TenantIaasVfsResources(new IaasObjectStore("OBJECT_STORE_2"),
            new IaasPolicy("VFS_POLICY_2")));

    assertEquals(expected, this.service.get(iaasIdpTenantIds));
  }

  @Test
  public void testAdd_ReturnsListOfVfsResourcesBuiltFromAllServiceResponse_WhenIdpTenantsAreNotNull() {
    List<IaasIdpTenant> iaasIdpTenant = List.of(new IaasIdpTenant("T1"), new IaasIdpTenant("T2"));
    iaasIdpTenant.get(0).setIaasRole(new IaasRole("IAAS_ROLE_1"));
    iaasIdpTenant.get(1).setIaasRole(new IaasRole("IAAS_ROLE_2"));

    doReturn(new IaasObjectStore("OBJECT_STORE_1")).when(mBuilder)
        .buildObjectStore(iaasIdpTenant.get(0));
    doReturn(new IaasObjectStore("OBJECT_STORE_2")).when(mBuilder)
        .buildObjectStore(iaasIdpTenant.get(1));

    doReturn(new IaasPolicy("VFS_POLICY_1")).when(mBuilder).buildVfsPolicy(iaasIdpTenant.get(0));
    doReturn(new IaasPolicy("VFS_POLICY_2")).when(mBuilder).buildVfsPolicy(iaasIdpTenant.get(1));

    doAnswer(inv -> inv.getArgument(0, List.class)).when(mPolicyService).add(anyList());
    doAnswer(inv -> inv.getArgument(0, List.class)).when(mObjectStoreService).add(anyList());
    doAnswer(inv -> inv.getArgument(0, List.class)).when(mAttachmentService).add(anyList());
    doAnswer(inv -> inv.getArgument(0, List.class)).when(mIaasBucketCrossOriginConfigService)
        .add(anyList());
    doAnswer(inv -> inv.getArgument(0, List.class)).when(mIaasPublicAccessBlockService)
        .add(anyList());

    List<TenantIaasVfsResources> expected = List.of(
        new TenantIaasVfsResources(new IaasObjectStore("OBJECT_STORE_1"),
            new IaasPolicy("VFS_POLICY_1")),
        new TenantIaasVfsResources(new IaasObjectStore("OBJECT_STORE_2"),
            new IaasPolicy("VFS_POLICY_2")));

    assertEquals(expected, this.service.add(iaasIdpTenant));
  }

  @Test
  public void testPut_ReturnsListOfVfsResourcesBuiltFromAllServiceResponse_WhenIdpTenantsAreNotNull() {
    List<IaasIdpTenant> iaasIdpTenant = List.of(new IaasIdpTenant("T1"), new IaasIdpTenant("T2"));
    iaasIdpTenant.get(0).setIaasRole(new IaasRole("IAAS_ROLE_1"));
    iaasIdpTenant.get(1).setIaasRole(new IaasRole("IAAS_ROLE_2"));

    doReturn(new IaasObjectStore("OBJECT_STORE_1")).when(mBuilder)
        .buildObjectStore(iaasIdpTenant.get(0));
    doReturn(new IaasObjectStore("OBJECT_STORE_2")).when(mBuilder)
        .buildObjectStore(iaasIdpTenant.get(1));

    doReturn(new IaasPolicy("VFS_POLICY_1")).when(mBuilder).buildVfsPolicy(iaasIdpTenant.get(0));
    doReturn(new IaasPolicy("VFS_POLICY_2")).when(mBuilder).buildVfsPolicy(iaasIdpTenant.get(1));

    doAnswer(inv -> inv.getArgument(0, List.class)).when(mPolicyService).put(anyList());
    doAnswer(inv -> inv.getArgument(0, List.class)).when(mObjectStoreService).put(anyList());
    doAnswer(inv -> inv.getArgument(0, List.class)).when(mAttachmentService).put(anyList());
    doAnswer(inv -> inv.getArgument(0, List.class)).when(mIaasBucketCrossOriginConfigService)
        .put(anyList());
    doAnswer(inv -> inv.getArgument(0, List.class)).when(mIaasPublicAccessBlockService)
        .put(anyList());

    List<TenantIaasVfsResources> expected = List.of(
        new TenantIaasVfsResources(new IaasObjectStore("OBJECT_STORE_1"),
            new IaasPolicy("VFS_POLICY_1")),
        new TenantIaasVfsResources(new IaasObjectStore("OBJECT_STORE_2"),
            new IaasPolicy("VFS_POLICY_2")));

    assertEquals(expected, this.service.put(iaasIdpTenant));
  }

  @Test
  public void testDelete_DelegatesDelete_WhenIdpTenantsAreNotNull() {
    Set<String> iaasIdpTenantIds = Set.of("T1", "T2");

    doReturn(new IaasRolePolicyAttachmentId("ROLE_1", "POLICY_ID_1")).when(mBuilder)
        .buildVfsAttachmentId("T1");
    doReturn(new IaasRolePolicyAttachmentId("ROLE_2", "POLICY_ID_2")).when(mBuilder)
        .buildVfsAttachmentId("T2");
    doReturn("POLICY_ID_1").when(mBuilder).getVfsPolicyId("T1");
    doReturn("POLICY_ID_2").when(mBuilder).getVfsPolicyId("T2");
    doReturn("OBJECT_STORE_1").when(mBuilder).getObjectStoreId("T1");
    doReturn("OBJECT_STORE_2").when(mBuilder).getObjectStoreId("T2");

    this.service.delete(iaasIdpTenantIds);

    InOrder order = inOrder(mAttachmentService, mIaasBucketCrossOriginConfigService,
        mIaasPublicAccessBlockService, mPolicyService, mObjectStoreService);
    order.verify(mAttachmentService, times(1))
        .delete(Set.of(new IaasRolePolicyAttachmentId("ROLE_1", "POLICY_ID_1"),
            new IaasRolePolicyAttachmentId("ROLE_2", "POLICY_ID_2")));
    order.verify(mIaasBucketCrossOriginConfigService, times(1))
        .delete(Set.of("OBJECT_STORE_1", "OBJECT_STORE_2"));
    order.verify(mIaasPublicAccessBlockService, times(1))
        .delete(Set.of("OBJECT_STORE_1", "OBJECT_STORE_2"));
    order.verify(mPolicyService, times(1)).delete(Set.of("POLICY_ID_1", "POLICY_ID_2"));
    order.verify(mObjectStoreService, times(1)).delete(Set.of("OBJECT_STORE_1", "OBJECT_STORE_2"));
  }
}
