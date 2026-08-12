package sh.trishul.iaas.idp.tenant.model;

import sh.trishul.iaas.access.role.model.IaasRole;
import sh.trishul.model.base.pojo.BaseModel;

public class TenantIaasAuthResources extends BaseModel {
  private IaasRole role;

  public TenantIaasAuthResources() {
    super();
  }

  public TenantIaasAuthResources(IaasRole role) {
    this();
    setRole(role);
  }

  public IaasRole getRole() {
    return role == null ? null : role.deepClone();
  }

  public TenantIaasAuthResources setRole(IaasRole role) {
    this.role = role == null ? null : role.deepClone();
    return this;
  }
}
