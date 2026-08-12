package sh.trishul.communication.client;

import sh.trishul.communication.model.message.BaseMessage;
import sh.trishul.communication.model.message.Message;
import sh.trishul.communication.model.message.UpdateMessage;
import sh.trishul.iaas.client.IaasClient;

public interface CommunicationMessageClient
    extends IaasClient<String, Message, BaseMessage<?>, UpdateMessage<?>> {
}
