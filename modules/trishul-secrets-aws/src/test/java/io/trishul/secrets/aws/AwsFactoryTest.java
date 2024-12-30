package io.trishul.secrets.aws;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AwsFactoryTest {
  private AwsFactory factory;

  @BeforeEach
  public void init() {
    factory = new AwsFactory();
  }

  @Test
  public void testSecretsMgrClient() throws URISyntaxException, IllegalAccessException {
    AWSSecretsManager secretsMgr
        = factory.secretsMgrClient("REGION", "URL", "ACCESS_KEY_ID", "ACCESS_SECRET_KEY");

    final AWSCredentialsProvider awsCredentialsProvider
        = (AWSCredentialsProvider) FieldUtils.readField(secretsMgr, "awsCredentialsProvider", true);
    final URI endpoint = (URI) FieldUtils.readField(secretsMgr, "endpoint", true);
    final String region = (String) FieldUtils.readField(secretsMgr, "signingRegion", true);

    assertEquals(new URI("https://URL"), endpoint);
    assertEquals("REGION", region);
    assertEquals(awsCredentialsProvider.getCredentials().getAWSAccessKeyId(), "ACCESS_KEY_ID");
    assertEquals(awsCredentialsProvider.getCredentials().getAWSSecretKey(), "ACCESS_SECRET_KEY");
  }
}
