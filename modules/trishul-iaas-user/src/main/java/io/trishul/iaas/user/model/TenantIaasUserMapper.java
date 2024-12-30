package io.trishul.iaas.user.model;

import io.trishul.user.model.BaseUser;
import java.util.List;

public class TenantIaasUserMapper {
  public static final TenantIaasUserMapper INSTANCE = new TenantIaasUserMapper();

  protected TenantIaasUserMapper() {}

  @SuppressWarnings("unchecked")
  public <IdpUser extends BaseIaasUser<IdpUser>, BU extends BaseUser<BU>> List<IdpUser> fromUsers(
      List<BU> users) {
    List<IdpUser> idpUsers = null;

    if (users != null) {
      idpUsers = users.stream().map(user -> (IdpUser) fromUser(user)).toList();
    }

    return idpUsers;
  }

  @SuppressWarnings("unchecked")
  public <IdpUser extends BaseIaasUser<IdpUser>, BU extends BaseUser<BU>> IdpUser fromUser(
      BU user) {
    IdpUser idpUser = null;

    if (user != null) {
      idpUser = (IdpUser) new IaasUser().setId(user.getUserName()).setEmail(user.getEmail())
          .setPhoneNumber(user.getPhoneNumber());
    }

    return idpUser;
  }
}
