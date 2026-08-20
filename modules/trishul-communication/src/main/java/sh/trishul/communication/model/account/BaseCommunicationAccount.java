package sh.trishul.communication.model.account;

public interface BaseCommunicationAccount<T extends BaseCommunicationAccount<T>> {
  String ATTR_FRIENDLY_NAME = "friendlyName";
  String ATTR_ACCOUNT_STATUS = "accountStatus";
  String ATTR_AUTH_TOKEN = "authToken";

  String getFriendlyName();

  T setFriendlyName(String friendlyName);

  CommunicationAccountStatus getAccountStatus();

  T setAccountStatus(CommunicationAccountStatus status);

  String getAuthToken();

  T setAuthToken(String authToken);
}
