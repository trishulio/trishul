package io.trishul.ai.service.autoconfiguration;

import io.trishul.ai.chat.model.AiChatModelConfig;
import io.trishul.ai.chat.model.AiChatModelConfigAccessor;
import io.trishul.ai.chat.model.AiChatModelConfigRefresher;
import io.trishul.ai.chat.model.BaseAiChatModelConfig;
import io.trishul.ai.chat.model.UpdateAiChatModelConfig;
import io.trishul.ai.service.chat.model.controller.AiChatModelConfigController;
import io.trishul.ai.service.chat.model.repository.AiChatModelConfigRepository;
import io.trishul.ai.service.chat.model.service.AiChatModelConfigService;
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

@Configuration
public class AiChatModelConfigAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(AiChatModelConfigService.class)
  public AiChatModelConfigService aiChatModelConfigService(LockService lockService,
      AiChatModelConfigRepository repository,
      Refresher<AiChatModelConfig, AiChatModelConfigAccessor<?>> refresher) {
    EntityMergerService<Long, AiChatModelConfig, BaseAiChatModelConfig<?>, UpdateAiChatModelConfig<?>> entityMergerService
        = new CrudEntityMergerService<>(lockService, BaseAiChatModelConfig.class,
            UpdateAiChatModelConfig.class, AiChatModelConfig.class, Set.of());

    RepoService<Long, AiChatModelConfig, AiChatModelConfigAccessor<?>> repoService
        = new CrudRepoService<>(repository, refresher);

    return new AiChatModelConfigService(entityMergerService, repoService);
  }

  @Bean
  @ConditionalOnMissingBean(AiChatModelConfigController.class)
  public AiChatModelConfigController aiChatModelConfigController(AiChatModelConfigService service) {
    return new AiChatModelConfigController(service);
  }

  @Bean
  public AccessorRefresher<Long, AiChatModelConfigAccessor<?>, AiChatModelConfig> aiChatModelConfigAccessorRefresher(
      AiChatModelConfigRepository repository) {
    return new AccessorRefresher<>(AiChatModelConfig.class,
        AiChatModelConfigAccessor::getChatModelConfig,
        AiChatModelConfigAccessor::setChatModelConfig, repository::findAllById);
  }

  @Bean
  public Refresher<AiChatModelConfig, AiChatModelConfigAccessor<?>> aiChatModelConfigRefresher(
      AccessorRefresher<Long, AiChatModelConfigAccessor<?>, AiChatModelConfig> accessorRefresher) {
    return new AiChatModelConfigRefresher(accessorRefresher);
  }
}
