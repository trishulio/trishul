package io.trishul.iaas.idp.tenant.resources;

import io.trishul.model.base.pojo.BaseModel;

public class TenantIaasIdpDeleteResult extends BaseModel {
    private long idpTenant;

    public TenantIaasIdpDeleteResult(long idpTenant) {
        this.idpTenant = idpTenant;
    }

    public long getIdpTenant() {
        return idpTenant;
    }

    public void setIdpTenant(long idpTenant) {
        this.idpTenant = idpTenant;
    }
}
