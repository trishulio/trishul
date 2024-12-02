package io.trishul.tenant.persistence.management.migration.manager;

import java.util.List;

import io.trishul.tenant.entity.Tenant;

public interface MigrationManager {
    void migrateAll(List<Tenant> tenants);

    void migrate(Tenant tenant);
}
