package io.trishul.iaas.idp.tenant.model;

import io.trishul.model.base.pojo.BaseModel;

public class TenantIaasAuthDeleteResult extends BaseModel {
    private long roles;

    public TenantIaasAuthDeleteResult(long roles) {
        setRoles(roles);
    }

    public long getRoles() {
        return roles;
    }

    public final void setRoles(long roles) {
        this.roles = roles;
    }
}
