package sh.trishul.ai.service.autoconfiguration;

import java.util.Set;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sh.trishul.ai.service.tool.model.controller.AiToolController;
import sh.trishul.ai.service.tool.model.repository.AiToolRepository;
import sh.trishul.ai.service.tool.model.service.AiToolService;
import sh.trishul.ai.service.tool.registry.AiToolRegistry;
import sh.trishul.ai.tool.model.AiTool;
import sh.trishul.ai.tool.model.AiToolAccessor;
import sh.trishul.ai.tool.model.AiToolRefresher;
import sh.trishul.ai.tool.model.BaseAiTool;
import sh.trishul.ai.tool.model.UpdateAiTool;
import sh.trishul.base.types.base.pojo.Refresher;
import sh.trishul.crud.controller.filter.AttributeFilter;
import sh.trishul.crud.service.CrudEntityMergerService;
import sh.trishul.crud.service.CrudRepoService;
import sh.trishul.crud.service.EntityMergerService;
import sh.trishul.crud.service.LockService;
import sh.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;
import sh.trishul.repo.jpa.repository.service.RepoService;

@Configuration
public class AiToolAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(AiToolRegistry.class)
  public AiToolRegistry aiToolRegistry() {
    return new AiToolRegistry();
  }

  @Bean
  @ConditionalOnMissingBean(AiToolService.class)
  public AiToolService aiToolService(LockService lockService, AiToolRepository repository,
      Refresher<AiTool, AiToolAccessor<?>> refresher) {
    EntityMergerService<Long, AiTool, BaseAiTool<?>, UpdateAiTool<?>> entityMergerService
        = new CrudEntityMergerService<>(lockService, BaseAiTool.class, UpdateAiTool.class,
            AiTool.class, Set.of());

    RepoService<Long, AiTool, AiToolAccessor<?>> repoService
        = new CrudRepoService<>(repository, refresher);

    return new AiToolService(entityMergerService, repoService);
  }

  @Bean
  @ConditionalOnMissingBean(AiToolController.class)
  public AiToolController aiToolController(AiToolService service, AttributeFilter filter) {
    return new AiToolController(service, filter);
  }

  @Bean
  public AccessorRefresher<Long, AiToolAccessor<?>, AiTool> aiToolAccessorRefresher(
      AiToolRepository repository) {
    return new AccessorRefresher<>(AiTool.class, AiToolAccessor::getTool, AiToolAccessor::setTool,
        repository::findAllById);
  }

  @Bean
  public Refresher<AiTool, AiToolAccessor<?>> aiToolRefresher(
      AccessorRefresher<Long, AiToolAccessor<?>, AiTool> accessorRefresher) {
    return new AiToolRefresher(accessorRefresher);
  }
}
