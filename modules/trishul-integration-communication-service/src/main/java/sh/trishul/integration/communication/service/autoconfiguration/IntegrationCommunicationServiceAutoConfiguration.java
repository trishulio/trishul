package sh.trishul.integration.communication.service.autoconfiguration;

import static java.util.Set.of;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sh.trishul.base.types.base.pojo.Refresher;
import sh.trishul.communication.service.message.CommunicationMessageService;
import sh.trishul.crud.service.CrudEntityMergerService;
import sh.trishul.crud.service.CrudRepoService;
import sh.trishul.crud.service.EntityMergerService;
import sh.trishul.crud.service.LockService;
import sh.trishul.integration.communication.model.BaseIntegrationCommunicationConfig;
import sh.trishul.integration.communication.model.IntegrationCommunicationConfig;
import sh.trishul.integration.communication.model.IntegrationCommunicationConfigAccessor;
import sh.trishul.integration.communication.model.IntegrationCommunicationConfigRefresher;
import sh.trishul.integration.communication.model.UpdateIntegrationCommunicationConfig;
import sh.trishul.integration.communication.service.repository.IntegrationCommunicationConfigRepository;
import sh.trishul.integration.communication.service.service.IntegrationCommunicationService;
import sh.trishul.integration.model.Integration;
import sh.trishul.integration.model.IntegrationAccessor;
import sh.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;
import sh.trishul.repo.jpa.repository.service.RepoService;

@Configuration
public class IntegrationCommunicationServiceAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(IntegrationCommunicationService.class)
  public IntegrationCommunicationService integrationCommunicationService(LockService lockService,
      IntegrationCommunicationConfigRepository configRepository,
      IntegrationCommunicationConfigRefresher configRefresher,
      CommunicationMessageService communicationMessageService) {
    EntityMergerService<Long, IntegrationCommunicationConfig, BaseIntegrationCommunicationConfig<?>, UpdateIntegrationCommunicationConfig<?>> entityMergerService
        = new CrudEntityMergerService<>(lockService, BaseIntegrationCommunicationConfig.class,
            UpdateIntegrationCommunicationConfig.class, IntegrationCommunicationConfig.class, of());
    RepoService<Long, IntegrationCommunicationConfig, IntegrationCommunicationConfigAccessor<?>> repoService
        = new CrudRepoService<>(configRepository, configRefresher);

    return new IntegrationCommunicationService(entityMergerService, repoService,
        communicationMessageService);
  }

  @Bean
  public AccessorRefresher<Long, IntegrationCommunicationConfigAccessor<?>, IntegrationCommunicationConfig> integrationCommunicationConfigAccessorRefresher(
      IntegrationCommunicationConfigRepository repo) {
    return new AccessorRefresher<>(IntegrationCommunicationConfig.class,
        IntegrationCommunicationConfigAccessor::getIntegrationCommunicationConfig,
        (accessor, config) -> accessor.setIntegrationCommunicationConfig(config),
        ids -> repo.findAllById(ids));
  }

  @Bean
  public IntegrationCommunicationConfigRefresher integrationCommunicationConfigRefresher(
      AccessorRefresher<Long, IntegrationCommunicationConfigAccessor<?>, IntegrationCommunicationConfig> integrationCommunicationConfigAccessorRefresher,
      Refresher<Integration, IntegrationAccessor<?>> integrationRefresher) {
    return new IntegrationCommunicationConfigRefresher(
        integrationCommunicationConfigAccessorRefresher, integrationRefresher);
  }
}
