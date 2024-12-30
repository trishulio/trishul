package io.trishul.iaas.user.model;

import io.trishul.base.types.base.pojo.Audited;
import io.trishul.base.types.base.pojo.CrudEntity;
import io.trishul.model.base.entity.BaseEntity;
import java.time.LocalDateTime;

public class IaasUser extends BaseEntity
    implements CrudEntity<String, IaasUser>, UpdateIaasUser<IaasUser>, Audited<IaasUser> {
  private String userName;
  private String email;
  private String phoneNumber; // We are not persisting it in the IDP
  private LocalDateTime createdAt;
  private LocalDateTime lastUpdated;

  public IaasUser() {
    super();
  }

  public IaasUser(String id) {
    this();
    setId(id);
  }

  public IaasUser(String userName, String email, String phoneNumber, LocalDateTime createdAt,
      LocalDateTime lastUpdated) {
    this(email);
    setUserName(userName);
    setPhoneNumber(phoneNumber);
    setCreatedAt(createdAt);
    setLastUpdated(lastUpdated);
  }

  @Override
  public String getId() {
    return getEmail();
  }

  @Override
  public final IaasUser setId(String id) {
    setEmail(id);
    return this;
  }

  @Override
  public String getUserName() {
    return userName;
  }

  @Override
  public final IaasUser setUserName(String userName) {
    this.userName = userName;
    return this;
  }

  @Override
  public String getEmail() {
    return email;
  }

  @Override
  public final IaasUser setEmail(String email) {
    this.email = email;
    return this;
  }

  @Override
  public String getPhoneNumber() {
    return phoneNumber;
  }

  @Override
  public final IaasUser setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  @Override
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  @Override
  public final IaasUser setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  @Override
  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  @Override
  public final IaasUser setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }

  @Override
  public Integer getVersion() {
    // Not implemented due to lack of use-case
    return null;
  }
}
