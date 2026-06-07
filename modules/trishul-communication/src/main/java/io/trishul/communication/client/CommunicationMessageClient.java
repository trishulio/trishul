package io.trishul.communication.client;

import io.trishul.communication.model.message.BaseMessage;
import io.trishul.communication.model.message.Message;
import io.trishul.communication.model.message.UpdateMessage;
import io.trishul.iaas.client.IaasClient;

public interface CommunicationMessageClient
    extends IaasClient<String, Message, BaseMessage<?>, UpdateMessage<?>> {
}
