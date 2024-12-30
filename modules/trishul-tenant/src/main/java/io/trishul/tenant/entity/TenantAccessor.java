package io.trishul.tenant.entity;

public interface TenantAccessor<T extends TenantAccessor<T>> {
  Tenant getTenant();

  T setTenant(Tenant tenant);
}
