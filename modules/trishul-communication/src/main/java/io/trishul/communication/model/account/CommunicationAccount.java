package io.trishul.communication.model.account;

import io.trishul.base.types.base.pojo.Audited;
import io.trishul.base.types.base.pojo.CrudEntity;
import io.trishul.model.base.entity.BaseEntity;
import java.time.LocalDateTime;

public class CommunicationAccount extends BaseEntity
    implements CrudEntity<String, CommunicationAccount>,
    UpdateCommunicationAccount<CommunicationAccount>, Audited<CommunicationAccount> {
  private String sid;
  private String friendlyName;
  private CommunicationAccountStatus accountStatus;
  private String authToken;
  private LocalDateTime createdAt;
  private LocalDateTime lastUpdated;

  public CommunicationAccount() {
    super();
  }

  public CommunicationAccount(String id) {
    this();
    setId(id);
  }

  public CommunicationAccount(String sid, String friendlyName,
      CommunicationAccountStatus accountStatus, String authToken, LocalDateTime createdAt,
      LocalDateTime lastUpdated) {
    this(sid);
    setFriendlyName(friendlyName);
    setAccountStatus(accountStatus);
    setAuthToken(authToken);
    setCreatedAt(createdAt);
    setLastUpdated(lastUpdated);
  }

  @Override
  public String getId() {
    return sid;
  }

  @Override
  public CommunicationAccount setId(String id) {
    this.sid = id;
    return this;
  }

  @Override
  public String getFriendlyName() {
    return friendlyName;
  }

  @Override
  public CommunicationAccount setFriendlyName(String friendlyName) {
    this.friendlyName = friendlyName;
    return this;
  }

  @Override
  public CommunicationAccountStatus getAccountStatus() {
    return accountStatus;
  }

  @Override
  public CommunicationAccount setAccountStatus(CommunicationAccountStatus accountStatus) {
    this.accountStatus = accountStatus;
    return this;
  }

  @Override
  public String getAuthToken() {
    return authToken;
  }

  @Override
  public CommunicationAccount setAuthToken(String authToken) {
    this.authToken = authToken;
    return this;
  }

  @Override
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  @Override
  public CommunicationAccount setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  @Override
  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  @Override
  public CommunicationAccount setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }

  @Override
  public Integer getVersion() {
    return null;
  }

  public CommunicationAccount setVersion(Integer version) {
    return this;
  }
}
