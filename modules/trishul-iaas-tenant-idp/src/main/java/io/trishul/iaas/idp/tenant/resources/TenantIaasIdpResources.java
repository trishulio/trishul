package io.trishul.iaas.idp.tenant.resources;

import io.trishul.iaas.idp.tenant.model.BaseIaasIdpTenant;
import io.trishul.iaas.idp.tenant.model.IaasIdpTenant;
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

    public void setIaasIdpTenant(IaasIdpTenant idpTenant) {
        this.idpTenant = idpTenant;
    }
}
