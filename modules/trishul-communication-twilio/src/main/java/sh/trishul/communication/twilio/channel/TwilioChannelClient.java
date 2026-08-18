package sh.trishul.communication.twilio.channel;

import com.twilio.rest.api.v2010.account.IncomingPhoneNumber;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sh.trishul.communication.model.channel.BaseCommunicationChannel;
import sh.trishul.communication.model.channel.CommunicationChannel;
import sh.trishul.communication.model.channel.UpdateCommunicationChannel;
import sh.trishul.iaas.client.IaasClient;
import sh.trishul.iaas.mapper.IaasEntityMapper;

public class TwilioChannelClient implements
    IaasClient<String, CommunicationChannel, BaseCommunicationChannel<?>, UpdateCommunicationChannel<?>> {
  private static final Logger log = LoggerFactory.getLogger(TwilioChannelClient.class);

  private final String accountSid;
  private final IaasEntityMapper<IncomingPhoneNumber, CommunicationChannel> mapper;

  public TwilioChannelClient(String accountSid,
      IaasEntityMapper<IncomingPhoneNumber, CommunicationChannel> mapper) {
    this.accountSid = accountSid;
    this.mapper = mapper;
  }

  @Override
  public CommunicationChannel get(String phoneNumberSid) {
    try {
      IncomingPhoneNumber phoneNumber
          = IncomingPhoneNumber.fetcher(accountSid, phoneNumberSid).fetch();
      return mapper.fromIaasEntity(phoneNumber);
    } catch (Exception e) {
      log.error("Failed to fetch phone number: {}", phoneNumberSid, e);
      return null;
    }
  }

  @Override
  public <BE extends BaseCommunicationChannel<?>> CommunicationChannel add(BE entity) {
    IncomingPhoneNumber phoneNumber = IncomingPhoneNumber.creator(accountSid)
        .setPhoneNumber(new PhoneNumber(entity.getAddress()))
        .setFriendlyName(entity.getDisplayName()).create();

    return mapper.fromIaasEntity(phoneNumber);
  }

  @Override
  public <UE extends UpdateCommunicationChannel<?>> CommunicationChannel put(UE entity) {
    if (entity.getId() != null && exists(entity.getId())) {
      IncomingPhoneNumber phoneNumber = IncomingPhoneNumber.updater(accountSid, entity.getId())
          .setFriendlyName(entity.getDisplayName()).update();
      return mapper.fromIaasEntity(phoneNumber);
    }
    return add(entity);
  }

  @Override
  public boolean delete(String phoneNumberSid) {
    try {
      return IncomingPhoneNumber.deleter(accountSid, phoneNumberSid).delete();
    } catch (Exception e) {
      log.error("Failed to delete phone number: {}", phoneNumberSid, e);
      return false;
    }
  }

  @Override
  public boolean exists(String phoneNumberSid) {
    return get(phoneNumberSid) != null;
  }
}
