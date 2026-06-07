package io.trishul.integration.service.autoconfiguration;

import io.trishul.crud.service.CrudEntityMergerService;
import io.trishul.crud.service.CrudRepoService;
import io.trishul.crud.service.EntityMergerService;
import io.trishul.integration.model.BaseIntegration;
import io.trishul.integration.model.Integration;
import io.trishul.integration.model.IntegrationAccessor;
import io.trishul.integration.model.UpdateIntegration;
import io.trishul.integration.service.repository.IntegrationRepository;
import io.trishul.integration.service.service.IntegrationService;
import io.trishul.repo.jpa.repository.service.RepoService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IntegrationServiceAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(IntegrationService.class)
  public IntegrationService integrationService(io.trishul.crud.service.LockService lockService,
      IntegrationRepository integrationRepository,
      io.trishul.integration.model.IntegrationRefresher integrationRefresher) {
    EntityMergerService<Long, Integration, BaseIntegration<?>, UpdateIntegration<?>> entityMergerService
        = new CrudEntityMergerService<>(lockService, BaseIntegration.class, UpdateIntegration.class,
            Integration.class, java.util.Set.of());
    RepoService<Long, Integration, IntegrationAccessor<?>> repoService
        = new CrudRepoService<>(integrationRepository, integrationRefresher);

    return new IntegrationService(entityMergerService, repoService);
  }

  @Bean
  public io.trishul.model.base.pojo.refresher.accessor.AccessorRefresher<Long, IntegrationAccessor<?>, Integration> integrationAccessorRefresher(
      IntegrationRepository repo) {
    return new io.trishul.model.base.pojo.refresher.accessor.AccessorRefresher<>(Integration.class,
        IntegrationAccessor::getIntegration,
        (accessor, integration) -> accessor.setIntegration(integration),
        ids -> repo.findAllById(ids));
  }

  @Bean
  public io.trishul.integration.model.IntegrationRefresher integrationRefresher(
      io.trishul.model.base.pojo.refresher.accessor.AccessorRefresher<Long, IntegrationAccessor<?>, Integration> integrationAccessorRefresher) {
    return new io.trishul.integration.model.IntegrationRefresher(integrationAccessorRefresher);
  }
}
