package sh.trishul.communication.twilio.channel;

import com.twilio.rest.api.v2010.account.IncomingPhoneNumber;
import sh.trishul.communication.model.channel.ChannelType;
import sh.trishul.communication.model.channel.CommunicationChannel;
import sh.trishul.iaas.mapper.IaasEntityMapper;

public class TwilioChannelMapper
    implements IaasEntityMapper<IncomingPhoneNumber, CommunicationChannel> {
  public static final TwilioChannelMapper INSTANCE = new TwilioChannelMapper();

  @Override
  public CommunicationChannel fromIaasEntity(IncomingPhoneNumber phoneNumber) {
    if (phoneNumber == null) {
      return null;
    }

    String capabilities = buildCapabilities();

    return new CommunicationChannel(phoneNumber.getSid(), phoneNumber.getPhoneNumber().toString(),
        ChannelType.SMS, phoneNumber.getFriendlyName(), capabilities,
        phoneNumber.getDateCreated() != null ? phoneNumber.getDateCreated().toLocalDateTime()
            : null,
        phoneNumber.getDateUpdated() != null ? phoneNumber.getDateUpdated().toLocalDateTime()
            : null);
  }

  private String buildCapabilities() {
    // TODO: Map Twilio phone number capabilities to JSON string
    return "{}";
  }
}
