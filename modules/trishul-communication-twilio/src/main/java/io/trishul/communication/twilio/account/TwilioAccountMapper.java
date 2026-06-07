package io.trishul.communication.twilio.account;

import com.twilio.rest.api.v2010.Account;
import io.trishul.communication.model.account.CommunicationAccount;
import io.trishul.communication.model.account.CommunicationAccountStatus;
import io.trishul.iaas.mapper.IaasEntityMapper;

public class TwilioAccountMapper implements IaasEntityMapper<Account, CommunicationAccount> {
  public static final TwilioAccountMapper INSTANCE = new TwilioAccountMapper();

  @Override
  public CommunicationAccount fromIaasEntity(Account twilioAccount) {
    if (twilioAccount == null) {
      return null;
    }

    CommunicationAccountStatus status = null;
    if (twilioAccount.getStatus() != null) {
      switch (twilioAccount.getStatus()) {
        case ACTIVE:
          status = CommunicationAccountStatus.ACTIVE;
          break;
        case SUSPENDED:
          status = CommunicationAccountStatus.SUSPENDED;
          break;
        case CLOSED:
          status = CommunicationAccountStatus.CLOSED;
          break;
        default:
          status = null;
      }
    }

    return new CommunicationAccount(twilioAccount.getSid(), twilioAccount.getFriendlyName(), status,
        twilioAccount.getAuthToken(),
        twilioAccount.getDateCreated() != null ? twilioAccount.getDateCreated().toLocalDateTime()
            : null,
        twilioAccount.getDateUpdated() != null ? twilioAccount.getDateUpdated().toLocalDateTime()
            : null);
  }
}
