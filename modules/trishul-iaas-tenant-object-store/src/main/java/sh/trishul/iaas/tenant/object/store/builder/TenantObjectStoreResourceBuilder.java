package sh.trishul.iaas.tenant.object.store.builder;

import sh.trishul.iaas.access.policy.model.IaasPolicy;
import sh.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachment;
import sh.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachmentId;
import sh.trishul.iaas.access.role.model.IaasRole;
import sh.trishul.iaas.idp.tenant.model.BaseIaasIdpTenant;
import sh.trishul.object.store.configuration.access.model.IaasObjectStoreAccessConfig;
import sh.trishul.object.store.configuration.cors.model.IaasObjectStoreCorsConfiguration;
import sh.trishul.object.store.model.IaasObjectStore;

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
