package io.trishul.iaas.tenant.idp.service.aws.cognito.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentity;
import com.amazonaws.services.cognitoidentity.model.Credentials;
import com.amazonaws.services.cognitoidentity.model.GetCredentialsForIdentityRequest;
import com.amazonaws.services.cognitoidentity.model.GetCredentialsForIdentityResult;
import com.amazonaws.services.cognitoidentity.model.GetIdRequest;
import com.amazonaws.services.cognitoidentity.model.GetIdResult;
import com.amazonaws.services.cognitoidentity.model.IdentityPoolShortDescription;
import com.amazonaws.services.cognitoidentity.model.ListIdentityPoolsRequest;
import com.amazonaws.services.cognitoidentity.model.ListIdentityPoolsResult;

public class AwsCognitoIdentitySdkWrapperTest {
    private AwsCognitoIdentityClient client;

    private AmazonCognitoIdentity mAws;

    @BeforeEach
    public void init() {
        mAws = mock(AmazonCognitoIdentity.class);

        client = new AwsCognitoIdentitySdkWrapper(mAws);
    }

    @Test
    public void testGetIdentityPools_ReturnsPoolsWithAllItems_WhenMaxItemValueIsMore() {
        List<IdentityPoolShortDescription> page1 = List.of(
            new IdentityPoolShortDescription().withIdentityPoolId("POOL_ID_1").withIdentityPoolName("POOL_NAME_1"),
            new IdentityPoolShortDescription().withIdentityPoolId("POOL_ID_2").withIdentityPoolName("POOL_NAME_2")
        );
        List<IdentityPoolShortDescription> page2 = List.of(
            new IdentityPoolShortDescription().withIdentityPoolId("POOL_ID_3").withIdentityPoolName("POOL_NAME_3"),
            new IdentityPoolShortDescription().withIdentityPoolId("POOL_ID_4").withIdentityPoolName("POOL_NAME_4")
        );
        List<IdentityPoolShortDescription> page3 = List.of(
            new IdentityPoolShortDescription().withIdentityPoolId("POOL_ID_5").withIdentityPoolName("POOL_NAME_5"),
            new IdentityPoolShortDescription().withIdentityPoolId("POOL_ID_6").withIdentityPoolName("POOL_NAME_6")
        );

        doReturn(new ListIdentityPoolsResult().withIdentityPools(page1).withNextToken("NEXT_1")).when(mAws).listIdentityPools(new ListIdentityPoolsRequest().withNextToken(null).withMaxResults(10));
        doReturn(new ListIdentityPoolsResult().withIdentityPools(page2).withNextToken("NEXT_2")).when(mAws).listIdentityPools(new ListIdentityPoolsRequest().withNextToken("NEXT_1").withMaxResults(10));
        doReturn(new ListIdentityPoolsResult().withIdentityPools(page3).withNextToken(null)).when(mAws).listIdentityPools(new ListIdentityPoolsRequest().withNextToken("NEXT_2").withMaxResults(10));

        List<IdentityPoolShortDescription> pools = client.getIdentityPools(10);

        List<IdentityPoolShortDescription> expected = List.of(
            new IdentityPoolShortDescription().withIdentityPoolId("POOL_ID_1").withIdentityPoolName("POOL_NAME_1"),
            new IdentityPoolShortDescription().withIdentityPoolId("POOL_ID_2").withIdentityPoolName("POOL_NAME_2"),
            new IdentityPoolShortDescription().withIdentityPoolId("POOL_ID_3").withIdentityPoolName("POOL_NAME_3"),
            new IdentityPoolShortDescription().withIdentityPoolId("POOL_ID_4").withIdentityPoolName("POOL_NAME_4"),
            new IdentityPoolShortDescription().withIdentityPoolId("POOL_ID_5").withIdentityPoolName("POOL_NAME_5"),
            new IdentityPoolShortDescription().withIdentityPoolId("POOL_ID_6").withIdentityPoolName("POOL_NAME_6")
        );
        assertEquals(expected, pools);
    }

    @Test
    public void testGetIdentityPools_ReturnsPoolsWithNItems_WhenMaxItemValueIsLess() {
        List<IdentityPoolShortDescription> page1 = List.of(
            new IdentityPoolShortDescription().withIdentityPoolId("POOL_ID_1").withIdentityPoolName("POOL_NAME_1"),
            new IdentityPoolShortDescription().withIdentityPoolId("POOL_ID_2").withIdentityPoolName("POOL_NAME_2")
        );
        List<IdentityPoolShortDescription> page2 = List.of(
            new IdentityPoolShortDescription().withIdentityPoolId("POOL_ID_3").withIdentityPoolName("POOL_NAME_3"),
            new IdentityPoolShortDescription().withIdentityPoolId("POOL_ID_4").withIdentityPoolName("POOL_NAME_4")
        );

        doReturn(new ListIdentityPoolsResult().withIdentityPools(page1).withNextToken("NEXT_1")).when(mAws).listIdentityPools(new ListIdentityPoolsRequest().withNextToken(null).withMaxResults(3));
        doReturn(new ListIdentityPoolsResult().withIdentityPools(page2).withNextToken("NEXT_2")).when(mAws).listIdentityPools(new ListIdentityPoolsRequest().withNextToken("NEXT_1").withMaxResults(3));

        List<IdentityPoolShortDescription> pools = client.getIdentityPools(3);

        List<IdentityPoolShortDescription> expected = List.of(
            new IdentityPoolShortDescription().withIdentityPoolId("POOL_ID_1").withIdentityPoolName("POOL_NAME_1"),
            new IdentityPoolShortDescription().withIdentityPoolId("POOL_ID_2").withIdentityPoolName("POOL_NAME_2"),
            new IdentityPoolShortDescription().withIdentityPoolId("POOL_ID_3").withIdentityPoolName("POOL_NAME_3")
        );
        assertEquals(expected, pools);
    }

    @Test
    public void testGetIdentityId_ReturnsIdentityId() {
        doReturn(new GetIdResult().withIdentityId("IDENTITY_ID")).when(mAws).getId(new GetIdRequest().withIdentityPoolId("POOL_ID").withLogins(Map.of("K", "V")));

        String identityId = client.getIdentityId("POOL_ID", Map.of("K", "V"));

        assertEquals("IDENTITY_ID", identityId);
    }

    @Test
    public void testGetCredentialsForIdentity_ReturnsCredentials() {
        Credentials creds = new Credentials()
                                .withAccessKeyId("ACCESS_KEY_ID")
                                .withSecretKey("SECRET_KEY")
                                .withSessionToken("SESSION_TOKEN")
                                .withExpiration(new Date(1, 1, 1));

        doReturn(new GetCredentialsForIdentityResult().withCredentials(creds)).when(mAws).getCredentialsForIdentity(new GetCredentialsForIdentityRequest().withIdentityId("IDENTITY_ID").withLogins(Map.of("K", "V")));

        Credentials credentials = client.getCredentialsForIdentity("IDENTITY_ID", Map.of("K", "V"));

        Credentials expected = new Credentials()
                .withAccessKeyId("ACCESS_KEY_ID")
                .withSecretKey("SECRET_KEY")
                .withSessionToken("SESSION_TOKEN")
                .withExpiration(new Date(1, 1, 1));

        assertEquals(expected, credentials);
    }
}
