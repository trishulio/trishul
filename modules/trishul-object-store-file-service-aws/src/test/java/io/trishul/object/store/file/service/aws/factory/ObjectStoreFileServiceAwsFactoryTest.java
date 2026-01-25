package io.trishul.object.store.file.service.aws.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.s3.AmazonS3;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ObjectStoreFileServiceAwsFactoryTest {
  private ObjectStoreFileServiceAwsFactory factory;

  @BeforeEach
  void init() {
    factory = new ObjectStoreFileServiceAwsFactory();
  }

  @Test
  void testS3Client() throws IllegalAccessException, URISyntaxException {
    AmazonS3 s3 = factory.s3Client("REGION", "ACCESS_KEY_ID", "ACCESS_SECRET_KEY", "SESSION_TOKEN");

    final AWSCredentialsProvider awsCredentialsProvider
        = (AWSCredentialsProvider) FieldUtils.readField(s3, "awsCredentialsProvider", true);
    final URI endpoint = (URI) FieldUtils.readField(s3, "endpoint", true);
    final String region = (String) FieldUtils.readField(s3, "signingRegion", true);

    assertEquals(new URI("https://s3.REGION.amazonaws.com"), endpoint);
    assertEquals("REGION", region);
    assertEquals("ACCESS_KEY_ID", awsCredentialsProvider.getCredentials().getAWSAccessKeyId());
    assertEquals("ACCESS_SECRET_KEY", awsCredentialsProvider.getCredentials().getAWSSecretKey());
    assertEquals(
        ((BasicSessionCredentials) awsCredentialsProvider.getCredentials()).getSessionToken(),
        "SESSION_TOKEN");
  }
}
