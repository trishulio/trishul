package io.trishul.auth.aws.client;

import java.util.List;
import java.util.Map;

import com.amazonaws.services.cognitoidentity.model.Credentials;
import com.amazonaws.services.cognitoidentity.model.IdentityPoolShortDescription;

public interface AwsCognitoIdentityClient {
    List<IdentityPoolShortDescription> getIdentityPools(int pageSize);

    String getIdentityId(String identityPoolId, Map<String, String> logins);

    Credentials getCredentialsForIdentity(String identityId, Map<String, String> logins);
}
