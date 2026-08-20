package sh.trishul.communication.service.channel;

import sh.trishul.communication.model.channel.CommunicationChannel;

public interface CommunicationChannelAccessor<T extends CommunicationChannelAccessor<T>> {
  String ATTR_COMMUNICATION_CHANNEL = "communicationChannel";

  CommunicationChannel getCommunicationChannel();

  T setCommunicationChannel(CommunicationChannel communicationChannel);
}
