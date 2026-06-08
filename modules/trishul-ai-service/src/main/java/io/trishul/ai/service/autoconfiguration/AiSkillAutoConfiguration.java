package io.trishul.ai.service.autoconfiguration;

import io.trishul.ai.service.skill.model.controller.AiSkillController;
import io.trishul.ai.service.skill.model.repository.AiSkillRepository;
import io.trishul.ai.service.skill.model.service.AiSkillService;
import io.trishul.ai.skill.model.AiSkill;
import io.trishul.ai.skill.model.AiSkillAccessor;
import io.trishul.ai.skill.model.AiSkillRefresher;
import io.trishul.ai.skill.model.BaseAiSkill;
import io.trishul.ai.skill.model.UpdateAiSkill;
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

import io.trishul.crud.controller.filter.AttributeFilter;

@Configuration
public class AiSkillAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(AiSkillService.class)
  public AiSkillService aiSkillService(LockService lockService, AiSkillRepository repository,
      Refresher<AiSkill, AiSkillAccessor<?>> refresher) {
    EntityMergerService<Long, AiSkill, BaseAiSkill<?>, UpdateAiSkill<?>> entityMergerService
        = new CrudEntityMergerService<>(lockService, BaseAiSkill.class, UpdateAiSkill.class,
            AiSkill.class, Set.of());

    RepoService<Long, AiSkill, AiSkillAccessor<?>> repoService
        = new CrudRepoService<>(repository, refresher);

    return new AiSkillService(entityMergerService, repoService);
  }

  @Bean
  @ConditionalOnMissingBean(AiSkillController.class)
  public AiSkillController aiSkillController(AiSkillService service, AttributeFilter filter) {
    return new AiSkillController(service, filter);
  }

  @Bean
  public AccessorRefresher<Long, AiSkillAccessor<?>, AiSkill> aiSkillAccessorRefresher(
      AiSkillRepository repository) {
    return new AccessorRefresher<>(AiSkill.class, AiSkillAccessor::getSkill,
        AiSkillAccessor::setSkill, repository::findAllById);
  }

  @Bean
  public Refresher<AiSkill, AiSkillAccessor<?>> aiSkillRefresher(
      AccessorRefresher<Long, AiSkillAccessor<?>, AiSkill> accessorRefresher) {
    return new AiSkillRefresher(accessorRefresher);
  }
}
