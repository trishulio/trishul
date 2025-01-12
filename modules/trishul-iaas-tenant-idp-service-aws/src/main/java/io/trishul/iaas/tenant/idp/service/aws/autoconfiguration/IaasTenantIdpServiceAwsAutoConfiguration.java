package io.trishul.iaas.tenant.idp.service.aws.autoconfiguration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;

import io.trishul.iaas.access.aws.AwsArnMapper;
import io.trishul.iaas.access.service.role.service.IaasRoleService;
import io.trishul.iaas.client.IaasClient;
import io.trishul.iaas.idp.tenant.model.BaseIaasIdpTenant;
import io.trishul.iaas.idp.tenant.model.IaasIdpTenant;
import io.trishul.iaas.idp.tenant.model.UpdateIaasIdpTenant;
import io.trishul.iaas.tenant.idp.service.aws.cognito.client.AwsGroupTypeMapper;
import io.trishul.iaas.tenant.idp.service.aws.cognito.client.AwsIaasUserTenantMembershipClient;
import io.trishul.iaas.tenant.idp.service.aws.cognito.client.AwsIdpTenantWithRoleClient;
import io.trishul.iaas.user.model.BaseIaasUserTenantMembership;
import io.trishul.iaas.user.model.IaasUserTenantMembership;
import io.trishul.iaas.user.model.IaasUserTenantMembershipId;
import io.trishul.iaas.user.model.UpdateIaasUserTenantMembership;

@Configuration
public class IaasTenantIdpServiceAwsAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean(AwsIdpTenantWithRoleClient.class)
        public IaasClient<String, IaasIdpTenant, BaseIaasIdpTenant<?>, UpdateIaasIdpTenant<?>> iaasIdpTenantClient(AWSCognitoIdentityProvider awsCognitoIdpProvider, @Value("${aws.cognito.user-pool.id}") String userPoolId, AwsArnMapper arnMapper, IaasRoleService roleService) {
        return new AwsIdpTenantWithRoleClient(awsCognitoIdpProvider, userPoolId, AwsGroupTypeMapper.INSTANCE, arnMapper, roleService);
    }

    @Bean
    @ConditionalOnMissingBean(AwsIaasUserTenantMembershipClient.class)
    public IaasClient<IaasUserTenantMembershipId, IaasUserTenantMembership, BaseIaasUserTenantMembership<?>, UpdateIaasUserTenantMembership<?>> awsCognitoUserGroupMembership(AWSCognitoIdentityProvider idp, @Value("${aws.cognito.user-pool.id}") String userPoolId) {
        return new AwsIaasUserTenantMembershipClient(idp, userPoolId);
    }
}
