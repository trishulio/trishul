package io.trishul.iaas.user.service.aws.autoconfiguration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;

import io.trishul.iaas.client.IaasClient;
import io.trishul.iaas.user.aws.model.AwsCognitoAdminGetUserResultMapper;
import io.trishul.iaas.user.aws.model.AwsCognitoUserMapper;
import io.trishul.iaas.user.model.BaseIaasUser;
import io.trishul.iaas.user.model.IaasUser;
import io.trishul.iaas.user.model.UpdateIaasUser;
import io.trishul.iaas.user.service.aws.AwsCognitoUserClient;

@Configuration
public class IaasUserServiceAwsAutoConfiguration {    
    @Bean
    @ConditionalOnMissingBean(AwsCognitoUserClient.class)
    public IaasClient<String, IaasUser, BaseIaasUser<?>, UpdateIaasUser<?>> awsUserClient(AWSCognitoIdentityProvider idp, @Value("${aws.cognito.user-pool.id}") String userPoolId) {
        return new AwsCognitoUserClient(idp, userPoolId, AwsCognitoAdminGetUserResultMapper.INSTANCE, AwsCognitoUserMapper.INSTANCE);
    }

}
