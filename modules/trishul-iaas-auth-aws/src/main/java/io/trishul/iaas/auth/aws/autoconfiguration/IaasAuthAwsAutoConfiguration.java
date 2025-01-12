package io.trishul.iaas.auth.aws.autoconfiguration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentity;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import io.trishul.iaas.auth.aws.client.AwsCognitoIdentityClient;
import io.trishul.iaas.auth.aws.client.AwsCognitoIdentitySdkWrapper;
import io.trishul.iaas.auth.aws.client.AwsIdentityCredentialsMapper;
import io.trishul.iaas.auth.aws.client.AwsResourceCredentialsFetcher;
import io.trishul.iaas.auth.aws.client.CachedAwsCognitoIdentityClient;
import io.trishul.iaas.auth.aws.factory.IaasAuthAwsFactory;
import io.trishul.iaas.auth.session.context.IaasAuthorizationFetcher;

@Configuration
public class IaasAuthAwsAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(IaasAuthAwsFactory.class)
  public IaasAuthAwsFactory iaasAuthAwsFactory() {
    return new IaasAuthAwsFactory();
  }

  @Bean
  @ConditionalOnMissingBean(AmazonCognitoIdentity.class)
  public AmazonCognitoIdentity amazonCognitoIdentity(IaasAuthAwsFactory awsFactory,
      @Value("${aws.cognito.region}") String region,
      @Value("${aws.cognito.access-key}") final String accessKeyId,
      @Value("${aws.cognito.access-secret}") final String accessSecretKey) {
    return awsFactory.getAwsCognitoIdentityClient(region, accessKeyId, accessSecretKey);
  }

  @Bean
  @ConditionalOnMissingBean(AWSCognitoIdentityProvider.class)
  public AWSCognitoIdentityProvider awsCognitoIdpProvider(IaasAuthAwsFactory awsFactory,
      @Value("${aws.cognito.region}") final String cognitoRegion,
      @Value("${aws.cognito.url}") final String cognitoUrl,
      @Value("${aws.cognito.access-key}") final String cognitoAccessKeyId,
      @Value("${aws.cognito.access-secret}") final String cognitoAccessSecretKey) {
    return awsFactory.getIdentityProvider(cognitoRegion, cognitoUrl, cognitoAccessKeyId,
        cognitoAccessSecretKey);
  }

  @Bean
  @ConditionalOnMissingBean(AwsCognitoIdentityClient.class)
  public AwsCognitoIdentityClient awsCognitoIdentityClient(
      AmazonCognitoIdentity amazonCognitoIdentity,
      @Value("${app.iaas.credentials.expiry.duration}") long credentialsExpiryDurationSeconds) {
    AwsCognitoIdentityClient awsCognitoIdentityClient
        = new AwsCognitoIdentitySdkWrapper(amazonCognitoIdentity);

    return new CachedAwsCognitoIdentityClient(awsCognitoIdentityClient,
        credentialsExpiryDurationSeconds);
  }

  @Bean
  @ConditionalOnMissingBean(IaasAuthorizationFetcher.class)
  public IaasAuthorizationFetcher iaasAuthorizationFetcher(AwsCognitoIdentityClient identityClient,
      @Value("${aws.cognito.user-pool.url}") String userPoolUrl) {
    return new AwsResourceCredentialsFetcher(identityClient, AwsIdentityCredentialsMapper.INSTANCE,
        userPoolUrl);
  }
}
