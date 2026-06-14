package io.trishul.communication.service.autoconfiguration;

import static java.util.Set.of;

import io.trishul.communication.model.account.BaseCommunicationAccount;
import io.trishul.communication.model.account.CommunicationAccount;
import io.trishul.communication.model.account.UpdateCommunicationAccount;
import io.trishul.communication.model.channel.BaseCommunicationChannel;
import io.trishul.communication.model.channel.CommunicationChannel;
import io.trishul.communication.model.channel.UpdateCommunicationChannel;
import io.trishul.communication.model.message.BaseMessage;
import io.trishul.communication.model.message.Message;
import io.trishul.communication.model.message.UpdateMessage;
import io.trishul.communication.service.account.CommunicationAccountService;
import io.trishul.communication.service.channel.CommunicationChannelService;
import io.trishul.communication.service.message.CommunicationMessageService;
import io.trishul.crud.service.CrudEntityMergerService;
import io.trishul.crud.service.EntityMergerService;
import io.trishul.crud.service.LockService;
import io.trishul.iaas.client.BulkIaasClient;
import io.trishul.iaas.client.IaasClient;
import io.trishul.iaas.repository.IaasRepository;
import io.trishul.model.executor.BlockingAsyncExecutor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommunicationServiceAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(CommunicationMessageService.class)
  public CommunicationMessageService communicationMessageService(LockService lockService,
      BlockingAsyncExecutor executor,
      IaasClient<String, Message, BaseMessage<?>, UpdateMessage<?>> messageClient) {
    IaasRepository<String, Message, BaseMessage<?>, UpdateMessage<?>> messageRepo
        = new BulkIaasClient<>(executor, messageClient);
    EntityMergerService<String, Message, BaseMessage<?>, UpdateMessage<?>> mergerService
        = new CrudEntityMergerService<>(lockService, BaseMessage.class, UpdateMessage.class,
            Message.class, of());

    return new CommunicationMessageService(mergerService, messageRepo);
  }

  @Bean
  @ConditionalOnMissingBean(CommunicationChannelService.class)
  public CommunicationChannelService communicationChannelService(LockService lockService,
      BlockingAsyncExecutor executor,
      IaasClient<String, CommunicationChannel, BaseCommunicationChannel<?>, UpdateCommunicationChannel<?>> channelClient) {
    IaasRepository<String, CommunicationChannel, BaseCommunicationChannel<?>, UpdateCommunicationChannel<?>> channelRepo
        = new BulkIaasClient<>(executor, channelClient);
    EntityMergerService<String, CommunicationChannel, BaseCommunicationChannel<?>, UpdateCommunicationChannel<?>> mergerService
        = new CrudEntityMergerService<>(lockService, BaseCommunicationChannel.class,
            UpdateCommunicationChannel.class, CommunicationChannel.class, of());

    return new CommunicationChannelService(mergerService, channelRepo);
  }

  @Bean
  @ConditionalOnMissingBean(CommunicationAccountService.class)
  public CommunicationAccountService communicationAccountService(LockService lockService,
      BlockingAsyncExecutor executor,
      IaasClient<String, CommunicationAccount, BaseCommunicationAccount<?>, UpdateCommunicationAccount<?>> accountClient) {
    IaasRepository<String, CommunicationAccount, BaseCommunicationAccount<?>, UpdateCommunicationAccount<?>> accountRepo
        = new BulkIaasClient<>(executor, accountClient);
    EntityMergerService<String, CommunicationAccount, BaseCommunicationAccount<?>, UpdateCommunicationAccount<?>> mergerService
        = new CrudEntityMergerService<>(lockService, BaseCommunicationAccount.class,
            UpdateCommunicationAccount.class, CommunicationAccount.class, of());

    return new CommunicationAccountService(mergerService, accountRepo);
  }
}
