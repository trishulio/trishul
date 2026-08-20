package sh.trishul.communication.service.account;

import sh.trishul.communication.model.account.CommunicationAccount;

public interface CommunicationAccountAccessor<T extends CommunicationAccountAccessor<T>> {
  String ATTR_COMMUNICATION_ACCOUNT = "communicationAccount";

  CommunicationAccount getCommunicationAccount();

  T setCommunicationAccount(CommunicationAccount communicationAccount);
}
