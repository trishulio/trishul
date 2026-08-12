package sh.trishul.object.store.file.service.autoconfiguration;

import java.net.URI;
import java.util.Set;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sh.trishul.crud.service.CrudEntityMergerService;
import sh.trishul.crud.service.EntityMergerService;
import sh.trishul.crud.service.LockService;
import sh.trishul.iaas.repository.IaasRepository;
import sh.trishul.iaas.repository.provider.IaasRepositoryProvider;
import sh.trishul.iaas.repository.provider.IaasRepositoryProviderProxy;
import sh.trishul.model.executor.BlockingAsyncExecutor;
import sh.trishul.object.store.file.model.BaseIaasObjectStoreFile;
import sh.trishul.object.store.file.model.IaasObjectStoreFile;
import sh.trishul.object.store.file.model.UpdateIaasObjectStoreFile;
import sh.trishul.object.store.file.service.controller.IaasObjectStoreFileController;
import sh.trishul.object.store.file.service.decorator.TemporaryImageSrcDecorator;
import sh.trishul.object.store.file.service.service.IaasObjectStoreFileService;

@Configuration
public class IaasObjectStoreFileServiceAutoConfiguration {
  @Bean
  @ConditionalOnMissingBean(TemporaryImageSrcDecorator.class)
  public TemporaryImageSrcDecorator temporaryImageSrcDecorator(
      IaasObjectStoreFileController objectStoreFileController) {
    return new TemporaryImageSrcDecorator(objectStoreFileController);
  }

  @Bean
  @ConditionalOnMissingBean(IaasObjectStoreFileService.class)
  public IaasObjectStoreFileService iaasObjectStoreFileService(LockService lockService,
      BlockingAsyncExecutor executor,
      IaasRepositoryProvider<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile<?>, UpdateIaasObjectStoreFile<?>> iaasObjectStoreFileClientProvider) {
    final EntityMergerService<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile<?>, UpdateIaasObjectStoreFile<?>> entityMergerService
        = new CrudEntityMergerService<>(lockService, BaseIaasObjectStoreFile.class,
            UpdateIaasObjectStoreFile.class, IaasObjectStoreFile.class,
            Set.of(BaseIaasObjectStoreFile.ATTR_MIN_VALID_UNTIL));
    IaasRepository<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile<?>, UpdateIaasObjectStoreFile<?>> iaasRepo
        = new IaasRepositoryProviderProxy<>(iaasObjectStoreFileClientProvider);

    return new IaasObjectStoreFileService(entityMergerService, iaasRepo);
  }
}
