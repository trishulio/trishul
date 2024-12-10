package io.trishul.tenant.persistence.resolver;

import java.util.UUID;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

import io.trishul.tenant.auth.TenantIdProvider;
import io.trishul.tenant.entity.Tenant;

public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {
    private final TenantIdProvider tenantIdProvider;
    private final String defaultTenantId;

    public TenantIdentifierResolver(TenantIdProvider tenantIdProvider, Tenant adminTenant) {
        this.tenantIdProvider = tenantIdProvider;
        this.defaultTenantId = adminTenant.getId().toString();
    }

    @Override
    public String resolveCurrentTenantIdentifier() {
        String currentTenantId = this.defaultTenantId;

        UUID tenantId = this.tenantIdProvider.getTenantId();
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
