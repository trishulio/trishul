package io.trishul.iaas.tenant.service;

import io.trishul.iaas.idp.tenant.model.TenantIaasAuthDeleteResult;
import io.trishul.iaas.idp.tenant.model.TenantIaasIdpDeleteResult;
import io.trishul.iaas.tenant.object.store.TenantIaasVfsDeleteResult;
import io.trishul.model.base.pojo.BaseModel;

public class TenantIaasDeleteResult extends BaseModel {
  private TenantIaasAuthDeleteResult auth;
  private TenantIaasIdpDeleteResult idp;
  private TenantIaasVfsDeleteResult vfs;

  public TenantIaasDeleteResult() {}

  public TenantIaasDeleteResult(TenantIaasAuthDeleteResult auth, TenantIaasIdpDeleteResult idp,
      TenantIaasVfsDeleteResult vfs) {
    this();
    setAuth(auth);
    setIdp(idp);
    setVfs(vfs);
  }

  public TenantIaasAuthDeleteResult getAuth() {
    return auth;
  }

  public TenantIaasDeleteResult setAuth(TenantIaasAuthDeleteResult auth) {
    this.auth = auth;
    return this;
  }

  public TenantIaasIdpDeleteResult getIdp() {
    return idp;
  }

  public TenantIaasDeleteResult setIdp(TenantIaasIdpDeleteResult idp) {
    this.idp = idp;
    return this;
  }

  public TenantIaasVfsDeleteResult getVfs() {
    return vfs;
  }

  public TenantIaasDeleteResult setVfs(TenantIaasVfsDeleteResult vfs) {
    this.vfs = vfs;
    return this;
  }
}
