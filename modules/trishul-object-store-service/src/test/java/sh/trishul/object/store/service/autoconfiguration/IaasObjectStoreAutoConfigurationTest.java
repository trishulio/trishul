package sh.trishul.object.store.service.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.crud.service.LockService;
import sh.trishul.iaas.client.IaasClient;
import sh.trishul.model.executor.BlockingAsyncExecutor;
import sh.trishul.object.store.configuration.access.model.IaasObjectStoreAccessConfig;
import sh.trishul.object.store.configuration.cors.model.IaasObjectStoreCorsConfiguration;
import sh.trishul.object.store.model.BaseIaasObjectStore;
import sh.trishul.object.store.model.IaasObjectStore;
import sh.trishul.object.store.model.UpdateIaasObjectStore;
import sh.trishul.object.store.service.IaasObjectStoreService;
import sh.trishul.object.store.service.cors.config.service.IaasObjectStoreAccessConfigService;
import sh.trishul.object.store.service.cors.config.service.IaasObjectStoreCorsConfigService;

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
    IaasClient<String, IaasObjectStore, BaseIaasObjectStore<?>, UpdateIaasObjectStore<?>> mockClient
        = mock(IaasClient.class);

    IaasObjectStoreService result
        = config.iaasObjectStoreService(mockLockService, mockExecutor, mockClient);

    assertNotNull(result);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testIaasObjectStoreCorsConfigService_ReturnsNonNull() {
    LockService mockLockService = mock(LockService.class);
    BlockingAsyncExecutor mockExecutor = mock(BlockingAsyncExecutor.class);
    IaasClient<String, IaasObjectStoreCorsConfiguration, IaasObjectStoreCorsConfiguration, IaasObjectStoreCorsConfiguration> mockClient
        = mock(IaasClient.class);

    IaasObjectStoreCorsConfigService result
        = config.iaasObjectStoreCorsConfigService(mockLockService, mockExecutor, mockClient);

    assertNotNull(result);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testIaasPublicAccessBlockService_ReturnsNonNull() {
    LockService mockLockService = mock(LockService.class);
    BlockingAsyncExecutor mockExecutor = mock(BlockingAsyncExecutor.class);
    IaasClient<String, IaasObjectStoreAccessConfig, IaasObjectStoreAccessConfig, IaasObjectStoreAccessConfig> mockClient
        = mock(IaasClient.class);

    IaasObjectStoreAccessConfigService result
        = config.iaasPublicAccessBlockService(mockLockService, mockExecutor, mockClient);

    assertNotNull(result);
  }
}
