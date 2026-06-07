package io.trishul.integration.communication.service.autoconfiguration;

import io.trishul.communication.service.message.CommunicationMessageService;
import io.trishul.crud.service.CrudEntityMergerService;
import io.trishul.crud.service.CrudRepoService;
import io.trishul.crud.service.EntityMergerService;
import io.trishul.integration.communication.model.BaseIntegrationCommunicationConfig;
import io.trishul.integration.communication.model.IntegrationCommunicationConfig;
import io.trishul.integration.communication.model.IntegrationCommunicationConfigAccessor;
import io.trishul.integration.communication.model.UpdateIntegrationCommunicationConfig;
import io.trishul.integration.communication.service.repository.IntegrationCommunicationConfigRepository;
import io.trishul.integration.communication.service.service.IntegrationCommunicationService;
import io.trishul.repo.jpa.repository.service.RepoService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IntegrationCommunicationServiceAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(IntegrationCommunicationService.class)
  public IntegrationCommunicationService integrationCommunicationService(
      io.trishul.crud.service.LockService lockService,
      IntegrationCommunicationConfigRepository configRepository,
      io.trishul.integration.communication.model.IntegrationCommunicationConfigRefresher configRefresher,
      CommunicationMessageService communicationMessageService) {
    EntityMergerService<Long, IntegrationCommunicationConfig, BaseIntegrationCommunicationConfig<?>, UpdateIntegrationCommunicationConfig<?>> entityMergerService
        = new CrudEntityMergerService<>(lockService, BaseIntegrationCommunicationConfig.class,
            UpdateIntegrationCommunicationConfig.class, IntegrationCommunicationConfig.class,
            java.util.Set.of());
    RepoService<Long, IntegrationCommunicationConfig, IntegrationCommunicationConfigAccessor<?>> repoService
        = new CrudRepoService<>(configRepository, configRefresher);

    return new IntegrationCommunicationService(entityMergerService, repoService,
        communicationMessageService);
  }

  @Bean
  public io.trishul.model.base.pojo.refresher.accessor.AccessorRefresher<Long, IntegrationCommunicationConfigAccessor<?>, IntegrationCommunicationConfig> integrationCommunicationConfigAccessorRefresher(
      IntegrationCommunicationConfigRepository repo) {
    return new io.trishul.model.base.pojo.refresher.accessor.AccessorRefresher<>(
        IntegrationCommunicationConfig.class,
        IntegrationCommunicationConfigAccessor::getIntegrationCommunicationConfig,
        (accessor, config) -> accessor.setIntegrationCommunicationConfig(config),
        ids -> repo.findAllById(ids));
  }

  @Bean
  public io.trishul.integration.communication.model.IntegrationCommunicationConfigRefresher integrationCommunicationConfigRefresher(
      io.trishul.model.base.pojo.refresher.accessor.AccessorRefresher<Long, IntegrationCommunicationConfigAccessor<?>, IntegrationCommunicationConfig> integrationCommunicationConfigAccessorRefresher,
      io.trishul.base.types.base.pojo.Refresher<io.trishul.integration.model.Integration, io.trishul.integration.model.IntegrationAccessor<?>> integrationRefresher) {
    return new io.trishul.integration.communication.model.IntegrationCommunicationConfigRefresher(
        integrationCommunicationConfigAccessorRefresher, integrationRefresher);
  }
}
