package io.trishul.tenant.persistence.management.migration.register;

import io.trishul.tenant.entity.TenantData;

public interface MigrationRegister {
  void migrate(TenantData tenant);

  boolean isMigrated(TenantData tenant);
}
