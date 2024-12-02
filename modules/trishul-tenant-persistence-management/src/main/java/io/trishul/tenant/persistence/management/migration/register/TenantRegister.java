package io.trishul.tenant.persistence.management.migration.register;

import io.trishul.tenant.entity.Tenant;

public interface TenantRegister {
    void add(Tenant tenant);

    void put(Tenant tenant);

    void remove(Tenant tenant);

    boolean exists(Tenant tenant);
}
