package io.trishul.communication.twilio.account;

import com.twilio.rest.api.v2010.Account;
import com.twilio.rest.api.v2010.AccountUpdater;
import io.trishul.communication.model.account.BaseCommunicationAccount;
import io.trishul.communication.model.account.CommunicationAccount;
import io.trishul.communication.model.account.UpdateCommunicationAccount;
import io.trishul.iaas.client.IaasClient;
import io.trishul.iaas.mapper.IaasEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwilioAccountClient implements
    IaasClient<String, CommunicationAccount, BaseCommunicationAccount<?>, UpdateCommunicationAccount<?>> {
  private static final Logger log = LoggerFactory.getLogger(TwilioAccountClient.class);

  private final IaasEntityMapper<Account, CommunicationAccount> mapper;

  public TwilioAccountClient(IaasEntityMapper<Account, CommunicationAccount> mapper) {
    this.mapper = mapper;
  }

  @Override
  public CommunicationAccount get(String accountSid) {
    try {
      Account twilioAccount = Account.fetcher(accountSid).fetch();
      return mapper.fromIaasEntity(twilioAccount);
    } catch (Exception e) {
      log.error("Failed to fetch Twilio account: {}", accountSid, e);
      return null;
    }
  }

  @Override
  public <BE extends BaseCommunicationAccount<?>> CommunicationAccount add(BE entity) {
    try {
      Account twilioAccount = Account.creator().setFriendlyName(entity.getFriendlyName()).create();
      return mapper.fromIaasEntity(twilioAccount);
    } catch (Exception e) {
      log.error("Failed to create Twilio sub-account", e);
      throw new RuntimeException("Failed to create Twilio sub-account", e);
    }
  }

  @Override
  public <UE extends UpdateCommunicationAccount<?>> CommunicationAccount put(UE entity) {
    if (entity.getId() != null && exists(entity.getId())) {
      try {
        AccountUpdater updater = Account.updater(entity.getId());
        if (entity.getFriendlyName() != null) {
          updater.setFriendlyName(entity.getFriendlyName());
        }
        if (entity.getAccountStatus() != null) {
          switch (entity.getAccountStatus()) {
            case ACTIVE:
              updater.setStatus(Account.Status.ACTIVE);
              break;
            case SUSPENDED:
              updater.setStatus(Account.Status.SUSPENDED);
              break;
            case CLOSED:
              updater.setStatus(Account.Status.CLOSED);
              break;
          }
        }
        Account twilioAccount = updater.update();
        return mapper.fromIaasEntity(twilioAccount);
      } catch (Exception e) {
        log.error("Failed to update Twilio account: {}", entity.getId(), e);
        throw new RuntimeException("Failed to update Twilio account", e);
      }
    }
    return add(entity);
  }

  @Override
  public boolean delete(String accountSid) {
    try {
      // In Twilio, sub-accounts are deleted by closing them.
      Account twilioAccount = Account.updater(accountSid).setStatus(Account.Status.CLOSED).update();
      return twilioAccount.getStatus() == Account.Status.CLOSED;
    } catch (Exception e) {
      log.error("Failed to close/delete Twilio account: {}", accountSid, e);
      return false;
    }
  }

  @Override
  public boolean exists(String accountSid) {
    return get(accountSid) != null;
  }
}
