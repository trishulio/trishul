package io.trishul.object.store.file.service.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import java.net.URI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.crud.service.LockService;
import io.trishul.iaas.repository.provider.IaasRepositoryProvider;
import io.trishul.model.executor.BlockingAsyncExecutor;
import io.trishul.object.store.file.model.BaseIaasObjectStoreFile;
import io.trishul.object.store.file.model.IaasObjectStoreFile;
import io.trishul.object.store.file.model.UpdateIaasObjectStoreFile;
import io.trishul.object.store.file.service.controller.IaasObjectStoreFileController;
import io.trishul.object.store.file.service.decorator.TemporaryImageSrcDecorator;
import io.trishul.object.store.file.service.service.IaasObjectStoreFileService;

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
    IaasRepositoryProvider<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile<?>, UpdateIaasObjectStoreFile<?>> mockProvider = mock(IaasRepositoryProvider.class);

    IaasObjectStoreFileService result = config.iaasObjectStoreFileService(mockLockService, mockExecutor, mockProvider);

    assertNotNull(result);
  }
}
