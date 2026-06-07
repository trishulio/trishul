package io.trishul.ai.service.autoconfiguration;

import io.trishul.ai.service.speech.model.controller.AiSpeechConfigController;
import io.trishul.ai.service.speech.model.repository.AiSpeechConfigRepository;
import io.trishul.ai.service.speech.model.service.AiSpeechConfigService;
import io.trishul.ai.speech.model.AiSpeechConfig;
import io.trishul.ai.speech.model.AiSpeechConfigAccessor;
import io.trishul.ai.speech.model.AiSpeechConfigRefresher;
import io.trishul.ai.speech.model.BaseAiSpeechConfig;
import io.trishul.ai.speech.model.UpdateAiSpeechConfig;
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
public class AiSpeechConfigAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(AiSpeechConfigService.class)
  public AiSpeechConfigService aiSpeechConfigService(LockService lockService,
      AiSpeechConfigRepository repository,
      Refresher<AiSpeechConfig, AiSpeechConfigAccessor<?>> refresher) {
    EntityMergerService<Long, AiSpeechConfig, BaseAiSpeechConfig<?>, UpdateAiSpeechConfig<?>> entityMergerService
        = new CrudEntityMergerService<>(lockService, BaseAiSpeechConfig.class,
            UpdateAiSpeechConfig.class, AiSpeechConfig.class, Set.of());

    RepoService<Long, AiSpeechConfig, AiSpeechConfigAccessor<?>> repoService
        = new CrudRepoService<>(repository, refresher);

    return new AiSpeechConfigService(entityMergerService, repoService);
  }

  @Bean
  @ConditionalOnMissingBean(AiSpeechConfigController.class)
  public AiSpeechConfigController aiSpeechConfigController(AiSpeechConfigService service) {
    return new AiSpeechConfigController(service);
  }

  @Bean
  public AccessorRefresher<Long, AiSpeechConfigAccessor<?>, AiSpeechConfig> aiSpeechConfigAccessorRefresher(
      AiSpeechConfigRepository repository) {
    return new AccessorRefresher<>(AiSpeechConfig.class, AiSpeechConfigAccessor::getSpeechConfig,
        AiSpeechConfigAccessor::setSpeechConfig, repository::findAllById);
  }

  @Bean
  public Refresher<AiSpeechConfig, AiSpeechConfigAccessor<?>> aiSpeechConfigRefresher(
      AccessorRefresher<Long, AiSpeechConfigAccessor<?>, AiSpeechConfig> accessorRefresher) {
    return new AiSpeechConfigRefresher(accessorRefresher);
  }
}
