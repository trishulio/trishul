package io.trishul.tenant.auth.model;

import io.trishul.auth.session.context.PrincipalContext;
import io.trishul.auth.session.context.holder.ContextHolder;
import io.trishul.tenant.entity.AdminTenant;
import io.trishul.tenant.entity.TenantIdProvider;
import java.util.UUID;

public class ContextHolderTenantIdProvider implements TenantIdProvider {
    private final ContextHolder ctxHolder;
    private final UUID defaultTenantId;

    public ContextHolderTenantIdProvider(ContextHolder ctxHolder, AdminTenant adminTenant) {
        this.ctxHolder = ctxHolder;
        this.defaultTenantId = adminTenant.getId();
    }

    @Override
    public UUID getTenantId() {
        UUID currentTenantId = this.defaultTenantId;

        PrincipalContext ctx = this.ctxHolder.getPrincipalContext();
        UUID tenantId = ctx.getGroupId();

        if (tenantId != null) {
            currentTenantId = tenantId;
        }

        return currentTenantId;
    }
}
