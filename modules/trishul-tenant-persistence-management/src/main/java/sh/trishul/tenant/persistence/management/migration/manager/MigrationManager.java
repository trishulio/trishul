package sh.trishul.tenant.persistence.management.migration.manager;

import java.util.List;
import sh.trishul.tenant.entity.TenantData;

public interface MigrationManager {
  void migrateAll(List<? extends TenantData> tenants);

  void migrate(TenantData tenant);
}
