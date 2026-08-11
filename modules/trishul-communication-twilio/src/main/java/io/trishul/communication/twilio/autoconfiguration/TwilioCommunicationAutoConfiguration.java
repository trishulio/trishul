package io.trishul.communication.twilio.autoconfiguration;

import com.twilio.Twilio;
import io.trishul.communication.model.account.BaseCommunicationAccount;
import io.trishul.communication.model.account.CommunicationAccount;
import io.trishul.communication.model.account.UpdateCommunicationAccount;
import io.trishul.communication.model.channel.BaseCommunicationChannel;
import io.trishul.communication.model.channel.CommunicationChannel;
import io.trishul.communication.model.channel.UpdateCommunicationChannel;
import io.trishul.communication.model.message.BaseMessage;
import io.trishul.communication.model.message.Message;
import io.trishul.communication.model.message.UpdateMessage;
import io.trishul.communication.twilio.account.TwilioAccountClient;
import io.trishul.communication.twilio.account.TwilioAccountMapper;
import io.trishul.communication.twilio.channel.TwilioChannelClient;
import io.trishul.communication.twilio.channel.TwilioChannelMapper;
import io.trishul.communication.twilio.message.TwilioMessageClient;
import io.trishul.communication.twilio.message.TwilioMessageMapper;
import io.trishul.iaas.client.IaasClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

