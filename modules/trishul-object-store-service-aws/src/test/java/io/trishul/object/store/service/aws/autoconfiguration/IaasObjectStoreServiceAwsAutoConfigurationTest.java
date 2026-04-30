package io.trishul.object.store.service.aws.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.amazonaws.services.s3.AmazonS3;

import io.trishul.iaas.client.IaasClient;

class IaasObjectStoreServiceAwsAutoConfigurationTest {

  private IaasObjectStoreServiceAwsAutoConfiguration config;

  @BeforeEach
  void setUp() {
    config = new IaasObjectStoreServiceAwsAutoConfiguration();
  }

  @Test
  void testIaasObjectStoreClient_ReturnsNonNull() {
    AmazonS3 mockAwsClient = mock(AmazonS3.class);

    IaasClient<?, ?, ?, ?> result = config.iaasObjectStoreClient(mockAwsClient);

    assertNotNull(result);
  }

  @Test
  void testIaasObjectStoreAccessConfigClient_ReturnsNonNull() {
    AmazonS3 mockAwsClient = mock(AmazonS3.class);

    IaasClient<?, ?, ?, ?> result = config.iaasObjectStoreAccessConfigClient(mockAwsClient);

    assertNotNull(result);
  }

  @Test
  void testIaasObjectStoreCorsConfigClient_ReturnsNonNull() {
    AmazonS3 mockAwsClient = mock(AmazonS3.class);

    IaasClient<?, ?, ?, ?> result = config.iaasObjectStoreCorsConfigClient(mockAwsClient);

    assertNotNull(result);
  }
}
