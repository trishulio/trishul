package io.trishul.communication.service.account;

import io.trishul.communication.model.account.CommunicationAccount;

public interface CommunicationAccountAccessor<T extends CommunicationAccountAccessor<T>> {
  final String ATTR_COMMUNICATION_ACCOUNT = "communicationAccount";

  CommunicationAccount getCommunicationAccount();

  T setCommunicationAccount(CommunicationAccount communicationAccount);
}
