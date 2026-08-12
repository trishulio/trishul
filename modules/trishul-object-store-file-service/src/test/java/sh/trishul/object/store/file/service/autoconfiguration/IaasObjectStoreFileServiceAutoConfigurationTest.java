package sh.trishul.object.store.file.service.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import java.net.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.crud.service.LockService;
import sh.trishul.iaas.repository.provider.IaasRepositoryProvider;
import sh.trishul.model.executor.BlockingAsyncExecutor;
import sh.trishul.object.store.file.model.BaseIaasObjectStoreFile;
import sh.trishul.object.store.file.model.IaasObjectStoreFile;
import sh.trishul.object.store.file.model.UpdateIaasObjectStoreFile;
import sh.trishul.object.store.file.service.controller.IaasObjectStoreFileController;
import sh.trishul.object.store.file.service.decorator.TemporaryImageSrcDecorator;
import sh.trishul.object.store.file.service.service.IaasObjectStoreFileService;

class IaasObjectStoreFileServiceAutoConfigurationTest {

  private IaasObjectStoreFileServiceAutoConfiguration config;

  @BeforeEach
  void setUp() {
    config = new IaasObjectStoreFileServiceAutoConfiguration();
  }

  @Test
  void testTemporaryImageSrcDecorator_ReturnsNonNull() {
    IaasObjectStoreFileController mockController = mock(IaasObjectStoreFileController.class);

    TemporaryImageSrcDecorator result = config.temporaryImageSrcDecorator(mockController);

    assertNotNull(result);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testIaasObjectStoreFileService_ReturnsNonNull() {
    LockService mockLockService = mock(LockService.class);
    BlockingAsyncExecutor mockExecutor = mock(BlockingAsyncExecutor.class);
    IaasRepositoryProvider<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile<?>, UpdateIaasObjectStoreFile<?>> mockProvider
        = mock(IaasRepositoryProvider.class);

    IaasObjectStoreFileService result
        = config.iaasObjectStoreFileService(mockLockService, mockExecutor, mockProvider);

    assertNotNull(result);
  }
}
