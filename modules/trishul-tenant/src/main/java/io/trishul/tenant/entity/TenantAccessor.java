package io.trishul.tenant.entity;

public interface TenantAccessor {
    Tenant getTenant();

    void setTenant(Tenant tenant);
}
