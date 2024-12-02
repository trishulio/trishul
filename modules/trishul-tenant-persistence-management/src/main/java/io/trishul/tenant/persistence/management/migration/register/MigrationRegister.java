package io.trishul.tenant.persistence.management.migration.register;

import io.trishul.tenant.entity.Tenant;

public interface MigrationRegister {
    void migrate(Tenant tenant);

    boolean isMigrated(Tenant tenant);
}
