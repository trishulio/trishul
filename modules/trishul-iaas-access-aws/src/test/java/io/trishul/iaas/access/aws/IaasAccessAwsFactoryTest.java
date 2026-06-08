package io.trishul.iaas.access.aws;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import java.net.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class IaasAccessAwsFactoryTest {
  private IaasAccessAwsFactory factory;

  @BeforeEach
  public void init() {
    factory = new IaasAccessAwsFactory();
  }

  @Test
  public void testIamClient_returnsIamClientWithCorrectCredentialsAndRegion() throws Exception {
    String accessKey = "accessKey";
    String secretKey = "secretKey";

    AmazonIdentityManagement iamClient = factory.iamClient(accessKey, secretKey);

    assertNotNull(iamClient);

    AWSCredentialsProvider credsProvider = (AWSCredentialsProvider) ReflectionTestUtils
        .getField(iamClient, "awsCredentialsProvider");
    assertNotNull(credsProvider);
    assertEquals(accessKey, credsProvider.getCredentials().getAWSAccessKeyId());
    assertEquals(secretKey, credsProvider.getCredentials().getAWSSecretKey());

    URI endpoint = (URI) ReflectionTestUtils.getField(iamClient, "endpoint");
    assertEquals(new URI("https://iam.amazonaws.com"), endpoint);

    String signingRegion = (String) ReflectionTestUtils.getField(iamClient, "signingRegion");
    assertEquals("us-east-1", signingRegion);
  }
}
