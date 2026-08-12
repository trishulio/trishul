package sh.trishul.ai.service.autoconfiguration;

import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sh.trishul.ai.chat.model.AiChatModelConfig;
import sh.trishul.ai.chat.model.AiChatModelConfigAccessor;
import sh.trishul.ai.chat.model.AiChatModelConfigRefresher;
import sh.trishul.ai.chat.model.BaseAiChatModelConfig;
import sh.trishul.ai.chat.model.UpdateAiChatModelConfig;
import sh.trishul.ai.service.chat.model.controller.AiChatModelConfigController;
import sh.trishul.ai.service.chat.model.repository.AiChatModelConfigRepository;
import sh.trishul.ai.service.chat.model.service.AiChatModelConfigService;
import sh.trishul.base.types.base.pojo.Refresher;
import sh.trishul.crud.controller.filter.AttributeFilter;
import sh.trishul.crud.service.CrudEntityMergerService;
import sh.trishul.crud.service.CrudRepoService;
import sh.trishul.crud.service.EntityMergerService;
import sh.trishul.crud.service.LockService;
import sh.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;
import sh.trishul.repo.jpa.converter.StringCryptoConverter;
import sh.trishul.repo.jpa.repository.service.RepoService;

@Configuration
public class AiChatModelConfigAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(StringCryptoConverter.class)
  public StringCryptoConverter stringCryptoConverter(
      @Value("${db.encryption.algorithm}") String algorithm,
      @Value("${db.encryption.key}") String encryptionKey) {
    return new StringCryptoConverter(algorithm, encryptionKey);
  }

  @Bean
  @ConditionalOnMissingBean(AiChatModelConfigService.class)
  public AiChatModelConfigService aiChatModelConfigService(LockService lockService,
      AiChatModelConfigRepository repository,
      Refresher<AiChatModelConfig, AiChatModelConfigAccessor<?>> refresher) {
    EntityMergerService<Long, AiChatModelConfig, BaseAiChatModelConfig<?>, UpdateAiChatModelConfig<?>> entityMergerService
        = new CrudEntityMergerService<>(lockService, BaseAiChatModelConfig.class,
            UpdateAiChatModelConfig.class, AiChatModelConfig.class, Set.of());

    RepoService<Long, AiChatModelConfig, AiChatModelConfigAccessor<?>> repoService
        = new CrudRepoService<>(repository, refresher);

    return new AiChatModelConfigService(entityMergerService, repoService);
  }

  @Bean
  @ConditionalOnMissingBean(AiChatModelConfigController.class)
  public AiChatModelConfigController aiChatModelConfigController(AiChatModelConfigService service,
      AttributeFilter filter) {
    return new AiChatModelConfigController(service, filter);
  }

  @Bean
  public AccessorRefresher<Long, AiChatModelConfigAccessor<?>, AiChatModelConfig> aiChatModelConfigAccessorRefresher(
      AiChatModelConfigRepository repository) {
    return new AccessorRefresher<>(AiChatModelConfig.class,
        AiChatModelConfigAccessor::getChatModelConfig,
        AiChatModelConfigAccessor::setChatModelConfig, repository::findAllById);
  }

  @Bean
  public Refresher<AiChatModelConfig, AiChatModelConfigAccessor<?>> aiChatModelConfigRefresher(
      AccessorRefresher<Long, AiChatModelConfigAccessor<?>, AiChatModelConfig> accessorRefresher) {
    return new AiChatModelConfigRefresher(accessorRefresher);
  }
}
