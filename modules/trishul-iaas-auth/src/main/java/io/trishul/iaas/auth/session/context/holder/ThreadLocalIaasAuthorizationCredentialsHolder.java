package io.trishul.iaas.auth.session.context.holder;

import io.trishul.iaas.auth.session.context.IaasAuthorizationCredentials;

public class ThreadLocalIaasAuthorizationCredentialsHolder
        implements IaasAuthorizationCredentialsHolder {
    private final InheritableThreadLocal<IaasAuthorizationCredentials>
            iaasAuthorizationCredentialsContainer;

    public ThreadLocalIaasAuthorizationCredentialsHolder() {
        this.iaasAuthorizationCredentialsContainer = new InheritableThreadLocal<>();
    }

    @Override
    public IaasAuthorizationCredentials getIaasAuthorizationCredentials() {
        return this.iaasAuthorizationCredentialsContainer.get();
    }

    public void setIaasAuthorizationCredentials(
            IaasAuthorizationCredentials iaasAuthorizationCredentials) {
        this.iaasAuthorizationCredentialsContainer.set(iaasAuthorizationCredentials);
    }
}
