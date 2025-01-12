package io.trishul.object.store.service.autoconfiguration;

import java.util.Set;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.trishul.crud.service.CrudEntityMergerService;
import io.trishul.crud.service.EntityMergerService;
import io.trishul.crud.service.LockService;
import io.trishul.iaas.client.BulkIaasClient;
import io.trishul.iaas.client.IaasClient;
import io.trishul.iaas.repository.IaasRepository;
import io.trishul.model.executor.BlockingAsyncExecutor;
import io.trishul.model.validator.UtilityProvider;
import io.trishul.object.store.configuration.access.model.IaasObjectStoreAccessConfig;
import io.trishul.object.store.configuration.cors.model.IaasObjectStoreCorsConfiguration;
import io.trishul.object.store.model.BaseIaasObjectStore;
import io.trishul.object.store.model.IaasObjectStore;
import io.trishul.object.store.model.UpdateIaasObjectStore;
import io.trishul.object.store.service.IaasObjectStoreService;
import io.trishul.object.store.service.cors.config.service.IaasObjectStoreAccessConfigService;
import io.trishul.object.store.service.cors.config.service.IaasObjectStoreCorsConfigService;

@Configuration
public class IaasObjectStoreAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(IaasObjectStoreService.class)
    public IaasObjectStoreService iaasObjectStoreService(UtilityProvider utilProvider, LockService lockService, BlockingAsyncExecutor executor, IaasClient<String, IaasObjectStore, BaseIaasObjectStore<?>, UpdateIaasObjectStore<?>> iaasObjectStoreClient) {
        EntityMergerService<String, IaasObjectStore, BaseIaasObjectStore<?>, UpdateIaasObjectStore<?>> entityMergerService = new CrudEntityMergerService<>(utilProvider, lockService, BaseIaasObjectStore.class, UpdateIaasObjectStore.class, IaasObjectStore.class, Set.of());
        IaasRepository<String, IaasObjectStore, BaseIaasObjectStore<?>, UpdateIaasObjectStore<?>> iaasRepo = new BulkIaasClient<>(executor, iaasObjectStoreClient);
        return new IaasObjectStoreService(entityMergerService, iaasRepo);
    }

    @Bean
    @ConditionalOnMissingBean(IaasObjectStoreCorsConfigService.class)
    public IaasObjectStoreCorsConfigService iaasObjectStoreCorsConfigService(UtilityProvider utilProvider, LockService lockService, BlockingAsyncExecutor executor, IaasClient<String, IaasObjectStoreCorsConfiguration, IaasObjectStoreCorsConfiguration, IaasObjectStoreCorsConfiguration> iaasObjectStoreCorsConfigClient) {
        EntityMergerService<String, IaasObjectStoreCorsConfiguration, IaasObjectStoreCorsConfiguration, IaasObjectStoreCorsConfiguration> entityMergerService = new CrudEntityMergerService<>(utilProvider, lockService, IaasObjectStoreCorsConfiguration.class, IaasObjectStoreCorsConfiguration.class, IaasObjectStoreCorsConfiguration.class, Set.of());
        IaasRepository<String, IaasObjectStoreCorsConfiguration, IaasObjectStoreCorsConfiguration, IaasObjectStoreCorsConfiguration> iaasRepo = new BulkIaasClient<>(executor, iaasObjectStoreCorsConfigClient);

        return new IaasObjectStoreCorsConfigService(entityMergerService, iaasRepo);
    }

    @Bean
    @ConditionalOnMissingBean(IaasObjectStoreAccessConfigService.class)
    public IaasObjectStoreAccessConfigService iaasPublicAccessBlockService(UtilityProvider utilProvider, LockService lockService, BlockingAsyncExecutor executor, IaasClient<String, IaasObjectStoreAccessConfig, IaasObjectStoreAccessConfig, IaasObjectStoreAccessConfig> iaasObjectStoreAccessConfigClient) {
        EntityMergerService<String, IaasObjectStoreAccessConfig, IaasObjectStoreAccessConfig, IaasObjectStoreAccessConfig> entityMergerService = new CrudEntityMergerService<>(utilProvider, lockService, IaasObjectStoreAccessConfig.class, IaasObjectStoreAccessConfig.class, IaasObjectStoreAccessConfig.class, Set.of());
        IaasRepository<String, IaasObjectStoreAccessConfig, IaasObjectStoreAccessConfig, IaasObjectStoreAccessConfig> iaasRepo = new BulkIaasClient<>(executor, iaasObjectStoreAccessConfigClient);

        return new IaasObjectStoreAccessConfigService(entityMergerService, iaasRepo);
    }
}
