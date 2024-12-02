package io.trishul.tenant.persistence.management.migration.register;

import io.trishul.tenant.entity.Tenant;

public class UnifiedTenantRegister implements TenantRegister {
    private final TenantRegister userReg;
    private final TenantRegister schemaReg;

    public UnifiedTenantRegister(TenantUserRegister userReg, TenantSchemaRegister schemaReg) {
        this.userReg = userReg;
        this.schemaReg = schemaReg;
    }

    @Override
    public void add(Tenant tenant) {
        userReg.add(tenant);
        schemaReg.add(tenant);
    }

    @Override
    public void put(Tenant tenant) {
        userReg.put(tenant);
        schemaReg.put(tenant);
    }

    @Override
    public void remove(Tenant tenant) {
        schemaReg.remove(tenant);
        userReg.remove(tenant);
    }

    @Override
    public boolean exists(Tenant tenant) {
        return userReg.exists(tenant) && schemaReg.exists(tenant);
    }
}
