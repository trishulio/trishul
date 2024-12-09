package io.trishul.iaas.auth.aws.autoconfiguration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentity;

import io.trishul.iaas.auth.aws.client.AwsCognitoIdentityClient;
import io.trishul.iaas.auth.aws.client.AwsCognitoIdentitySdkWrapper;
import io.trishul.iaas.auth.aws.client.AwsIdentityCredentialsMapper;
import io.trishul.iaas.auth.aws.client.AwsResourceCredentialsFetcher;
import io.trishul.iaas.auth.aws.client.CachedAwsCognitoIdentityClient;
import io.trishul.iaas.auth.aws.factory.AwsFactory;
import io.trishul.iaas.auth.session.context.IaasAuthorizationFetcher;

@Configuration
public class AwsAutoConfiguration {

    @Bean
    public AwsFactory awsFactory() {
        return new AwsFactory();
    }

    @Bean
    @ConditionalOnMissingBean(AmazonCognitoIdentity.class)
    public AmazonCognitoIdentity cognitoIdentity(AwsFactory awsFactory, @Value("${aws.cognito.region}") String region, @Value("${aws.cognito.access-key}") final String accessKeyId, @Value("${aws.cognito.access-secret}") final String accessSecretKey) {
        return awsFactory.getAwsCognitoIdentityClient(region, accessKeyId, accessSecretKey);
    }

    @Bean
    @ConditionalOnMissingBean(AwsCognitoIdentityClient.class)
    public AwsCognitoIdentityClient cognitoIdentityClient(AmazonCognitoIdentity cognitoIdentity, @Value("${app.iaas.credentials.expiry.duration}") long credentialsExpiryDurationSeconds) {
        AwsCognitoIdentityClient cognitoIdentityClient = new AwsCognitoIdentitySdkWrapper(cognitoIdentity);

        return new CachedAwsCognitoIdentityClient(cognitoIdentityClient, credentialsExpiryDurationSeconds);
    }

    @Bean
    @ConditionalOnMissingBean(IaasAuthorizationFetcher.class)
    public IaasAuthorizationFetcher iaasAuthorizationFetch(AwsCognitoIdentityClient identityClient, @Value("${aws.cognito.user-pool.url}") String userPoolUrl) {
        return new AwsResourceCredentialsFetcher(identityClient, AwsIdentityCredentialsMapper.INSTANCE, userPoolUrl);
    }
}
