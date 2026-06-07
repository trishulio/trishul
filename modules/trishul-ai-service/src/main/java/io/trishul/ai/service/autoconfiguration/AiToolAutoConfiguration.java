package io.trishul.ai.service.autoconfiguration;

import io.trishul.ai.service.tool.model.controller.AiToolController;
import io.trishul.ai.service.tool.model.repository.AiToolRepository;
import io.trishul.ai.service.tool.model.service.AiToolService;
import io.trishul.ai.tool.model.AiTool;
import io.trishul.ai.tool.model.AiToolAccessor;
import io.trishul.ai.tool.model.AiToolRefresher;
import io.trishul.ai.tool.model.BaseAiTool;
import io.trishul.ai.tool.model.UpdateAiTool;
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

import io.trishul.ai.service.tool.registry.AiToolRegistry;

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
  public AiToolController aiToolController(AiToolService service,
      io.trishul.crud.controller.filter.AttributeFilter filter) {
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
