package io.trishul.object.store.file.service.autoconfiguration;

import java.net.URI;
import java.util.Set;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.trishul.crud.service.CrudEntityMergerService;
import io.trishul.crud.service.EntityMergerService;
import io.trishul.crud.service.LockService;
import io.trishul.iaas.repository.IaasRepository;
import io.trishul.iaas.repository.provider.IaasRepositoryProvider;
import io.trishul.iaas.repository.provider.IaasRepositoryProviderProxy;
import io.trishul.model.executor.BlockingAsyncExecutor;
import io.trishul.model.validator.UtilityProvider;
import io.trishul.object.store.file.model.BaseIaasObjectStoreFile;
import io.trishul.object.store.file.model.IaasObjectStoreFile;
import io.trishul.object.store.file.model.UpdateIaasObjectStoreFile;
import io.trishul.object.store.file.service.controller.IaasObjectStoreFileController;
import io.trishul.object.store.file.service.decorator.TemporaryImageSrcDecorator;
import io.trishul.object.store.file.service.service.IaasObjectStoreFileService;

@Configuration
public class IaasObjectStoreFileServiceAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(TemporaryImageSrcDecorator.class)
    public TemporaryImageSrcDecorator temporaryImageSrcDecorator(IaasObjectStoreFileController objectStoreFileController) {
        return new TemporaryImageSrcDecorator(objectStoreFileController);
    }

  @Bean
  @ConditionalOnMissingBean(IaasObjectStoreFileService.class)
  public IaasObjectStoreFileService iaasObjectStoreFileService(UtilityProvider utilProvider,
      LockService lockService, BlockingAsyncExecutor executor,
      IaasRepositoryProvider<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile<?>, UpdateIaasObjectStoreFile<?>> iaasObjectStoreFileClientProvider) {
    final EntityMergerService<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile<?>, UpdateIaasObjectStoreFile<?>> entityMergerService
        = new CrudEntityMergerService<>(utilProvider, lockService, BaseIaasObjectStoreFile.class,
            UpdateIaasObjectStoreFile.class, IaasObjectStoreFile.class,
            Set.of(IaasObjectStoreFile.ATTR_MIN_VALID_UNTIL));
    IaasRepository<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile<?>, UpdateIaasObjectStoreFile<?>> iaasRepo
        = new IaasRepositoryProviderProxy<>(iaasObjectStoreFileClientProvider);

    return new IaasObjectStoreFileService(entityMergerService, iaasRepo);
  }
}
