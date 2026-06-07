package io.trishul.ai.service.autoconfiguration;

import io.trishul.ai.memory.model.AiChatMemoryConfig;
import io.trishul.ai.memory.model.AiChatMemoryConfigAccessor;
import io.trishul.ai.memory.model.AiChatMemoryConfigRefresher;
import io.trishul.ai.memory.model.BaseAiChatMemoryConfig;
import io.trishul.ai.memory.model.UpdateAiChatMemoryConfig;
import io.trishul.ai.service.memory.model.controller.AiChatMemoryConfigController;
import io.trishul.ai.service.memory.model.repository.AiChatMemoryConfigRepository;
import io.trishul.ai.service.memory.model.service.AiChatMemoryConfigService;
import io.trishul.base.types.base.pojo.Refresher;
import io.trishul.crud.service.CrudEntityMergerService;
import io.trishul.crud.service.CrudRepoService;
import io.trishul.crud.service.EntityMergerService;
import io.trishul.crud.service.LockService;
import io.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;
import io.trishul.repo.jpa.repository.service.RepoService;
import java.util.Set;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.trishul.ai.service.memory.store.TenantChatMemoryStore;

@Configuration
public class AiChatMemoryConfigAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(TenantChatMemoryStore.class)
  public TenantChatMemoryStore tenantChatMemoryStore() {
    return new TenantChatMemoryStore();
  }

  @Bean
  @ConditionalOnMissingBean(AiChatMemoryConfigService.class)
  public AiChatMemoryConfigService aiChatMemoryConfigService(LockService lockService,
      AiChatMemoryConfigRepository repository,
      Refresher<AiChatMemoryConfig, AiChatMemoryConfigAccessor<?>> refresher) {
    EntityMergerService<Long, AiChatMemoryConfig, BaseAiChatMemoryConfig<?>, UpdateAiChatMemoryConfig<?>> entityMergerService
        = new CrudEntityMergerService<>(lockService, BaseAiChatMemoryConfig.class,
            UpdateAiChatMemoryConfig.class, AiChatMemoryConfig.class, Set.of());

    RepoService<Long, AiChatMemoryConfig, AiChatMemoryConfigAccessor<?>> repoService
        = new CrudRepoService<>(repository, refresher);

    return new AiChatMemoryConfigService(entityMergerService, repoService);
  }

  @Bean
  @ConditionalOnMissingBean(AiChatMemoryConfigController.class)
  public AiChatMemoryConfigController aiChatMemoryConfigController(
      AiChatMemoryConfigService service) {
    return new AiChatMemoryConfigController(service);
  }

  @Bean
  public AccessorRefresher<Long, AiChatMemoryConfigAccessor<?>, AiChatMemoryConfig> aiChatMemoryConfigAccessorRefresher(
      AiChatMemoryConfigRepository repository) {
    return new AccessorRefresher<>(AiChatMemoryConfig.class,
        AiChatMemoryConfigAccessor::getChatMemoryConfig,
        AiChatMemoryConfigAccessor::setChatMemoryConfig, repository::findAllById);
  }

  @Bean
  public Refresher<AiChatMemoryConfig, AiChatMemoryConfigAccessor<?>> aiChatMemoryConfigRefresher(
      AccessorRefresher<Long, AiChatMemoryConfigAccessor<?>, AiChatMemoryConfig> accessorRefresher) {
    return new AiChatMemoryConfigRefresher(accessorRefresher);
  }
}
