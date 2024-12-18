package io.trishul.iaas.idp.tenant.model;

import io.trishul.iaas.access.role.model.IaasRole;
import io.trishul.model.base.pojo.BaseModel;

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
        return role;
    }

    public final void setRole(IaasRole role) {
        this.role = role;
    }
}
