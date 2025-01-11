package io.trishul.tenant.auth.model;

import java.util.UUID;

import io.trishul.auth.session.context.PrincipalContext;
import io.trishul.auth.session.context.holder.ContextHolder;
import io.trishul.tenant.entity.AdminTenant;
import io.trishul.tenant.entity.TenantIdProvider;

public class ContextHolderTenantIdProvider implements TenantIdProvider {
  private final ContextHolder contextHolder;
  private final UUID defaultTenantId;

  public ContextHolderTenantIdProvider(ContextHolder contextHolder, AdminTenant adminTenant) {
    this.contextHolder = contextHolder;
    this.defaultTenantId = adminTenant.getId();
  }

  @Override
  public UUID getTenantId() {
    UUID currentTenantId = this.defaultTenantId;

    PrincipalContext ctx = this.contextHolder.getPrincipalContext();

    UUID tenantId = null;
    if (ctx != null) {
      tenantId = ctx.getGroupId();
    }

    if (tenantId != null) {
      currentTenantId = tenantId;
    }

    return currentTenantId;
  }
}
