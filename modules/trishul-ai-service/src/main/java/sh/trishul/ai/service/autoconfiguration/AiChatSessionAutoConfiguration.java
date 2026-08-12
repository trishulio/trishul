package sh.trishul.ai.service.autoconfiguration;

import java.util.Set;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sh.trishul.ai.agent.model.AiAgentConfig;
import sh.trishul.ai.agent.model.AiAgentConfigAccessor;
import sh.trishul.ai.memory.model.AiChatMemoryConfig;
import sh.trishul.ai.memory.model.AiChatMemoryConfigAccessor;
import sh.trishul.ai.service.session.model.controller.AiChatSessionController;
import sh.trishul.ai.service.session.model.repository.AiChatSessionRepository;
import sh.trishul.ai.service.session.model.service.AiChatSessionService;
import sh.trishul.ai.session.model.AiChatSession;
import sh.trishul.ai.session.model.AiChatSessionAccessor;
import sh.trishul.ai.session.model.AiChatSessionRefresher;
import sh.trishul.ai.session.model.BaseAiChatSession;
import sh.trishul.ai.session.model.UpdateAiChatSession;
import sh.trishul.base.types.base.pojo.Refresher;
import sh.trishul.crud.controller.filter.AttributeFilter;
import sh.trishul.crud.service.CrudEntityMergerService;
import sh.trishul.crud.service.CrudRepoService;
import sh.trishul.crud.service.EntityMergerService;
import sh.trishul.crud.service.LockService;
import sh.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;
import sh.trishul.repo.jpa.repository.service.RepoService;

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
