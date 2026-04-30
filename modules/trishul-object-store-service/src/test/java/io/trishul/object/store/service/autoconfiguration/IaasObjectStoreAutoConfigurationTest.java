package io.trishul.object.store.service.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.crud.service.LockService;
import io.trishul.iaas.client.IaasClient;
import io.trishul.model.executor.BlockingAsyncExecutor;
import io.trishul.object.store.configuration.access.model.IaasObjectStoreAccessConfig;
import io.trishul.object.store.configuration.cors.model.IaasObjectStoreCorsConfiguration;
import io.trishul.object.store.model.BaseIaasObjectStore;
import io.trishul.object.store.model.IaasObjectStore;
import io.trishul.object.store.model.UpdateIaasObjectStore;
import io.trishul.object.store.service.IaasObjectStoreService;
import io.trishul.object.store.service.cors.config.service.IaasObjectStoreAccessConfigService;
import io.trishul.object.store.service.cors.config.service.IaasObjectStoreCorsConfigService;

class IaasObjectStoreAutoConfigurationTest {

  private IaasObjectStoreAutoConfiguration config;

  @BeforeEach
  void setUp() {
    config = new IaasObjectStoreAutoConfiguration();
  }

  @Test
  @SuppressWarnings("unchecked")
  void testIaasObjectStoreService_ReturnsNonNull() {
    LockService mockLockService = mock(LockService.class);
    BlockingAsyncExecutor mockExecutor = mock(BlockingAsyncExecutor.class);
    IaasClient<String, IaasObjectStore, BaseIaasObjectStore<?>, UpdateIaasObjectStore<?>> mockClient = mock(IaasClient.class);

    IaasObjectStoreService result = config.iaasObjectStoreService(mockLockService, mockExecutor, mockClient);

    assertNotNull(result);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testIaasObjectStoreCorsConfigService_ReturnsNonNull() {
    LockService mockLockService = mock(LockService.class);
    BlockingAsyncExecutor mockExecutor = mock(BlockingAsyncExecutor.class);
    IaasClient<String, IaasObjectStoreCorsConfiguration, IaasObjectStoreCorsConfiguration, IaasObjectStoreCorsConfiguration> mockClient = mock(IaasClient.class);

    IaasObjectStoreCorsConfigService result = config.iaasObjectStoreCorsConfigService(mockLockService, mockExecutor, mockClient);

    assertNotNull(result);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testIaasPublicAccessBlockService_ReturnsNonNull() {
    LockService mockLockService = mock(LockService.class);
    BlockingAsyncExecutor mockExecutor = mock(BlockingAsyncExecutor.class);
    IaasClient<String, IaasObjectStoreAccessConfig, IaasObjectStoreAccessConfig, IaasObjectStoreAccessConfig> mockClient = mock(IaasClient.class);

    IaasObjectStoreAccessConfigService result = config.iaasPublicAccessBlockService(mockLockService, mockExecutor, mockClient);

    assertNotNull(result);
  }
}
