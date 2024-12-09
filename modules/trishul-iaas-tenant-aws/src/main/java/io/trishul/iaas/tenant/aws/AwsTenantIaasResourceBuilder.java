package io.trishul.iaas.tenant.aws;

import java.util.List;

import com.amazonaws.services.s3.model.BucketCrossOriginConfiguration;
import com.amazonaws.services.s3.model.CORSRule;
import com.amazonaws.services.s3.model.CORSRule.AllowedMethods;

import io.trishul.iaas.access.policy.model.BaseIaasPolicy;
import io.trishul.iaas.access.policy.model.IaasPolicy;
import io.trishul.iaas.access.role.attachment.policy.BaseIaasRolePolicyAttachment;
import io.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachment;
import io.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachmentId;
import io.trishul.iaas.access.role.model.BaseIaasRole;
import io.trishul.iaas.access.role.model.IaasRole;
import io.trishul.iaas.idp.tenant.model.BaseIaasIdpTenant;
import io.trishul.iaas.tenant.object.store.builder.TenantObjectStoreResourceBuilder;
import io.trishul.iaas.tenant.resource.TenantIaasResourceBuilder;
import io.trishul.object.store.configuration.access.model.IaasObjectStoreAccessConfig;
import io.trishul.object.store.configuration.cors.model.IaasObjectStoreCorsConfiguration;
import io.trishul.object.store.model.BaseIaasObjectStore;
import io.trishul.object.store.model.IaasObjectStore;

import com.amazonaws.services.s3.model.PublicAccessBlockConfiguration;

public class AwsTenantIaasResourceBuilder implements TenantIaasResourceBuilder, TenantObjectStoreResourceBuilder {
    private final AwsDocumentTemplates templates;

    private final List<String> allowedHeaders;
    private final List<String> allowedMethods;
    private final List<String> allowedOrigins;
    private final boolean blockPublicAcls;
    private final boolean ignorePublicAcls;
    private final boolean blockPublicPolicy;
    private final boolean restrictPublicBuckets;

    public AwsTenantIaasResourceBuilder(AwsDocumentTemplates templates, List<String> allowedHeaders, List<String> allowedMethods, List<String> allowedOrigins, boolean blockPublicAcls, boolean ignorePublicAcls, boolean blockPublicPolicy, boolean restrictPublicBuckets) {
        this.templates = templates;
        this.allowedHeaders = allowedHeaders;
        this.allowedMethods = allowedMethods;
        this.allowedOrigins = allowedOrigins;
        this.blockPublicAcls = blockPublicAcls;
        this.ignorePublicAcls = ignorePublicAcls;
        this.blockPublicPolicy = blockPublicPolicy;
        this.restrictPublicBuckets = restrictPublicBuckets;
    }

    @Override
    public String getRoleId(String iaasIdpTenantId) {
        return this.templates.getTenantIaasRoleName(iaasIdpTenantId);
    }

    @Override
    public <R extends BaseIaasRole, T extends BaseIaasIdpTenant> R buildRole(T iaasIdpTenant) {
        String iaasIdpTenantId = iaasIdpTenant.getName();
        @SuppressWarnings("unchecked")
        R role = (R) new IaasRole();
        role.setName(this.templates.getTenantIaasRoleName(iaasIdpTenantId));
        role.setDescription(this.templates.getTenantIaasRoleDescription(iaasIdpTenantId));
        role.setAssumePolicyDocument(this.templates.getCognitoIdAssumeRolePolicyDoc());

        return role;
    }

    @Override
    public String getVfsPolicyId(String iaasIdpTenantId) {
        return this.templates.getTenantVfsPolicyName(iaasIdpTenantId);
    }

    @Override
    public <P extends BaseIaasPolicy, T extends BaseIaasIdpTenant> P buildVfsPolicy(T iaasIdpTenant) {
        String iaasIdpTenantId = iaasIdpTenant.getName();
        @SuppressWarnings("unchecked")
        P policy = (P) new IaasPolicy();
        policy.setName(this.templates.getTenantVfsPolicyName(iaasIdpTenantId));
        policy.setDescription(this.templates.getTenantVfsPolicyDescription(iaasIdpTenantId));
        policy.setDocument(this.templates.getTenantBucketPolicyDoc(iaasIdpTenantId));

        return policy;
    }

    @Override
    public String getObjectStoreId(String iaasIdpTenantId) {
        return this.templates.getTenantVfsBucketName(iaasIdpTenantId);
    }

    @Override
    public <O extends BaseIaasObjectStore, T extends BaseIaasIdpTenant> O buildObjectStore(T iaasIdpTenant) {
        String iaasIdpTenantId = iaasIdpTenant.getName();
        @SuppressWarnings("unchecked")
        O objectStore = (O) new IaasObjectStore();
        objectStore.setName(this.templates.getTenantVfsBucketName(iaasIdpTenantId));

        return objectStore;
    }

    @Override
    public IaasRolePolicyAttachmentId buildVfsAttachmentId(String iaasIdpTenantId) {
        IaasRolePolicyAttachmentId id = new IaasRolePolicyAttachmentId();
        id.setPolicyId(this.templates.getTenantVfsPolicyName(iaasIdpTenantId));
        id.setRoleId(this.templates.getTenantIaasRoleName(iaasIdpTenantId));

        return id;
    }

    @Override
    public <A extends BaseIaasRolePolicyAttachment> A buildAttachment(IaasRole role, IaasPolicy policy) {
        @SuppressWarnings("unchecked")
        A attachment = (A) new IaasRolePolicyAttachment();

        attachment.setIaasRole(role);
        attachment.setIaasPolicy(policy);

        return attachment;
    }

    @Override
    public <T extends BaseIaasIdpTenant> IaasObjectStoreCorsConfiguration buildObjectStoreCorsConfiguration(T iaasIdpTenant) {
        List<String> sanitizedOrigins = allowedOrigins.stream().map(o -> o.replaceAll("/*$", "")).toList();

        CORSRule corsRule = new CORSRule().withAllowedHeaders(allowedHeaders)
                                          .withAllowedMethods(allowedMethods.stream().map(method -> AllowedMethods.valueOf(method)).toList())
                                          .withAllowedOrigins(sanitizedOrigins);

        List<CORSRule> corsRules = List.of(corsRule);

        String bucketName = this.getObjectStoreId(iaasIdpTenant.getName());

        return new IaasObjectStoreCorsConfiguration(bucketName, new BucketCrossOriginConfiguration(corsRules));
    }

    @Override
    public <T extends BaseIaasIdpTenant> IaasObjectStoreAccessConfig buildPublicAccessBlock(T iaasIdpTenant) {
        PublicAccessBlockConfiguration publicAccessBlockConfiguration = new PublicAccessBlockConfiguration().withBlockPublicAcls(blockPublicAcls)
                                                                                                            .withBlockPublicPolicy(blockPublicPolicy)
                                                                                                            .withIgnorePublicAcls(ignorePublicAcls)
                                                                                                            .withRestrictPublicBuckets(restrictPublicBuckets);

        String bucketName = this.getObjectStoreId(iaasIdpTenant.getName());
        return new IaasObjectStoreAccessConfig(bucketName, publicAccessBlockConfiguration);
    }
}
