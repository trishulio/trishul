package io.trishul.iaas.tenant.idp.service.aws.cognito.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentity;
import com.amazonaws.services.cognitoidentity.model.Credentials;
import com.amazonaws.services.cognitoidentity.model.GetCredentialsForIdentityRequest;
import com.amazonaws.services.cognitoidentity.model.GetCredentialsForIdentityResult;
import com.amazonaws.services.cognitoidentity.model.GetIdRequest;
import com.amazonaws.services.cognitoidentity.model.GetIdResult;
import com.amazonaws.services.cognitoidentity.model.IdentityPoolShortDescription;
import com.amazonaws.services.cognitoidentity.model.ListIdentityPoolsRequest;
import com.amazonaws.services.cognitoidentity.model.ListIdentityPoolsResult;

public class AwsCognitoIdentitySdkWrapper implements AwsCognitoIdentityClient {
    private final AmazonCognitoIdentity awsCognitoIdentityClient;

    public AwsCognitoIdentitySdkWrapper(AmazonCognitoIdentity awsCognitoIdentityClient) {
        this.awsCognitoIdentityClient = awsCognitoIdentityClient;
    }

    @Override
    public List<IdentityPoolShortDescription> getIdentityPools(int pageSize) {
        List<IdentityPoolShortDescription> identityPools = new ArrayList<>();

        String nextToken = null;
        do {
            ListIdentityPoolsRequest request = new ListIdentityPoolsRequest()
                                               .withMaxResults(pageSize)
                                               .withNextToken(nextToken);
            ListIdentityPoolsResult result = this.awsCognitoIdentityClient.listIdentityPools(request);

            nextToken = result.getNextToken();

            for (IdentityPoolShortDescription pool: result.getIdentityPools()) {
                if (identityPools.size() >= pageSize) {
                    break;
                }
                identityPools.add(pool);
            }
        } while (nextToken != null && identityPools.size() < pageSize);

        return identityPools;
    }

    @Override
    public String getIdentityId(String identityPoolId, Map<String, String> logins) {
        GetIdRequest request = new GetIdRequest()
                                .withIdentityPoolId(identityPoolId)
                                .withLogins(logins);

        GetIdResult result = awsCognitoIdentityClient.getId(request);

        return result.getIdentityId();
    }

    @Override
    public Credentials getCredentialsForIdentity(String identityId, Map<String, String> logins) {
        GetCredentialsForIdentityRequest request = new GetCredentialsForIdentityRequest()
                                                    .withIdentityId(identityId)
                                                    .withLogins(logins);

        GetCredentialsForIdentityResult result = awsCognitoIdentityClient.getCredentialsForIdentity(request);

        return result.getCredentials();
    }
}
