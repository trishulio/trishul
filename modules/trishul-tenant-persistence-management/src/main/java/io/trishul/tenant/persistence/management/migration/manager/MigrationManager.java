package io.trishul.tenant.persistence.management.migration.manager;

import io.trishul.tenant.entity.TenantData;

import java.util.List;

public interface MigrationManager {
  void migrateAll(List<? extends TenantData> tenants);

  void migrate(TenantData tenant);
}
