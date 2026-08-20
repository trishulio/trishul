package sh.trishul.tenant.entity;

import java.util.Collection;
import java.util.UUID;
import sh.trishul.base.types.base.pojo.Refresher;
import sh.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;

public class TenantRefresher implements Refresher<Tenant, TenantAccessor<?>> {
  private final AccessorRefresher<UUID, TenantAccessor<?>, Tenant> refresher;

  public TenantRefresher(AccessorRefresher<UUID, TenantAccessor<?>, Tenant> refresher) {
    this.refresher = refresher;
  }

  @Override
  public void refresh(Collection<Tenant> tenants) {
    // Nothing to refresh
  }

  @Override
  public void refreshAccessors(Collection<? extends TenantAccessor<?>> accessors) {
    this.refresher.refreshAccessors(accessors);
  }
}
