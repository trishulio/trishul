package sh.trishul.tenant.persistence.management.migration.register;

import sh.trishul.tenant.entity.TenantData;

public interface MigrationRegister {
  void migrate(TenantData tenant);
}
