package io.trishul.iaas.access.aws.autoconfiguration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClient;
import io.trishul.iaas.access.aws.AwsArnMapper;
import io.trishul.iaas.access.aws.AwsIaasPolicyMapper;
import io.trishul.iaas.access.aws.AwsIaasRoleMapper;
import io.trishul.iaas.access.aws.AwsIamPolicyClient;
import io.trishul.iaas.access.aws.AwsIamRoleClient;
import io.trishul.iaas.access.aws.AwsIamRolePolicyAttachmentClient;
import io.trishul.iaas.access.aws.IaasAccessAwsFactory;
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
import io.trishul.iaas.client.IaasClient;

@Configuration
public class IaasAccessAwsAutoConfiguration {
  @Bean
  @ConditionalOnMissingBean(IaasAccessAwsFactory.class)
  public IaasAccessAwsFactory iaasAccessAwsFactory() {
    return new IaasAccessAwsFactory();
  }

  @Bean
  @ConditionalOnMissingBean(AmazonIdentityManagement.class)
  public AmazonIdentityManagement iamClient(IaasAccessAwsFactory iaasAccessAwsFactory,
      @Value("${aws.iam.access-key}") String iamAccessKeyId,
      @Value("${aws.iam.access-secret}") String iamSecret) {
    return iaasAccessAwsFactory.iamClient(iamAccessKeyId, iamSecret);
  }

  @Bean
  @ConditionalOnMissingBean(AwsArnMapper.class)
  public AwsArnMapper arnMapper(@Value("${aws.deployment.accountId}") String accountId,
      @Value("${aws.deployment.parition}") String partition) {
    return new AwsArnMapper(accountId, partition);
  }

  @Bean
  @ConditionalOnMissingBean(AwsIamPolicyClient.class)
  public IaasClient<String, IaasPolicy, BaseIaasPolicy<?>, UpdateIaasPolicy<?>> iaasPolicyClient(
      AmazonIdentityManagement awsIamClient, AwsArnMapper awsMapper) {
    return new AwsIamPolicyClient(awsIamClient, awsMapper, AwsIaasPolicyMapper.INSTANCE);
  }

  @Bean
  @ConditionalOnMissingBean(AwsIamRoleClient.class)
  public IaasClient<String, IaasRole, BaseIaasRole<?>, UpdateIaasRole<?>> iaasRoleClient(
      AmazonIdentityManagement awsIamClient) {
    return new AwsIamRoleClient(awsIamClient, AwsIaasRoleMapper.INSTANCE);
  }

  @Bean
  @ConditionalOnMissingBean(AwsIamRolePolicyAttachmentClient.class)
  public IaasClient<IaasRolePolicyAttachmentId, IaasRolePolicyAttachment, BaseIaasRolePolicyAttachment<?>, UpdateIaasRolePolicyAttachment<?>> awsIamRolePolicyClientClient(
      AmazonIdentityManagementClient iamClient, AwsArnMapper arnMapper) {
    return new AwsIamRolePolicyAttachmentClient(iamClient, arnMapper);
  }
}

