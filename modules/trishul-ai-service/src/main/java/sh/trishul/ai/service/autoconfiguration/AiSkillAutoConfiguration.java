package sh.trishul.ai.service.autoconfiguration;

import java.util.Set;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sh.trishul.ai.service.skill.model.controller.AiSkillController;
import sh.trishul.ai.service.skill.model.repository.AiSkillRepository;
import sh.trishul.ai.service.skill.model.service.AiSkillService;
import sh.trishul.ai.skill.model.AiSkill;
import sh.trishul.ai.skill.model.AiSkillAccessor;
import sh.trishul.ai.skill.model.AiSkillRefresher;
import sh.trishul.ai.skill.model.BaseAiSkill;
import sh.trishul.ai.skill.model.UpdateAiSkill;
import sh.trishul.base.types.base.pojo.Refresher;
import sh.trishul.crud.controller.filter.AttributeFilter;
import sh.trishul.crud.service.CrudEntityMergerService;
import sh.trishul.crud.service.CrudRepoService;
import sh.trishul.crud.service.EntityMergerService;
import sh.trishul.crud.service.LockService;
import sh.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;
import sh.trishul.repo.jpa.repository.service.RepoService;

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
