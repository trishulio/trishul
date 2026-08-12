package sh.trishul.iaas.idp.tenant.model;

import sh.trishul.model.base.pojo.BaseModel;

public class TenantIaasAuthDeleteResult extends BaseModel {
  private long roles;

  public TenantIaasAuthDeleteResult(long roles) {
    setRoles(roles);
  }

  public long getRoles() {
    return roles;
  }

  public TenantIaasAuthDeleteResult setRoles(long roles) {
    this.roles = roles;
    return this;
  }
}
