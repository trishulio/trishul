package sh.trishul.tenant.persistence.management.migration.register;

import sh.trishul.tenant.entity.TenantData;

public interface TenantRegister {
  void add(TenantData tenant);

  void put(TenantData tenant);

  void remove(TenantData tenant);

  boolean exists(TenantData tenant);
}
