package io.trishul.iaas.tenant.resource;

import io.trishul.iaas.idp.tenant.model.TenantIaasAuthResources;
import io.trishul.iaas.idp.tenant.model.TenantIaasIdpResources;
import io.trishul.iaas.tenant.object.store.TenantIaasVfsResources;
import io.trishul.model.base.pojo.BaseModel;

public class TenantIaasResources extends BaseModel {
  private TenantIaasAuthResources authResources;
  private TenantIaasIdpResources idpResources;
  private TenantIaasVfsResources vfsResources;

  public TenantIaasResources() {}

  public TenantIaasResources(TenantIaasAuthResources authResources,
      TenantIaasIdpResources idpResources, TenantIaasVfsResources vfsResources) {
    this();
    setAuthResources(authResources);
    setIdpResources(idpResources);
    setVfsResources(vfsResources);
  }

  public TenantIaasAuthResources getAuthResources() {
    return authResources;
  }

  public TenantIaasResources setAuthResources(TenantIaasAuthResources authResources) {
    this.authResources = authResources;
    return this;
  }

  public TenantIaasIdpResources getIdpResources() {
    return idpResources;
  }

  public TenantIaasResources setIdpResources(TenantIaasIdpResources idpResources) {
    this.idpResources = idpResources;
    return this;
  }

  public TenantIaasVfsResources getVfsResources() {
    return vfsResources;
  }

  public TenantIaasResources setVfsResources(TenantIaasVfsResources vfsResources) {
    this.vfsResources = vfsResources;
    return this;
  }
}
