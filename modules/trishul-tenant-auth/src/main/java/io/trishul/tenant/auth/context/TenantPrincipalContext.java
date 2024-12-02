package io.trishul.tenant.auth.context;

import java.util.UUID;

import io.trishul.auth.session.context.PrincipalContext;
import io.trishul.model.base.pojo.BaseModel;
import io.trishul.tenant.model.TenantIdProvider;

public class TenantPrincipalContext extends BaseModel implements TenantIdProvider {
    private final PrincipalContext principalContext;

    public TenantPrincipalContext(PrincipalContext principalContext) {
        this.principalContext = principalContext;
    }

    @Override
    public UUID getTenantId() {
        return this.principalContext.getGroupId();
    }
}
