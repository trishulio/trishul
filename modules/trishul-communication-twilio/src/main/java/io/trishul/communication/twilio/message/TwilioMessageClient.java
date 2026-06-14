package io.trishul.communication.twilio.message;

import static com.twilio.rest.api.v2010.account.Message.creator;
import static com.twilio.rest.api.v2010.account.Message.deleter;
import static com.twilio.rest.api.v2010.account.Message.fetcher;

import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.rest.api.v2010.account.MessageFetcher;
import com.twilio.type.PhoneNumber;
import io.trishul.communication.model.channel.ChannelType;
import io.trishul.communication.model.message.BaseMessage;
import io.trishul.communication.model.message.Message;
import io.trishul.communication.model.message.UpdateMessage;
import io.trishul.iaas.client.IaasClient;
import io.trishul.iaas.mapper.IaasEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwilioMessageClient
    implements IaasClient<String, Message, BaseMessage<?>, UpdateMessage<?>> {
  private static final Logger log = LoggerFactory.getLogger(TwilioMessageClient.class);

  private final String accountSid;
  private final IaasEntityMapper<com.twilio.rest.api.v2010.account.Message, Message> mapper;

  public TwilioMessageClient(String accountSid,
      IaasEntityMapper<com.twilio.rest.api.v2010.account.Message, Message> mapper) {
    this.accountSid = accountSid;
    this.mapper = mapper;
  }

  @Override
  public Message get(String messageSid) {
    try {
      MessageFetcher fetcher = fetcher(accountSid, messageSid);
      com.twilio.rest.api.v2010.account.Message twilioMessage = fetcher.fetch();
      return mapper.fromIaasEntity(twilioMessage);
    } catch (Exception e) {
      log.error("Failed to fetch message: {}", messageSid, e);
      return null;
    }
  }

  @Override
  public <BE extends BaseMessage<?>> Message add(BE entity) {
    String to = formatAddress(entity.getTo(), entity.getChannelType());
    String from = formatAddress(entity.getFrom(), entity.getChannelType());

    MessageCreator creator
        = creator(accountSid, new PhoneNumber(to), new PhoneNumber(from), entity.getBody());

    com.twilio.rest.api.v2010.account.Message twilioMessage = creator.create();
    return mapper.fromIaasEntity(twilioMessage);
  }

  @Override
  public <UE extends UpdateMessage<?>> Message put(UE entity) {
    // Messages are immutable in Twilio; treat put as get-or-create
    if (entity.getId() != null && exists(entity.getId())) {
      return get(entity.getId());
    }
    return add(entity);
  }

  @Override
  public boolean delete(String messageSid) {
    try {
      return deleter(accountSid, messageSid).delete();
    } catch (Exception e) {
      log.error("Failed to delete message: {}", messageSid, e);
      return false;
    }
  }

  @Override
  public boolean exists(String messageSid) {
    return get(messageSid) != null;
  }

  private String formatAddress(String address, ChannelType channelType) {
    if (channelType == ChannelType.WHATSAPP && address != null
        && !address.startsWith("whatsapp:")) {
      return "whatsapp:" + address;
    }
    return address;
  }
}
