package sh.trishul.iaas.user.service.aws.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.iaas.client.IaasClient;

class IaasUserServiceAwsAutoConfigurationTest {

  private IaasUserServiceAwsAutoConfiguration config;

  @BeforeEach
  void setUp() {
    config = new IaasUserServiceAwsAutoConfiguration();
  }

  @Test
  void testAwsUserClient_ReturnsNonNull() {
    AWSCognitoIdentityProvider mockIdp = mock(AWSCognitoIdentityProvider.class);
    String userPoolId = "test-user-pool-id";

    IaasClient<?, ?, ?, ?> result = config.awsUserClient(mockIdp, userPoolId);

    assertNotNull(result);
  }
}
