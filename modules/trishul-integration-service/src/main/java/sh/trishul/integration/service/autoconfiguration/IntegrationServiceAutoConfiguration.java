package sh.trishul.integration.service.autoconfiguration;

import static java.util.Set.of;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sh.trishul.crud.service.CrudEntityMergerService;
import sh.trishul.crud.service.CrudRepoService;
import sh.trishul.crud.service.EntityMergerService;
import sh.trishul.crud.service.LockService;
import sh.trishul.integration.model.BaseIntegration;
import sh.trishul.integration.model.Integration;
import sh.trishul.integration.model.IntegrationAccessor;
import sh.trishul.integration.model.IntegrationRefresher;
import sh.trishul.integration.model.UpdateIntegration;
import sh.trishul.integration.service.repository.IntegrationRepository;
import sh.trishul.integration.service.service.IntegrationService;
import sh.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;
import sh.trishul.repo.jpa.repository.service.RepoService;

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
