package io.trishul.object.store.aws.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.amazonaws.services.s3.AmazonS3;

import io.trishul.object.store.aws.model.mapper.ObjectStoreAwsFactory;

class IaasObjectStoreAwsConfigurationTest {

  private IaasObjectStoreAwsConfiguration config;

  @BeforeEach
  void setUp() {
    config = new IaasObjectStoreAwsConfiguration();
  }

  @Test
  void testAwsFactory_ReturnsNonNull() {
    ObjectStoreAwsFactory result = config.awsFactory();

    assertNotNull(result);
  }

  @Test
  void testS3Client_ReturnsNonNull() {
    ObjectStoreAwsFactory mockFactory = mock(ObjectStoreAwsFactory.class);
    AmazonS3 mockS3Client = mock(AmazonS3.class);
    
    when(mockFactory.s3Client("us-east-1", "accessKey", "secret"))
        .thenReturn(mockS3Client);

    AmazonS3 result = config.s3Client(mockFactory, "us-east-1", "accessKey", "secret");

    assertNotNull(result);
  }
}
