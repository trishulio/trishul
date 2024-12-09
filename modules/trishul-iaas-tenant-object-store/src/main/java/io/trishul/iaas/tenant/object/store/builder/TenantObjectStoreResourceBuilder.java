package io.trishul.iaas.tenant.object.store.builder;

import io.trishul.iaas.access.policy.model.BaseIaasPolicy;
import io.trishul.iaas.access.policy.model.IaasPolicy;
import io.trishul.iaas.access.role.attachment.policy.BaseIaasRolePolicyAttachment;
import io.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachmentId;
import io.trishul.iaas.access.role.model.IaasRole;
import io.trishul.iaas.idp.tenant.model.BaseIaasIdpTenant;
import io.trishul.object.store.configuration.access.model.IaasObjectStoreAccessConfig;
import io.trishul.object.store.configuration.cors.model.IaasObjectStoreCorsConfiguration;
import io.trishul.object.store.model.BaseIaasObjectStore;

public interface TenantObjectStoreResourceBuilder {
    String getVfsPolicyId(String iaasIdpTenantId);
    <P extends BaseIaasPolicy, T extends BaseIaasIdpTenant> P buildVfsPolicy(T iaasIdpTenant);

    String getObjectStoreId(String iaasIdpTenantId);
    <O extends BaseIaasObjectStore, T extends BaseIaasIdpTenant> O buildObjectStore(T iaasIdpTenant);

    IaasRolePolicyAttachmentId buildVfsAttachmentId(String iaasIdpTenantId);
    <A extends BaseIaasRolePolicyAttachment> A buildAttachment(IaasRole role, IaasPolicy policy);

    <T extends BaseIaasIdpTenant> IaasObjectStoreCorsConfiguration buildObjectStoreCorsConfiguration(T iaasIdpTenant);
    <T extends BaseIaasIdpTenant> IaasObjectStoreAccessConfig buildPublicAccessBlock(T iaasIdpTenant);
}
