package sh.trishul.ai.service.autoconfiguration;

import java.util.Set;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sh.trishul.ai.service.speech.model.controller.AiSpeechConfigController;
import sh.trishul.ai.service.speech.model.repository.AiSpeechConfigRepository;
import sh.trishul.ai.service.speech.model.service.AiSpeechConfigService;
import sh.trishul.ai.speech.model.AiSpeechConfig;
import sh.trishul.ai.speech.model.AiSpeechConfigAccessor;
import sh.trishul.ai.speech.model.AiSpeechConfigRefresher;
import sh.trishul.ai.speech.model.BaseAiSpeechConfig;
import sh.trishul.ai.speech.model.UpdateAiSpeechConfig;
import sh.trishul.base.types.base.pojo.Refresher;
import sh.trishul.crud.controller.filter.AttributeFilter;
import sh.trishul.crud.service.CrudEntityMergerService;
import sh.trishul.crud.service.CrudRepoService;
import sh.trishul.crud.service.EntityMergerService;
import sh.trishul.crud.service.LockService;
import sh.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;
import sh.trishul.repo.jpa.repository.service.RepoService;

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
  public AiSpeechConfigController aiSpeechConfigController(AiSpeechConfigService service,
      AttributeFilter filter) {
    return new AiSpeechConfigController(service, filter);
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
