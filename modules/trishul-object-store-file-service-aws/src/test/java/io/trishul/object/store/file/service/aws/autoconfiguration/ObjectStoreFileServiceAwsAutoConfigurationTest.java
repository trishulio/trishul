package io.trishul.object.store.file.service.aws.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.iaas.auth.session.context.ContextHolderAuthorizationFetcher;
import io.trishul.iaas.repository.provider.IaasRepositoryProvider;
import io.trishul.object.store.file.service.aws.factory.ObjectStoreFileServiceAwsFactory;
import io.trishul.object.store.file.service.service.IaasObjectStoreNameProvider;

class ObjectStoreFileServiceAwsAutoConfigurationTest {

  private ObjectStoreFileServiceAwsAutoConfiguration config;

  @BeforeEach
  void setUp() {
    config = new ObjectStoreFileServiceAwsAutoConfiguration();
  }

  @Test
  void testObjectStoreFileServiceAwsFactory_ReturnsNonNull() {
    ObjectStoreFileServiceAwsFactory result = config.objectStoreFileServiceAwsFactory();

    assertNotNull(result);
  }

  @Test
  void testIaasObjectStoreFileClientProvider_ReturnsNonNull() {
    String region = "us-east-1";
    IaasObjectStoreNameProvider mockBucketNameProvider = mock(IaasObjectStoreNameProvider.class);
    ContextHolderAuthorizationFetcher mockAuthFetcher
        = mock(ContextHolderAuthorizationFetcher.class);
    ObjectStoreFileServiceAwsFactory mockAwsFactory = mock(ObjectStoreFileServiceAwsFactory.class);
    Long getPresignUrlDuration = 3600L;

    IaasRepositoryProvider<?, ?, ?, ?> result = config.iaasObjectStoreFileClientProvider(region,
        mockBucketNameProvider, mockAuthFetcher, mockAwsFactory, getPresignUrlDuration);

    assertNotNull(result);
  }
}
