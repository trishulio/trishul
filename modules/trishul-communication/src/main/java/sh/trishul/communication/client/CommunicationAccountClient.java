package sh.trishul.communication.client;

import sh.trishul.communication.model.account.BaseCommunicationAccount;
import sh.trishul.communication.model.account.CommunicationAccount;
import sh.trishul.communication.model.account.UpdateCommunicationAccount;
import sh.trishul.iaas.client.IaasClient;

public interface CommunicationAccountClient extends
    IaasClient<String, CommunicationAccount, BaseCommunicationAccount<?>, UpdateCommunicationAccount<?>> {
}
