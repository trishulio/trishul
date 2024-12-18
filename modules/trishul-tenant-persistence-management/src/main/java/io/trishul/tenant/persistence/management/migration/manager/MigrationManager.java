package io.trishul.tenant.persistence.management.migration.manager;

import io.trishul.tenant.entity.Tenant;
import java.util.List;

public interface MigrationManager {
    void migrateAll(List<Tenant> tenants);

    void migrate(Tenant tenant);
}
