package io.trishul.iaas.auth.aws.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import com.amazonaws.services.cognitoidentity.model.Credentials;
import com.amazonaws.services.cognitoidentity.model.IdentityPoolShortDescription;
import io.trishul.iaas.auth.session.context.IaasAuthorization;
import io.trishul.iaas.auth.session.context.IaasAuthorizationCredentials;
import io.trishul.iaas.auth.session.context.IaasAuthorizationFetcher;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AwsResourceCredentialsFetcherTest {
  private IaasAuthorizationFetcher fetcher;

  private AwsCognitoIdentityClient mIdentityClient;

  @BeforeEach
  public void init() {
    mIdentityClient = mock(AwsCognitoIdentityClient.class);

    fetcher = new AwsResourceCredentialsFetcher(mIdentityClient,
        AwsIdentityCredentialsMapper.INSTANCE, "USER_POOL");
  }

  @Test
  public void testFetch_ReturnsCredentials() {
    doReturn(List.of(new IdentityPoolShortDescription().withIdentityPoolId("POOL_ID")))
        .when(mIdentityClient).getIdentityPools(1);
    doReturn("IDENTITY_ID").when(mIdentityClient).getIdentityId("POOL_ID",
        Map.of("USER_POOL", "TOKEN"));
    doReturn(new Credentials().withAccessKeyId("AK").withSecretKey("SK").withSessionToken("ST"))
        .when(mIdentityClient)
        .getCredentialsForIdentity("IDENTITY_ID", Map.of("USER_POOL", "TOKEN"));

    IaasAuthorization auth = fetcher.fetch(new IaasAuthorizationCredentials("TOKEN"));

    IaasAuthorization expected = new IaasAuthorization("AK", "SK", "ST", null);
    assertEquals(expected, auth);
  }
}
