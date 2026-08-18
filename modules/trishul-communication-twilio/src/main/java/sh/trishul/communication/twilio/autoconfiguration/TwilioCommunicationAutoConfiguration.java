package sh.trishul.communication.twilio.autoconfiguration;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Value;
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
import sh.trishul.communication.twilio.account.TwilioAccountClient;
import sh.trishul.communication.twilio.account.TwilioAccountMapper;
import sh.trishul.communication.twilio.channel.TwilioChannelClient;
import sh.trishul.communication.twilio.channel.TwilioChannelMapper;
import sh.trishul.communication.twilio.message.TwilioMessageClient;
import sh.trishul.communication.twilio.message.TwilioMessageMapper;
import sh.trishul.iaas.client.IaasClient;

@Configuration
public class TwilioCommunicationAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(TwilioMessageClient.class)
  public IaasClient<String, Message, BaseMessage<?>, UpdateMessage<?>> twilioMessageClient(
      @Value("${twilio.account.sid}") String accountSid,
      @Value("${twilio.auth.token}") String authToken) {
    Twilio.init(accountSid, authToken);
    return (IaasClient<String, Message, BaseMessage<?>, UpdateMessage<?>>) (IaasClient) new TwilioMessageClient(
        accountSid, TwilioMessageMapper.INSTANCE);
  }

  @Bean
  @ConditionalOnMissingBean(TwilioChannelClient.class)
  public IaasClient<String, CommunicationChannel, BaseCommunicationChannel<?>, UpdateCommunicationChannel<?>> twilioChannelClient(
      @Value("${twilio.account.sid}") String accountSid,
      @Value("${twilio.auth.token}") String authToken) {
    Twilio.init(accountSid, authToken);
    return new TwilioChannelClient(accountSid, TwilioChannelMapper.INSTANCE);
  }

  @Bean
  @ConditionalOnMissingBean(TwilioAccountClient.class)
  public IaasClient<String, CommunicationAccount, BaseCommunicationAccount<?>, UpdateCommunicationAccount<?>> twilioAccountClient(
      @Value("${twilio.account.sid}") String accountSid,
      @Value("${twilio.auth.token}") String authToken) {
    Twilio.init(accountSid, authToken);
    return new TwilioAccountClient(TwilioAccountMapper.INSTANCE);
  }
}
