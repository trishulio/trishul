package io.trishul.iaas.user.model;

import io.trishul.model.base.entity.BaseEntity;

public class IaasUserTenantMembershipId extends BaseEntity {
  private String userId;
  private String tenantId;

  public IaasUserTenantMembershipId() {
    super();
  }

  public IaasUserTenantMembershipId(String userId, String tenantId) {
    this();
    setUserId(userId);
    setTenantId(tenantId);
  }

  public String getUserId() {
    return userId;
  }

  public final IaasUserTenantMembershipId setUserId(String userId) {
    this.userId = userId;
    return this;
  }

  public String getTenantId() {
    return tenantId;
  }

  public final IaasUserTenantMembershipId setTenantId(String tenantId) {
    this.tenantId = tenantId;
    return this;
  }

  public static IaasUserTenantMembershipId build(IaasUser user, String tenantId) {
    IaasUserTenantMembershipId id = null;

    if (user != null || tenantId != null) {
      id = new IaasUserTenantMembershipId();
      if (user != null) {
        id.setUserId(user.getId());
      }

      if (tenantId != null) {
        id.setTenantId(tenantId);
      }
    }

    return id;
  }
}
