package sh.trishul.ai.service.autoconfiguration;

import java.util.Set;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sh.trishul.ai.guardrail.model.AiGuardrail;
import sh.trishul.ai.guardrail.model.AiGuardrailAccessor;
import sh.trishul.ai.guardrail.model.AiGuardrailRefresher;
import sh.trishul.ai.guardrail.model.BaseAiGuardrail;
import sh.trishul.ai.guardrail.model.UpdateAiGuardrail;
import sh.trishul.ai.service.guardrail.model.controller.AiGuardrailController;
import sh.trishul.ai.service.guardrail.model.repository.AiGuardrailRepository;
import sh.trishul.ai.service.guardrail.model.service.AiGuardrailService;
import sh.trishul.ai.service.guardrail.pipeline.GuardrailPipeline;
import sh.trishul.base.types.base.pojo.Refresher;
import sh.trishul.crud.controller.filter.AttributeFilter;
import sh.trishul.crud.service.CrudEntityMergerService;
import sh.trishul.crud.service.CrudRepoService;
import sh.trishul.crud.service.EntityMergerService;
import sh.trishul.crud.service.LockService;
import sh.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;
import sh.trishul.repo.jpa.repository.service.RepoService;

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
