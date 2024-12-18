package io.trishul.iaas.tenant.idp.service.aws.cognito.client;

import com.amazonaws.services.cognitoidentity.model.Credentials;
import com.amazonaws.services.cognitoidentity.model.IdentityPoolShortDescription;
import java.util.List;
import java.util.Map;

public interface AwsCognitoIdentityClient {
    List<IdentityPoolShortDescription> getIdentityPools(int pageSize);

    String getIdentityId(String identityPoolId, Map<String, String> logins);

    Credentials getCredentialsForIdentity(String identityId, Map<String, String> logins);
}
