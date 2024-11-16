package io.trishul.auth.session.context;

import io.trishul.auth.session.token.IaasAuthorizationCredentials;

public interface IaasAuthorizationFetcher {
    IaasAuthorization fetch(IaasAuthorizationCredentials loginCredentials);
}
