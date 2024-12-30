package io.trishul.iaas.auth.session.context;

public interface IaasAuthorizationFetcher {
  IaasAuthorization fetch(IaasAuthorizationCredentials loginCredentials);
}
