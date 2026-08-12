package sh.trishul.ai.service.autoconfiguration;

import java.util.Set;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sh.trishul.ai.memory.model.AiChatMemoryConfig;
import sh.trishul.ai.memory.model.AiChatMemoryConfigAccessor;
import sh.trishul.ai.memory.model.AiChatMemoryConfigRefresher;
import sh.trishul.ai.memory.model.BaseAiChatMemoryConfig;
import sh.trishul.ai.memory.model.UpdateAiChatMemoryConfig;
import sh.trishul.ai.service.agent.factory.AgentFactory;
import sh.trishul.ai.service.memory.manager.AiChatMemoryManager;
import sh.trishul.ai.service.memory.manager.AiChatMemoryManagerWrapper;
import sh.trishul.ai.service.memory.manager.CachingChatMemoryManager;
import sh.trishul.ai.service.memory.model.controller.AiChatMemoryConfigController;
import sh.trishul.ai.service.memory.model.repository.AiChatMemoryConfigRepository;
import sh.trishul.ai.service.memory.model.service.AiChatMemoryConfigService;
import sh.trishul.ai.service.memory.provider.AiChatMemoryConfigProvider;
import sh.trishul.ai.service.memory.provider.CachingAiChatMemoryConfigProvider;
import sh.trishul.ai.service.memory.store.TenantChatMemoryStore;
import sh.trishul.base.types.base.pojo.Refresher;
import sh.trishul.crud.controller.filter.AttributeFilter;
import sh.trishul.crud.service.CrudEntityMergerService;
import sh.trishul.crud.service.CrudRepoService;
import sh.trishul.crud.service.EntityMergerService;
import sh.trishul.crud.service.LockService;
import sh.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;
import sh.trishul.repo.jpa.repository.service.RepoService;

@Configuration
public class AiChatMemoryConfigAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(TenantChatMemoryStore.class)
  public TenantChatMemoryStore tenantChatMemoryStore() {
    return new TenantChatMemoryStore();
  }

  @Bean
  @ConditionalOnMissingBean(AiChatMemoryConfigProvider.class)
  public AiChatMemoryConfigProvider aiChatMemoryConfigProvider(
      AiChatMemoryConfigService chatMemoryConfigService) {
    return new CachingAiChatMemoryConfigProvider(chatMemoryConfigService);
  }

  @Bean
  @ConditionalOnMissingBean(AiChatMemoryManager.class)
  public AiChatMemoryManager aiChatMemoryManager(AgentFactory agentFactory) {
    return new CachingChatMemoryManager(agentFactory);
  }

  @Bean
  @ConditionalOnMissingBean(AiChatMemoryManagerWrapper.class)
  public AiChatMemoryManagerWrapper aiChatMemoryManagerWrapper(
      AiChatMemoryConfigProvider configProvider, AiChatMemoryManager memoryManager) {
    return new AiChatMemoryManagerWrapper(configProvider, memoryManager);
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
      AiChatMemoryConfigService service, AttributeFilter filter) {
    return new AiChatMemoryConfigController(service, filter);
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
