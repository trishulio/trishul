package io.trishul.tenant.auth.model;

import java.util.UUID;

import io.trishul.auth.session.context.PrincipalContext;
import io.trishul.auth.session.context.holder.ContextHolder;
import io.trishul.tenant.auth.TenantIdProvider;

public class ContextHolderTenantIdProvider implements TenantIdProvider {
    private final ContextHolder ctxHolder;

    public ContextHolderTenantIdProvider(ContextHolder ctxHolder) {
        this.ctxHolder = ctxHolder;
    }

    @Override
    public UUID getTenantId() {
        PrincipalContext ctx = this.ctxHolder.getPrincipalContext();

        return ctx.getGroupId();
    }
}
