package io.trishul.ai.service.autoconfiguration;

import io.trishul.ai.agent.model.AiAgentConfig;
import io.trishul.ai.agent.model.AiAgentConfigAccessor;
import io.trishul.ai.memory.model.AiChatMemoryConfig;
import io.trishul.ai.memory.model.AiChatMemoryConfigAccessor;
import io.trishul.ai.service.session.model.controller.AiChatSessionController;
import io.trishul.ai.service.session.model.repository.AiChatSessionRepository;
import io.trishul.ai.service.session.model.service.AiChatSessionService;
import io.trishul.ai.session.model.AiChatSession;
import io.trishul.ai.session.model.AiChatSessionAccessor;
import io.trishul.ai.session.model.AiChatSessionRefresher;
import io.trishul.ai.session.model.BaseAiChatSession;
import io.trishul.ai.session.model.UpdateAiChatSession;
import io.trishul.base.types.base.pojo.Refresher;
import io.trishul.crud.controller.filter.AttributeFilter;
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

@Configuration
public class AiChatSessionAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(AiChatSessionService.class)
  public AiChatSessionService aiChatSessionService(LockService lockService,
      AiChatSessionRepository repository,
      Refresher<AiChatSession, AiChatSessionAccessor<?>> refresher) {
    EntityMergerService<Long, AiChatSession, BaseAiChatSession<?>, UpdateAiChatSession<?>> entityMergerService
        = new CrudEntityMergerService<>(lockService, BaseAiChatSession.class,
            UpdateAiChatSession.class, AiChatSession.class, Set.of());

    RepoService<Long, AiChatSession, AiChatSessionAccessor<?>> repoService
        = new CrudRepoService<>(repository, refresher);

    return new AiChatSessionService(entityMergerService, repoService);
  }

  @Bean
  @ConditionalOnMissingBean(AiChatSessionController.class)
  public AiChatSessionController aiChatSessionController(AiChatSessionService service,
      AttributeFilter filter) {
    return new AiChatSessionController(service, filter);
  }

  @Bean
  public AccessorRefresher<Long, AiChatSessionAccessor<?>, AiChatSession> aiChatSessionAccessorRefresher(
      AiChatSessionRepository repository) {
    return new AccessorRefresher<>(AiChatSession.class, AiChatSessionAccessor::getChatSession,
        AiChatSessionAccessor::setChatSession, repository::findAllById);
  }

  @Bean
  public Refresher<AiChatSession, AiChatSessionAccessor<?>> aiChatSessionRefresher(
      AccessorRefresher<Long, AiChatSessionAccessor<?>, AiChatSession> accessorRefresher,
      Refresher<AiAgentConfig, AiAgentConfigAccessor<?>> agentConfigRefresher,
      Refresher<AiChatMemoryConfig, AiChatMemoryConfigAccessor<?>> chatMemoryConfigRefresher) {
    return new AiChatSessionRefresher(accessorRefresher, agentConfigRefresher,
        chatMemoryConfigRefresher);
  }
}
