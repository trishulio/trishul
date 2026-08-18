package sh.trishul.ai.service.autoconfiguration;

import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sh.trishul.ai.agent.model.AiAgentConfig;
import sh.trishul.ai.agent.model.AiAgentConfigAccessor;
import sh.trishul.ai.agent.model.AiAgentConfigRefresher;
import sh.trishul.ai.agent.model.BaseAiAgentConfig;
import sh.trishul.ai.agent.model.UpdateAiAgentConfig;
import sh.trishul.ai.chat.model.AiChatModelConfig;
import sh.trishul.ai.chat.model.AiChatModelConfigAccessor;
import sh.trishul.ai.memory.model.AiChatMemoryConfig;
import sh.trishul.ai.memory.model.AiChatMemoryConfigAccessor;
import sh.trishul.ai.service.agent.cache.AgentCache;
import sh.trishul.ai.service.agent.factory.AgentFactory;
import sh.trishul.ai.service.agent.factory.ChatModelFactory;
import sh.trishul.ai.service.agent.factory.StreamingChatModelFactory;
import sh.trishul.ai.service.agent.manager.AiAgentManagerWrapper;
import sh.trishul.ai.service.agent.model.controller.AiAgentConfigController;
import sh.trishul.ai.service.agent.model.repository.AiAgentConfigRepository;
import sh.trishul.ai.service.agent.model.service.AiAgentConfigService;
import sh.trishul.ai.service.agent.provider.AiAgentConfigProvider;
import sh.trishul.ai.service.agent.provider.CachingAiAgentConfigProvider;
import sh.trishul.ai.service.memory.manager.AiChatMemoryManagerWrapper;
import sh.trishul.ai.service.memory.store.TenantChatMemoryStore;
import sh.trishul.ai.service.tool.registry.AiToolRegistry;
import sh.trishul.base.types.base.pojo.Refresher;
import sh.trishul.crud.controller.filter.AttributeFilter;
import sh.trishul.crud.service.CrudEntityMergerService;
import sh.trishul.crud.service.CrudRepoService;
import sh.trishul.crud.service.EntityMergerService;
import sh.trishul.crud.service.LockService;
import sh.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;
import sh.trishul.repo.jpa.repository.service.RepoService;

@Configuration
public class AiAgentConfigAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(StreamingChatModelFactory.class)
  public StreamingChatModelFactory streamingChatModelFactory(
      @Value("${ai.copilot.base-url:https://models.inference.ai.azure.com}") String copilotBaseUrl,
      @Value("${ai.openrouter.base-url:https://openrouter.ai/api/v1}") String openRouterBaseUrl,
      @Value("${ai.openai.base-url:}") String openAiBaseUrl) {
    return new StreamingChatModelFactory(copilotBaseUrl, openRouterBaseUrl, openAiBaseUrl);
  }

  @Bean
  @ConditionalOnMissingBean(ChatModelFactory.class)
  public ChatModelFactory chatModelFactory(
      @Value("${ai.copilot.base-url:https://models.inference.ai.azure.com}") String copilotBaseUrl,
      @Value("${ai.openrouter.base-url:https://openrouter.ai/api/v1}") String openRouterBaseUrl,
      @Value("${ai.openai.base-url:}") String openAiBaseUrl) {
    return new ChatModelFactory(copilotBaseUrl, openRouterBaseUrl, openAiBaseUrl);
  }

  @Bean
  @ConditionalOnMissingBean(AgentFactory.class)
  public AgentFactory agentFactory(TenantChatMemoryStore memoryStore, AiToolRegistry toolRegistry,
      StreamingChatModelFactory streamingModelFactory, ChatModelFactory chatModelFactory) {
    return new AgentFactory(memoryStore, toolRegistry, streamingModelFactory, chatModelFactory);
  }

  @Bean
  @ConditionalOnMissingBean(AgentCache.class)
  public AgentCache agentCache(AgentFactory agentFactory) {
    return new AgentCache(agentFactory);
  }

  @Bean
  @ConditionalOnMissingBean(AiAgentConfigProvider.class)
  public AiAgentConfigProvider aiAgentConfigProvider(AiAgentConfigService aiAgentConfigService) {
    return new CachingAiAgentConfigProvider(aiAgentConfigService);
  }

  @Bean
  @ConditionalOnMissingBean(AiAgentManagerWrapper.class)
  public AiAgentManagerWrapper aiAgentManagerWrapper(AiAgentConfigProvider aiAgentConfigProvider,
      AiChatMemoryManagerWrapper chatMemoryManagerWrapper, AgentFactory agentFactory) {
    return new AiAgentManagerWrapper(aiAgentConfigProvider, chatMemoryManagerWrapper, agentFactory);
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
  public AiAgentConfigController aiAgentConfigController(AiAgentConfigService service,
      AttributeFilter filter) {
    return new AiAgentConfigController(service, filter);
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
