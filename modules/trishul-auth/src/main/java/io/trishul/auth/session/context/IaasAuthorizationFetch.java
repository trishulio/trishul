package io.trishul.auth.session.context;

import io.trishul.auth.session.token.IaasAuthorizationCredentials;

public interface IaasAuthorizationFetch {
    IaasAuthorization fetch(IaasAuthorizationCredentials loginCredentials);
}
