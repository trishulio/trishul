package io.trishul.iaas.auth.session.context;

import io.trishul.iaas.auth.session.context.holder.IaasAuthorizationCredentialsHolder;

public class ContextHolderAuthorizationFetcher {
    private final IaasAuthorizationFetcher fetcher;
    private final IaasAuthorizationCredentialsHolder credentialsHolder;

    public ContextHolderAuthorizationFetcher(IaasAuthorizationFetcher fetcher, IaasAuthorizationCredentialsHolder credentialsHolder) {
        this.fetcher = fetcher;
        this.credentialsHolder = credentialsHolder;
    }

    public IaasAuthorization fetch() {
        return this.fetcher.fetch(this.credentialsHolder.getIaasAuthorizationCredentials());
    }
}
