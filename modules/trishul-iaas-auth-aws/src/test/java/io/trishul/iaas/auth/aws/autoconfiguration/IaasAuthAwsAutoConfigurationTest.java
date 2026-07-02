package io.trishul.iaas.auth.aws.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentity;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import io.trishul.iaas.auth.aws.client.AwsCognitoIdentityClient;
import io.trishul.iaas.auth.aws.factory.IaasAuthAwsFactory;
import io.trishul.iaas.auth.session.context.IaasAuthorizationFetcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IaasAuthAwsAutoConfigurationTest {

  private IaasAuthAwsAutoConfiguration config;

  @BeforeEach
  void setUp() {
    config = new IaasAuthAwsAutoConfiguration();
  }

  @Test
  void testIaasAuthAwsFactory_ReturnsNonNull() {
    IaasAuthAwsFactory result = config.iaasAuthAwsFactory();

    assertNotNull(result);
  }

  @Test
  void testAmazonCognitoIdentity_ReturnsNonNull() {
    IaasAuthAwsFactory mockFactory = mock(IaasAuthAwsFactory.class);
    AmazonCognitoIdentity mockIdentity = mock(AmazonCognitoIdentity.class);

    when(mockFactory.getAwsCognitoIdentityClient("region", "accessKey", "secretKey"))
        .thenReturn(mockIdentity);

    AmazonCognitoIdentity result
        = config.amazonCognitoIdentity(mockFactory, "region", "accessKey", "secretKey");

    assertNotNull(result);
  }

  @Test
  void testAwsCognitoIdpProvider_ReturnsNonNull() {
    IaasAuthAwsFactory mockFactory = mock(IaasAuthAwsFactory.class);
    AWSCognitoIdentityProvider mockProvider = mock(AWSCognitoIdentityProvider.class);

    when(mockFactory.getIdentityProvider("region", "url", "accessKey", "secretKey"))
        .thenReturn(mockProvider);

    AWSCognitoIdentityProvider result
        = config.awsCognitoIdpProvider(mockFactory, "region", "url", "accessKey", "secretKey");

    assertNotNull(result);
  }

  @Test
  void testAwsCognitoIdentityClient_ReturnsNonNull() {
    AmazonCognitoIdentity mockIdentity = mock(AmazonCognitoIdentity.class);
    long expiryDuration = 3600L;

    AwsCognitoIdentityClient result = config.awsCognitoIdentityClient(mockIdentity, expiryDuration);

    assertNotNull(result);
  }

  @Test
  void testIaasAuthorizationFetcher_ReturnsNonNull() {
    AwsCognitoIdentityClient mockClient = mock(AwsCognitoIdentityClient.class);
    String userPoolUrl = "https://cognito-idp.us-east-1.amazonaws.com/pool-id";
    String identityPoolId = "us-east-1:12345678-1234-1234-1234-123456789012";

    IaasAuthorizationFetcher result
        = config.iaasAuthorizationFetcher(mockClient, userPoolUrl, identityPoolId);

    assertNotNull(result);
  }
}
