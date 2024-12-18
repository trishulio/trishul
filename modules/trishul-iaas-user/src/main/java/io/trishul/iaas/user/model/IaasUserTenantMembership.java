package io.trishul.iaas.user.model;

import io.trishul.base.types.base.pojo.CrudEntity;
import io.trishul.model.base.entity.BaseEntity;

public class IaasUserTenantMembership extends BaseEntity
        implements CrudEntity<IaasUserTenantMembershipId>, UpdateIaasUserTenantMembership {
    private IaasUser user;
    private String tenantId;

    public IaasUserTenantMembership() {
        super();
    }

    public IaasUserTenantMembership(IaasUserTenantMembershipId id) {
        setId(id);
    }

    public IaasUserTenantMembership(IaasUser user, String tenantId) {
        this();
        setUser(user);
        setTenantId(tenantId);
    }

    @Override
    public IaasUserTenantMembershipId getId() {
        return IaasUserTenantMembershipId.build(this.user, this.tenantId);
    }

    @Override
    public final void setId(IaasUserTenantMembershipId id) {
        if (id != null) {
            if (user == null) {
                user = new IaasUser(id.getUserId());
            }
            this.tenantId = id.getTenantId();
        }
    }

    @Override
    public IaasUser getUser() {
        return user;
    }

    @Override
    public final void setUser(IaasUser user) {
        this.user = user;
    }

    @Override
    public String getTenantId() {
        return tenantId;
    }

    @Override
    public final void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public Integer getVersion() {
        // Not implemented due to lack of use-case
        return null;
    }
}
