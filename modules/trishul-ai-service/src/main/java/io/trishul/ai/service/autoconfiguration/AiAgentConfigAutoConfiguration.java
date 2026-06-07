package io.trishul.ai.service.autoconfiguration;

import io.trishul.ai.agent.model.AiAgentConfig;
import io.trishul.ai.agent.model.AiAgentConfigAccessor;
import io.trishul.ai.agent.model.AiAgentConfigRefresher;
import io.trishul.ai.agent.model.BaseAiAgentConfig;
import io.trishul.ai.agent.model.UpdateAiAgentConfig;
import io.trishul.ai.chat.model.AiChatModelConfig;
import io.trishul.ai.chat.model.AiChatModelConfigAccessor;
import io.trishul.ai.memory.model.AiChatMemoryConfig;
import io.trishul.ai.memory.model.AiChatMemoryConfigAccessor;
import io.trishul.ai.service.agent.model.controller.AiAgentConfigController;
import io.trishul.ai.service.agent.model.repository.AiAgentConfigRepository;
import io.trishul.ai.service.agent.model.service.AiAgentConfigService;
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

import io.trishul.ai.service.agent.cache.AgentCache;
import io.trishul.ai.service.agent.factory.AgentFactory;
import io.trishul.ai.service.agent.factory.StreamingChatModelFactory;
import io.trishul.ai.service.memory.store.TenantChatMemoryStore;
import io.trishul.ai.service.tool.registry.AiToolRegistry;

@Configuration
public class AiAgentConfigAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(StreamingChatModelFactory.class)
  public StreamingChatModelFactory streamingChatModelFactory() {
    return new StreamingChatModelFactory();
  }

  @Bean
  @ConditionalOnMissingBean(AgentFactory.class)
  public AgentFactory agentFactory(TenantChatMemoryStore memoryStore, AiToolRegistry toolRegistry,
      StreamingChatModelFactory modelFactory) {
    return new AgentFactory(memoryStore, toolRegistry, modelFactory);
  }

  @Bean
  @ConditionalOnMissingBean(AgentCache.class)
  public AgentCache agentCache(AgentFactory agentFactory) {
    return new AgentCache(agentFactory);
  }

  @Bean
  @ConditionalOnMissingBean(AiAgentConfigService.class)
  public AiAgentConfigService aiAgentConfigService(LockService lockService,
      AiAgentConfigRepository repository,
      Refresher<AiAgentConfig, AiAgentConfigAccessor<?>> refresher) {
    EntityMergerService<Long, AiAgentConfig, BaseAiAgentConfig<?>, UpdateAiAgentConfig<?>> entityMergerService
        = new CrudEntityMergerService<>(lockService, BaseAiAgentConfig.class,
            UpdateAiAgentConfig.class, AiAgentConfig.class, Set.of());

    RepoService<Long, AiAgentConfig, AiAgentConfigAccessor<?>> repoService
        = new CrudRepoService<>(repository, refresher);

    return new AiAgentConfigService(entityMergerService, repoService);
  }

  @Bean
  @ConditionalOnMissingBean(AiAgentConfigController.class)
  public AiAgentConfigController aiAgentConfigController(AiAgentConfigService service) {
    return new AiAgentConfigController(service);
  }

  @Bean
  public AccessorRefresher<Long, AiAgentConfigAccessor<?>, AiAgentConfig> aiAgentConfigAccessorRefresher(
      AiAgentConfigRepository repository) {
    return new AccessorRefresher<>(AiAgentConfig.class, AiAgentConfigAccessor::getAgentConfig,
        AiAgentConfigAccessor::setAgentConfig, repository::findAllById);
  }

  @Bean
  public Refresher<AiAgentConfig, AiAgentConfigAccessor<?>> aiAgentConfigRefresher(
      AccessorRefresher<Long, AiAgentConfigAccessor<?>, AiAgentConfig> accessorRefresher,
      Refresher<AiChatModelConfig, AiChatModelConfigAccessor<?>> chatModelConfigRefresher,
      Refresher<AiChatMemoryConfig, AiChatMemoryConfigAccessor<?>> chatMemoryConfigRefresher) {
    return new AiAgentConfigRefresher(accessorRefresher, chatModelConfigRefresher,
        chatMemoryConfigRefresher);
  }
}
