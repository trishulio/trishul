package io.trishul.iaas.auth.aws.factory;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentity;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentityClientBuilder;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClient;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IaasAuthAwsFactory {
  private static final Logger log = LoggerFactory.getLogger(IaasAuthAwsFactory.class);

  public AWSCognitoIdentityProvider getIdentityProvider(final String cognitoRegion,
      String cognitoUrl, String cognitoAccessKeyId, String cognitoAccessSecretKey) {
    log.debug("Creating an instance of AwsCognitoIdp");
    final AwsClientBuilder.EndpointConfiguration endpointConfig
        = new AwsClientBuilder.EndpointConfiguration(cognitoUrl, cognitoRegion);
    final AWSCognitoIdentityProviderClientBuilder idpClientBuilder
        = AWSCognitoIdentityProviderClient.builder().withEndpointConfiguration(endpointConfig);
    BasicAWSCredentials basicAwsCredentials
        = new BasicAWSCredentials(cognitoAccessKeyId, cognitoAccessSecretKey);
    idpClientBuilder.withCredentials(new AWSStaticCredentialsProvider(basicAwsCredentials));

    AWSCognitoIdentityProvider awsCognitoIdp = idpClientBuilder.build();

    return awsCognitoIdp;
  }

  public AmazonCognitoIdentity getAwsCognitoIdentityClient(String region, String accessKeyId,
      String accessSecretKey) {
    AWSCredentials creds = new BasicAWSCredentials(accessKeyId, accessSecretKey);
    AWSStaticCredentialsProvider credsProvider = new AWSStaticCredentialsProvider(creds);
    AmazonCognitoIdentity amazonCognitoIdentity = AmazonCognitoIdentityClientBuilder.standard()
        .withCredentials(credsProvider).withRegion(region).build();
    return amazonCognitoIdentity;
  }
}
