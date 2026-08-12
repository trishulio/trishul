package sh.trishul.iaas.access.service.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.crud.service.LockService;
import sh.trishul.iaas.access.policy.model.BaseIaasPolicy;
import sh.trishul.iaas.access.policy.model.IaasPolicy;
import sh.trishul.iaas.access.policy.model.UpdateIaasPolicy;
import sh.trishul.iaas.access.role.attachment.policy.BaseIaasRolePolicyAttachment;
import sh.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachment;
import sh.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachmentId;
import sh.trishul.iaas.access.role.attachment.policy.UpdateIaasRolePolicyAttachment;
import sh.trishul.iaas.access.role.model.BaseIaasRole;
import sh.trishul.iaas.access.role.model.IaasRole;
import sh.trishul.iaas.access.role.model.UpdateIaasRole;
import sh.trishul.iaas.access.service.policy.service.IaasPolicyService;
import sh.trishul.iaas.access.service.role.policy.attachment.service.IaasRolePolicyAttachmentService;
import sh.trishul.iaas.access.service.role.service.IaasRoleService;
import sh.trishul.iaas.client.IaasClient;
import sh.trishul.model.executor.BlockingAsyncExecutor;

class IaasAccessServiceAutoConfigurationTest {
  private IaasAccessServiceAutoConfiguration config;

  private LockService mLockService;
  private BlockingAsyncExecutor mExecutor;

  @BeforeEach
  void init() {
    config = new IaasAccessServiceAutoConfiguration();
    mLockService = mock(LockService.class);
    mExecutor = mock(BlockingAsyncExecutor.class);
  }

  @Test
  void testIaasRoleService_ReturnsNonNullInstance() {
    @SuppressWarnings("unchecked")
    IaasClient<String, IaasRole, BaseIaasRole<?>, UpdateIaasRole<?>> mRoleClient
        = mock(IaasClient.class);

    IaasRoleService service = config.iaasRoleService(mLockService, mExecutor, mRoleClient);

    assertNotNull(service);
  }

  @Test
  void testIaasRoleService_ReturnsInstanceOfIaasRoleService() {
    @SuppressWarnings("unchecked")
    IaasClient<String, IaasRole, BaseIaasRole<?>, UpdateIaasRole<?>> mRoleClient
        = mock(IaasClient.class);

    IaasRoleService service = config.iaasRoleService(mLockService, mExecutor, mRoleClient);

    assertTrue(service instanceof IaasRoleService);
  }

  @Test
  void testIaasPolicyService_ReturnsNonNullInstance() {
    @SuppressWarnings("unchecked")
    IaasClient<String, IaasPolicy, BaseIaasPolicy<?>, UpdateIaasPolicy<?>> mPolicyClient
        = mock(IaasClient.class);

    IaasPolicyService service = config.iaasPolicyService(mLockService, mExecutor, mPolicyClient);

    assertNotNull(service);
  }

  @Test
  void testIaasPolicyService_ReturnsInstanceOfIaasPolicyService() {
    @SuppressWarnings("unchecked")
    IaasClient<String, IaasPolicy, BaseIaasPolicy<?>, UpdateIaasPolicy<?>> mPolicyClient
        = mock(IaasClient.class);

    IaasPolicyService service = config.iaasPolicyService(mLockService, mExecutor, mPolicyClient);

    assertTrue(service instanceof IaasPolicyService);
  }

  @Test
  void testIaasRolePolicyAttachmentService_ReturnsNonNullInstance() {
    @SuppressWarnings("unchecked")
    IaasClient<IaasRolePolicyAttachmentId, IaasRolePolicyAttachment, BaseIaasRolePolicyAttachment<?>, UpdateIaasRolePolicyAttachment<?>> mAttachmentClient
        = mock(IaasClient.class);

    IaasRolePolicyAttachmentService service
        = config.iaasRolePolicyAttachmentService(mLockService, mExecutor, mAttachmentClient);

    assertNotNull(service);
  }

  @Test
  void testIaasRolePolicyAttachmentService_ReturnsInstanceOfIaasRolePolicyAttachmentService() {
    @SuppressWarnings("unchecked")
    IaasClient<IaasRolePolicyAttachmentId, IaasRolePolicyAttachment, BaseIaasRolePolicyAttachment<?>, UpdateIaasRolePolicyAttachment<?>> mAttachmentClient
        = mock(IaasClient.class);

    IaasRolePolicyAttachmentService service
        = config.iaasRolePolicyAttachmentService(mLockService, mExecutor, mAttachmentClient);

    assertTrue(service instanceof IaasRolePolicyAttachmentService);
  }
}
