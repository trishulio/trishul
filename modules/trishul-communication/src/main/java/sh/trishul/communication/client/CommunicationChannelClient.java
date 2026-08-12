package sh.trishul.communication.client;

import sh.trishul.communication.model.channel.BaseCommunicationChannel;
import sh.trishul.communication.model.channel.CommunicationChannel;
import sh.trishul.communication.model.channel.UpdateCommunicationChannel;
import sh.trishul.iaas.client.IaasClient;

public interface CommunicationChannelClient extends
    IaasClient<String, CommunicationChannel, BaseCommunicationChannel<?>, UpdateCommunicationChannel<?>> {
}
