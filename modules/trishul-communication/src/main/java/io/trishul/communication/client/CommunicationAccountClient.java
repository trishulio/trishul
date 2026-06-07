package io.trishul.communication.client;

import io.trishul.communication.model.account.BaseCommunicationAccount;
import io.trishul.communication.model.account.CommunicationAccount;
import io.trishul.communication.model.account.UpdateCommunicationAccount;
import io.trishul.iaas.client.IaasClient;

public interface CommunicationAccountClient extends
    IaasClient<String, CommunicationAccount, BaseCommunicationAccount<?>, UpdateCommunicationAccount<?>> {
}
