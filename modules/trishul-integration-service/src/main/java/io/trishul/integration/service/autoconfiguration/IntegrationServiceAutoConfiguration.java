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

import io.trishul.crud.service.LockService;
import io.trishul.integration.model.IntegrationRefresher;
import io.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;
import static java.util.Set.of;

@Configuration
public class IntegrationServiceAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(IntegrationService.class)
  public IntegrationService integrationService(LockService lockService,
      IntegrationRepository integrationRepository, IntegrationRefresher integrationRefresher) {
    EntityMergerService<Long, Integration, BaseIntegration<?>, UpdateIntegration<?>> entityMergerService
        = new CrudEntityMergerService<>(lockService, BaseIntegration.class, UpdateIntegration.class,
            Integration.class, of());
    RepoService<Long, Integration, IntegrationAccessor<?>> repoService
        = new CrudRepoService<>(integrationRepository, integrationRefresher);

    return new IntegrationService(entityMergerService, repoService);
  }

  @Bean
  public AccessorRefresher<Long, IntegrationAccessor<?>, Integration> integrationAccessorRefresher(
      IntegrationRepository repo) {
    return new AccessorRefresher<>(Integration.class, IntegrationAccessor::getIntegration,
        (accessor, integration) -> accessor.setIntegration(integration),
        ids -> repo.findAllById(ids));
  }

  @Bean
  public IntegrationRefresher integrationRefresher(
      AccessorRefresher<Long, IntegrationAccessor<?>, Integration> integrationAccessorRefresher) {
    return new IntegrationRefresher(integrationAccessorRefresher);
  }
}
