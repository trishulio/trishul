package io.trishul.auth.session.context;

import io.trishul.auth.session.context.holder.ContextHolder;

// TODO: Rename to something better: Tenant is not in the context - authorization is.
public class TenantContextIaasAuthorizationFetcher {
    private final IaasAuthorizationFetcher fetcher;
    private final ContextHolder contextHolder;

    public TenantContextIaasAuthorizationFetcher(IaasAuthorizationFetcher fetcher, ContextHolder contextHolder) {
        this.fetcher = fetcher;
        this.contextHolder = contextHolder;
    }

    public IaasAuthorization fetch() {
        PrincipalContext ctx;
        ctx = this.contextHolder.getPrincipalContext();

        return this.fetcher.fetch(ctx.getIaasLogin());
    }
}
