package io.trishul.communication.service.channel;

import io.trishul.communication.model.channel.CommunicationChannel;

public interface CommunicationChannelAccessor<T extends CommunicationChannelAccessor<T>> {
  final String ATTR_COMMUNICATION_CHANNEL = "communicationChannel";

  CommunicationChannel getCommunicationChannel();

  T setCommunicationChannel(CommunicationChannel communicationChannel);
}
