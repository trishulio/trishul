package io.trishul.iaas.auth.aws.client;

import com.amazonaws.services.cognitoidentity.model.Credentials;
import com.google.common.collect.ImmutableMap;
import io.trishul.iaas.auth.session.context.IaasAuthorization;
import io.trishul.iaas.auth.session.context.IaasAuthorizationCredentials;
import io.trishul.iaas.auth.session.context.IaasAuthorizationFetcher;
import java.util.Map;

public class AwsResourceCredentialsFetcher implements IaasAuthorizationFetcher {
  private static final int NUM_COGNITO_ID_POOLS = 1; // 1 because we only have 1 Cognito ID Pool per
                                                     // deployment.

  private final AwsCognitoIdentityClient identityClient;
  private final AwsIdentityCredentialsMapper iaasAuthorizationMapper;
  private final String userPoolUrl;

  public AwsResourceCredentialsFetcher(AwsCognitoIdentityClient identityClient,
      AwsIdentityCredentialsMapper iaasAuthorizationMapper, String userPoolUrl) {
    this.identityClient = identityClient;
    this.iaasAuthorizationMapper = iaasAuthorizationMapper;
    this.userPoolUrl = userPoolUrl;
  }

  @Override
  public IaasAuthorization fetch(IaasAuthorizationCredentials loginCredentials) {
    Map<String, String> logins = ImmutableMap.of(userPoolUrl, loginCredentials.toString());

    String identityPoolId
        = this.identityClient.getIdentityPools(NUM_COGNITO_ID_POOLS).get(0).getIdentityPoolId();
    String identityId = this.identityClient.getIdentityId(identityPoolId, logins);
    Credentials credentials = this.identityClient.getCredentialsForIdentity(identityId, logins);

    return iaasAuthorizationMapper.fromIaasEntity(credentials);
  }
}
