package io.trishul.iaas.auth.aws.client;

import com.amazonaws.services.cognitoidentity.model.Credentials;
import com.google.common.collect.ImmutableMap;
import io.trishul.iaas.auth.session.context.IaasAuthorization;
import io.trishul.iaas.auth.session.context.IaasAuthorizationCredentials;
import io.trishul.iaas.auth.session.context.IaasAuthorizationFetcher;
import java.util.Map;

public class AwsResourceCredentialsFetcher implements IaasAuthorizationFetcher {
  private final AwsCognitoIdentityClient identityClient;
  private final AwsIdentityCredentialsMapper iaasAuthorizationMapper;
  private final String userPoolUrl;
  private final String identityPoolId;

  public AwsResourceCredentialsFetcher(AwsCognitoIdentityClient identityClient,
      AwsIdentityCredentialsMapper iaasAuthorizationMapper, String userPoolUrl,
      String identityPoolId) {
    this.identityClient = identityClient;
    this.iaasAuthorizationMapper = iaasAuthorizationMapper;
    this.userPoolUrl = userPoolUrl;
    this.identityPoolId = identityPoolId;
  }

  @Override
  public IaasAuthorization fetch(IaasAuthorizationCredentials loginCredentials) {
    Map<String, String> logins = ImmutableMap.of(userPoolUrl, loginCredentials.toString());
    String identityId = this.identityClient.getIdentityId(this.identityPoolId, logins);
    Credentials credentials = this.identityClient.getCredentialsForIdentity(identityId, logins);

    return iaasAuthorizationMapper.fromIaasEntity(credentials);
  }
}
