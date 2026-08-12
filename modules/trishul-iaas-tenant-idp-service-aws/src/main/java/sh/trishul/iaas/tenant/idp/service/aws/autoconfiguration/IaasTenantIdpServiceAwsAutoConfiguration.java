package sh.trishul.iaas.tenant.idp.service.aws.autoconfiguration;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sh.trishul.iaas.access.aws.AwsArnMapper;
import sh.trishul.iaas.access.service.role.service.IaasRoleService;
import sh.trishul.iaas.client.IaasClient;
import sh.trishul.iaas.idp.tenant.model.BaseIaasIdpTenant;
import sh.trishul.iaas.idp.tenant.model.IaasIdpTenant;
import sh.trishul.iaas.idp.tenant.model.UpdateIaasIdpTenant;
import sh.trishul.iaas.tenant.idp.service.aws.cognito.client.AwsGroupTypeMapper;
import sh.trishul.iaas.tenant.idp.service.aws.cognito.client.AwsIaasUserTenantMembershipClient;
import sh.trishul.iaas.tenant.idp.service.aws.cognito.client.AwsIdpTenantWithRoleClient;
import sh.trishul.iaas.user.model.BaseIaasUserTenantMembership;
import sh.trishul.iaas.user.model.IaasUserTenantMembership;
import sh.trishul.iaas.user.model.IaasUserTenantMembershipId;
import sh.trishul.iaas.user.model.UpdateIaasUserTenantMembership;

@Configuration
public class IaasTenantIdpServiceAwsAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(AwsIdpTenantWithRoleClient.class)
  public IaasClient<String, IaasIdpTenant, BaseIaasIdpTenant<?>, UpdateIaasIdpTenant<?>> iaasIdpTenantClient(
      AWSCognitoIdentityProvider awsCognitoIdpProvider,
      @Value("${aws.cognito.user-pool.id}") String userPoolId, AwsArnMapper arnMapper,
      IaasRoleService roleService) {
    return new AwsIdpTenantWithRoleClient(awsCognitoIdpProvider, userPoolId,
        AwsGroupTypeMapper.INSTANCE, arnMapper, roleService);
  }

  @Bean
  @ConditionalOnMissingBean(AwsIaasUserTenantMembershipClient.class)
  public IaasClient<IaasUserTenantMembershipId, IaasUserTenantMembership, BaseIaasUserTenantMembership<?>, UpdateIaasUserTenantMembership<?>> awsCognitoUserGroupMembership(
      AWSCognitoIdentityProvider idp, @Value("${aws.cognito.user-pool.id}") String userPoolId) {
    return new AwsIaasUserTenantMembershipClient(idp, userPoolId);
  }
}
