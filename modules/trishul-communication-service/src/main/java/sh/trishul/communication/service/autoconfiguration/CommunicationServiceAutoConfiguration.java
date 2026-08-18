package sh.trishul.communication.service.autoconfiguration;

import static java.util.Set.of;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sh.trishul.communication.model.account.BaseCommunicationAccount;
import sh.trishul.communication.model.account.CommunicationAccount;
import sh.trishul.communication.model.account.UpdateCommunicationAccount;
import sh.trishul.communication.model.channel.BaseCommunicationChannel;
import sh.trishul.communication.model.channel.CommunicationChannel;
import sh.trishul.communication.model.channel.UpdateCommunicationChannel;
import sh.trishul.communication.model.message.BaseMessage;
import sh.trishul.communication.model.message.Message;
import sh.trishul.communication.model.message.UpdateMessage;
import sh.trishul.communication.service.account.CommunicationAccountService;
import sh.trishul.communication.service.channel.CommunicationChannelService;
import sh.trishul.communication.service.message.CommunicationMessageService;
import sh.trishul.crud.service.CrudEntityMergerService;
import sh.trishul.crud.service.EntityMergerService;
import sh.trishul.crud.service.LockService;
import sh.trishul.iaas.client.BulkIaasClient;
import sh.trishul.iaas.client.IaasClient;
import sh.trishul.iaas.repository.IaasRepository;
import sh.trishul.model.executor.BlockingAsyncExecutor;

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
