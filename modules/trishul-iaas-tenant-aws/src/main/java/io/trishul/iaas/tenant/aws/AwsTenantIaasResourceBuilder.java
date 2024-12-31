package io.trishul.iaas.tenant.aws;

import com.amazonaws.services.s3.model.BucketCrossOriginConfiguration;
import com.amazonaws.services.s3.model.CORSRule;
import com.amazonaws.services.s3.model.CORSRule.AllowedMethods;
import com.amazonaws.services.s3.model.PublicAccessBlockConfiguration;
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
import java.util.List;

public class AwsTenantIaasResourceBuilder
    implements TenantIaasResourceBuilder, TenantObjectStoreResourceBuilder {
  private final AwsDocumentTemplates templates;

  private final List<String> allowedHeaders;
  private final List<String> allowedMethods;
  private final List<String> allowedOrigins;
  private final boolean blockPublicAcls;
  private final boolean ignorePublicAcls;
  private final boolean blockPublicPolicy;
  private final boolean restrictPublicBuckets;

  public AwsTenantIaasResourceBuilder(AwsDocumentTemplates templates, List<String> allowedHeaders,
      List<String> allowedMethods, List<String> allowedOrigins, boolean blockPublicAcls,
      boolean ignorePublicAcls, boolean blockPublicPolicy, boolean restrictPublicBuckets) {
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
  public BaseIaasRole<?> buildRole(BaseIaasIdpTenant<?> iaasIdpTenant) {
    String iaasIdpTenantId = iaasIdpTenant.getName();
    return new IaasRole().setName(this.templates.getTenantIaasRoleName(iaasIdpTenantId))
        .setDescription(this.templates.getTenantIaasRoleDescription(iaasIdpTenantId))
        .setAssumePolicyDocument(this.templates.getCognitoIdAssumeRolePolicyDoc());
  }


  @Override
  public String getVfsPolicyId(String iaasIdpTenantId) {
    return this.templates.getTenantVfsPolicyName(iaasIdpTenantId);
  }

  @Override
  public BaseIaasPolicy<?> buildVfsPolicy(BaseIaasIdpTenant<?> iaasIdpTenant) {
    String iaasIdpTenantId = iaasIdpTenant.getName();

    return new IaasPolicy().setName(this.templates.getTenantVfsPolicyName(iaasIdpTenantId))
        .setDescription(this.templates.getTenantVfsPolicyDescription(iaasIdpTenantId))
        .setDocument(this.templates.getTenantBucketPolicyDoc(iaasIdpTenantId));
  }

  @Override
  public String getObjectStoreId(String iaasIdpTenantId) {
    return this.templates.getTenantVfsBucketName(iaasIdpTenantId);
  }

  @Override
  public BaseIaasObjectStore<?> buildObjectStore(BaseIaasIdpTenant<?> iaasIdpTenant) {
    String iaasIdpTenantId = iaasIdpTenant.getName();
    return new IaasObjectStore().setName(this.templates.getTenantVfsBucketName(iaasIdpTenantId));
  }

  @Override
  public IaasRolePolicyAttachmentId buildVfsAttachmentId(String iaasIdpTenantId) {
    return new IaasRolePolicyAttachmentId()
        .setPolicyId(this.templates.getTenantVfsPolicyName(iaasIdpTenantId))
        .setRoleId(this.templates.getTenantIaasRoleName(iaasIdpTenantId));
  }

  @Override
  public BaseIaasRolePolicyAttachment<?> buildAttachment(IaasRole role, IaasPolicy policy) {
    return new IaasRolePolicyAttachment().setIaasRole(role).setIaasPolicy(policy);
  }

  @Override
  public IaasObjectStoreCorsConfiguration buildObjectStoreCorsConfiguration(
      BaseIaasIdpTenant<?> iaasIdpTenant) {
    List<String> sanitizedOrigins
        = allowedOrigins.stream().map(o -> o.replaceAll("/*$", "")).toList();

    CORSRule corsRule = new CORSRule().withAllowedHeaders(allowedHeaders)
        .withAllowedMethods(
            allowedMethods.stream().map(method -> AllowedMethods.valueOf(method)).toList())
        .withAllowedOrigins(sanitizedOrigins);

    List<CORSRule> corsRules = List.of(corsRule);

    String bucketName = this.getObjectStoreId(iaasIdpTenant.getName());

    return new IaasObjectStoreCorsConfiguration(bucketName,
        new BucketCrossOriginConfiguration(corsRules));
  }

  @Override
  public IaasObjectStoreAccessConfig buildPublicAccessBlock(BaseIaasIdpTenant<?> iaasIdpTenant) {
    PublicAccessBlockConfiguration publicAccessBlockConfiguration
        = new PublicAccessBlockConfiguration().withBlockPublicAcls(blockPublicAcls)
            .withBlockPublicPolicy(blockPublicPolicy).withIgnorePublicAcls(ignorePublicAcls)
            .withRestrictPublicBuckets(restrictPublicBuckets);

    String bucketName = this.getObjectStoreId(iaasIdpTenant.getName());
    return new IaasObjectStoreAccessConfig(bucketName, publicAccessBlockConfiguration);
  }
}
