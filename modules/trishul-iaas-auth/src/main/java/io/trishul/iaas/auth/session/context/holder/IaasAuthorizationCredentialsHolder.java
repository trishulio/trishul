package io.trishul.iaas.auth.session.context.holder;

import io.trishul.iaas.auth.session.context.IaasAuthorizationCredentials;

public interface IaasAuthorizationCredentialsHolder {
    IaasAuthorizationCredentials getIaasAuthorizationCredentials();
}
