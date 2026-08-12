package sh.trishul.iaas.user.service.aws.autoconfiguration;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sh.trishul.iaas.client.IaasClient;
import sh.trishul.iaas.user.aws.model.AwsCognitoAdminGetUserResultMapper;
import sh.trishul.iaas.user.aws.model.AwsCognitoUserMapper;
import sh.trishul.iaas.user.model.BaseIaasUser;
import sh.trishul.iaas.user.model.IaasUser;
import sh.trishul.iaas.user.model.UpdateIaasUser;
import sh.trishul.iaas.user.service.aws.AwsCognitoUserClient;

@Configuration
public class IaasUserServiceAwsAutoConfiguration {
  @Bean
  @ConditionalOnMissingBean(AwsCognitoUserClient.class)
  public IaasClient<String, IaasUser, BaseIaasUser<?>, UpdateIaasUser<?>> awsUserClient(
      AWSCognitoIdentityProvider idp, @Value("${aws.cognito.user-pool.id}") String userPoolId) {
    return new AwsCognitoUserClient(idp, userPoolId, AwsCognitoAdminGetUserResultMapper.INSTANCE,
        AwsCognitoUserMapper.INSTANCE);
  }

}
