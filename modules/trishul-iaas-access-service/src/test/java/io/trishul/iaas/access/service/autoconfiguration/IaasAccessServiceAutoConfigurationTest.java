package io.trishul.iaas.access.service.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import io.trishul.crud.service.LockService;
import io.trishul.iaas.access.policy.model.BaseIaasPolicy;
import io.trishul.iaas.access.policy.model.IaasPolicy;
import io.trishul.iaas.access.policy.model.UpdateIaasPolicy;
import io.trishul.iaas.access.role.attachment.policy.BaseIaasRolePolicyAttachment;
import io.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachment;
import io.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachmentId;
import io.trishul.iaas.access.role.attachment.policy.UpdateIaasRolePolicyAttachment;
import io.trishul.iaas.access.role.model.BaseIaasRole;
import io.trishul.iaas.access.role.model.IaasRole;
import io.trishul.iaas.access.role.model.UpdateIaasRole;
import io.trishul.iaas.access.service.policy.service.IaasPolicyService;
import io.trishul.iaas.access.service.role.policy.attachment.service.IaasRolePolicyAttachmentService;
import io.trishul.iaas.access.service.role.service.IaasRoleService;
import io.trishul.iaas.client.IaasClient;
import io.trishul.model.executor.BlockingAsyncExecutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
