package io.trishul.ai.service.autoconfiguration;

import io.trishul.ai.guardrail.model.AiGuardrail;
import io.trishul.ai.guardrail.model.AiGuardrailAccessor;
import io.trishul.ai.guardrail.model.AiGuardrailRefresher;
import io.trishul.ai.guardrail.model.BaseAiGuardrail;
import io.trishul.ai.guardrail.model.UpdateAiGuardrail;
import io.trishul.ai.service.guardrail.model.controller.AiGuardrailController;
import io.trishul.ai.service.guardrail.model.repository.AiGuardrailRepository;
import io.trishul.ai.service.guardrail.model.service.AiGuardrailService;
import io.trishul.ai.service.guardrail.pipeline.GuardrailPipeline;
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
public class AiGuardrailAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(GuardrailPipeline.class)
  public GuardrailPipeline guardrailPipeline() {
    return new GuardrailPipeline();
  }

  @Bean
  @ConditionalOnMissingBean(AiGuardrailService.class)
  public AiGuardrailService aiGuardrailService(LockService lockService,
      AiGuardrailRepository repository, Refresher<AiGuardrail, AiGuardrailAccessor<?>> refresher) {
    EntityMergerService<Long, AiGuardrail, BaseAiGuardrail<?>, UpdateAiGuardrail<?>> entityMergerService
        = new CrudEntityMergerService<>(lockService, BaseAiGuardrail.class, UpdateAiGuardrail.class,
            AiGuardrail.class, Set.of());

    RepoService<Long, AiGuardrail, AiGuardrailAccessor<?>> repoService
        = new CrudRepoService<>(repository, refresher);

    return new AiGuardrailService(entityMergerService, repoService);
  }

  @Bean
  @ConditionalOnMissingBean(AiGuardrailController.class)
  public AiGuardrailController aiGuardrailController(AiGuardrailService service,
      AttributeFilter filter) {
    return new AiGuardrailController(service, filter);
  }

  @Bean
  public AccessorRefresher<Long, AiGuardrailAccessor<?>, AiGuardrail> aiGuardrailAccessorRefresher(
      AiGuardrailRepository repository) {
    return new AccessorRefresher<>(AiGuardrail.class, AiGuardrailAccessor::getGuardrail,
        AiGuardrailAccessor::setGuardrail, repository::findAllById);
  }

  @Bean
  public Refresher<AiGuardrail, AiGuardrailAccessor<?>> aiGuardrailRefresher(
      AccessorRefresher<Long, AiGuardrailAccessor<?>, AiGuardrail> accessorRefresher) {
    return new AiGuardrailRefresher(accessorRefresher);
  }
}
