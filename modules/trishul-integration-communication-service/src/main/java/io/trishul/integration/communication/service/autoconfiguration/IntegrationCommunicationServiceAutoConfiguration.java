package io.trishul.integration.communication.service.autoconfiguration;

import static java.util.Set.of;

import io.trishul.base.types.base.pojo.Refresher;
import io.trishul.communication.service.message.CommunicationMessageService;
import io.trishul.crud.service.CrudEntityMergerService;
import io.trishul.crud.service.CrudRepoService;
import io.trishul.crud.service.EntityMergerService;
import io.trishul.crud.service.LockService;
import io.trishul.integration.communication.model.BaseIntegrationCommunicationConfig;
import io.trishul.integration.communication.model.IntegrationCommunicationConfig;
import io.trishul.integration.communication.model.IntegrationCommunicationConfigAccessor;
import io.trishul.integration.communication.model.IntegrationCommunicationConfigRefresher;
import io.trishul.integration.communication.model.UpdateIntegrationCommunicationConfig;
import io.trishul.integration.communication.service.repository.IntegrationCommunicationConfigRepository;
import io.trishul.integration.communication.service.service.IntegrationCommunicationService;
import io.trishul.integration.model.Integration;
import io.trishul.integration.model.IntegrationAccessor;
import io.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;
import io.trishul.repo.jpa.repository.service.RepoService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
