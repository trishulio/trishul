package io.trishul.iaas.tenant.object.store.builder;

import io.trishul.iaas.access.policy.model.IaasPolicy;
import io.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachment;
import io.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachmentId;
import io.trishul.iaas.access.role.model.IaasRole;
import io.trishul.iaas.idp.tenant.model.BaseIaasIdpTenant;
import io.trishul.object.store.configuration.access.model.IaasObjectStoreAccessConfig;
import io.trishul.object.store.configuration.cors.model.IaasObjectStoreCorsConfiguration;
import io.trishul.object.store.model.IaasObjectStore;

public interface TenantObjectStoreResourceBuilder {
  String getVfsPolicyId(String iaasIdpTenantId);

  IaasPolicy buildVfsPolicy(BaseIaasIdpTenant<?> iaasIdpTenant);

  String getObjectStoreId(String iaasIdpTenantId);

  IaasObjectStore buildObjectStore(BaseIaasIdpTenant<?> iaasIdpTenant);

  IaasRolePolicyAttachmentId buildVfsAttachmentId(String iaasIdpTenantId);

  IaasRolePolicyAttachment buildAttachment(IaasRole role, IaasPolicy policy);

  IaasObjectStoreCorsConfiguration buildObjectStoreCorsConfiguration(
      BaseIaasIdpTenant<?> iaasIdpTenant);

  IaasObjectStoreAccessConfig buildPublicAccessBlock(BaseIaasIdpTenant<?> iaasIdpTenant);
}
