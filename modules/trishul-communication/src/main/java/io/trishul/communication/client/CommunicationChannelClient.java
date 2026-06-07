package io.trishul.communication.client;

import io.trishul.communication.model.channel.BaseCommunicationChannel;
import io.trishul.communication.model.channel.CommunicationChannel;
import io.trishul.communication.model.channel.UpdateCommunicationChannel;
import io.trishul.iaas.client.IaasClient;

public interface CommunicationChannelClient extends
    IaasClient<String, CommunicationChannel, BaseCommunicationChannel<?>, UpdateCommunicationChannel<?>> {
}
