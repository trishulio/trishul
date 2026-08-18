package sh.trishul.iaas.auth.session.context.holder;

import sh.trishul.iaas.auth.session.context.IaasAuthorizationCredentials;

public interface IaasAuthorizationCredentialsHolder {
  IaasAuthorizationCredentials getIaasAuthorizationCredentials();
}
