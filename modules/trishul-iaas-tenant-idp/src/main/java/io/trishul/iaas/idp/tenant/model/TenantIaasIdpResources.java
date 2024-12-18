package io.trishul.iaas.idp.tenant.model;

import io.trishul.model.base.pojo.BaseModel;

public class TenantIaasIdpResources extends BaseModel {
    private IaasIdpTenant idpTenant;

    public TenantIaasIdpResources() {
        super();
    }

    public TenantIaasIdpResources(IaasIdpTenant idpTenant) {
        this();
        setIaasIdpTenant(idpTenant);
    }

    public BaseIaasIdpTenant getIaasIdpTenant() {
        return idpTenant;
    }

    public final void setIaasIdpTenant(IaasIdpTenant idpTenant) {
        this.idpTenant = idpTenant;
    }
}
