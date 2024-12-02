package io.trishul.tenant.persistence.resolver;

import java.util.UUID;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

import io.trishul.auth.session.context.PrincipalContext;
import io.trishul.auth.session.context.holder.ContextHolder;
import io.trishul.tenant.entity.Tenant;

public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {
    private final ContextHolder contextHolder;
    private final String defaultTenantId;

    public TenantIdentifierResolver(ContextHolder contextHolder, Tenant adminTenant) {
        this.contextHolder = contextHolder;
        this.defaultTenantId = adminTenant.getId().toString();
    }

    @Override
    public String resolveCurrentTenantIdentifier() {
        String currentTenantId = this.defaultTenantId;

        UUID tenantId = null;

        PrincipalContext ctx = contextHolder.getPrincipalContext();
        if (ctx != null) {
            tenantId = ctx.getTenantId();
        }

        if (tenantId != null) {
            currentTenantId = tenantId.toString();
        }

        return currentTenantId;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
