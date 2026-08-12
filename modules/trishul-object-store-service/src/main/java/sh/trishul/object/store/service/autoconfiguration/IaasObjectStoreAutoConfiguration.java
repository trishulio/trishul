package sh.trishul.object.store.service.autoconfiguration;

import java.util.Set;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sh.trishul.crud.service.CrudEntityMergerService;
import sh.trishul.crud.service.EntityMergerService;
import sh.trishul.crud.service.LockService;
import sh.trishul.iaas.client.BulkIaasClient;
import sh.trishul.iaas.client.IaasClient;
import sh.trishul.iaas.repository.IaasRepository;
import sh.trishul.model.executor.BlockingAsyncExecutor;
import sh.trishul.object.store.configuration.access.model.IaasObjectStoreAccessConfig;
import sh.trishul.object.store.configuration.cors.model.IaasObjectStoreCorsConfiguration;
import sh.trishul.object.store.model.BaseIaasObjectStore;
import sh.trishul.object.store.model.IaasObjectStore;
import sh.trishul.object.store.model.UpdateIaasObjectStore;
import sh.trishul.object.store.service.IaasObjectStoreService;
import sh.trishul.object.store.service.cors.config.service.IaasObjectStoreAccessConfigService;
import sh.trishul.object.store.service.cors.config.service.IaasObjectStoreCorsConfigService;

@Configuration
public class IaasObjectStoreAutoConfiguration {
  @Bean
  @ConditionalOnMissingBean(IaasObjectStoreService.class)
  public IaasObjectStoreService iaasObjectStoreService(LockService lockService,
      BlockingAsyncExecutor executor,
      IaasClient<String, IaasObjectStore, BaseIaasObjectStore<?>, UpdateIaasObjectStore<?>> iaasObjectStoreClient) {
    EntityMergerService<String, IaasObjectStore, BaseIaasObjectStore<?>, UpdateIaasObjectStore<?>> entityMergerService
        = new CrudEntityMergerService<>(lockService, BaseIaasObjectStore.class,
            UpdateIaasObjectStore.class, IaasObjectStore.class, Set.of());
    IaasRepository<String, IaasObjectStore, BaseIaasObjectStore<?>, UpdateIaasObjectStore<?>> iaasRepo
        = new BulkIaasClient<>(executor, iaasObjectStoreClient);
    return new IaasObjectStoreService(entityMergerService, iaasRepo);
  }

  @Bean
  @ConditionalOnMissingBean(IaasObjectStoreCorsConfigService.class)
  public IaasObjectStoreCorsConfigService iaasObjectStoreCorsConfigService(LockService lockService,
      BlockingAsyncExecutor executor,
      IaasClient<String, IaasObjectStoreCorsConfiguration, IaasObjectStoreCorsConfiguration, IaasObjectStoreCorsConfiguration> iaasObjectStoreCorsConfigClient) {
    EntityMergerService<String, IaasObjectStoreCorsConfiguration, IaasObjectStoreCorsConfiguration, IaasObjectStoreCorsConfiguration> entityMergerService
        = new CrudEntityMergerService<>(lockService, IaasObjectStoreCorsConfiguration.class,
            IaasObjectStoreCorsConfiguration.class, IaasObjectStoreCorsConfiguration.class,
            Set.of());
    IaasRepository<String, IaasObjectStoreCorsConfiguration, IaasObjectStoreCorsConfiguration, IaasObjectStoreCorsConfiguration> iaasRepo
        = new BulkIaasClient<>(executor, iaasObjectStoreCorsConfigClient);

    return new IaasObjectStoreCorsConfigService(entityMergerService, iaasRepo);
  }

  @Bean
  @ConditionalOnMissingBean(IaasObjectStoreAccessConfigService.class)
  public IaasObjectStoreAccessConfigService iaasPublicAccessBlockService(LockService lockService,
      BlockingAsyncExecutor executor,
      IaasClient<String, IaasObjectStoreAccessConfig, IaasObjectStoreAccessConfig, IaasObjectStoreAccessConfig> iaasObjectStoreAccessConfigClient) {
    EntityMergerService<String, IaasObjectStoreAccessConfig, IaasObjectStoreAccessConfig, IaasObjectStoreAccessConfig> entityMergerService
        = new CrudEntityMergerService<>(lockService, IaasObjectStoreAccessConfig.class,
            IaasObjectStoreAccessConfig.class, IaasObjectStoreAccessConfig.class, Set.of());
    IaasRepository<String, IaasObjectStoreAccessConfig, IaasObjectStoreAccessConfig, IaasObjectStoreAccessConfig> iaasRepo
        = new BulkIaasClient<>(executor, iaasObjectStoreAccessConfigClient);

    return new IaasObjectStoreAccessConfigService(entityMergerService, iaasRepo);
  }
}
