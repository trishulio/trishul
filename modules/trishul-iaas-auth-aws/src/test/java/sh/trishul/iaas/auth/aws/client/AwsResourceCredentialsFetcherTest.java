package sh.trishul.iaas.auth.aws.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import com.amazonaws.services.cognitoidentity.model.Credentials;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.iaas.auth.session.context.IaasAuthorization;
import sh.trishul.iaas.auth.session.context.IaasAuthorizationCredentials;
import sh.trishul.iaas.auth.session.context.IaasAuthorizationFetcher;

class AwsResourceCredentialsFetcherTest {
  private IaasAuthorizationFetcher fetcher;

  private AwsCognitoIdentityClient mIdentityClient;

  @BeforeEach
  void init() {
    mIdentityClient = mock(AwsCognitoIdentityClient.class);

    fetcher = new AwsResourceCredentialsFetcher(mIdentityClient,
        AwsIdentityCredentialsMapper.INSTANCE, "USER_POOL", "CONFIGURED_POOL_ID");
  }

  @Test
  void testFetch_UsesConfiguredIdentityPoolId_ReturnsCredentials() {
    fetcher = new AwsResourceCredentialsFetcher(mIdentityClient,
        AwsIdentityCredentialsMapper.INSTANCE, "USER_POOL", "CONFIGURED_POOL_ID");

    doReturn("IDENTITY_ID").when(mIdentityClient).getIdentityId("CONFIGURED_POOL_ID",
        Map.of("USER_POOL", "TOKEN"));
    doReturn(new Credentials().withAccessKeyId("AK").withSecretKey("SK").withSessionToken("ST"))
        .when(mIdentityClient)
        .getCredentialsForIdentity("IDENTITY_ID", Map.of("USER_POOL", "TOKEN"));

    IaasAuthorization auth = fetcher.fetch(new IaasAuthorizationCredentials("TOKEN"));

    IaasAuthorization expected = new IaasAuthorization("AK", "SK", "ST", null);
    assertEquals(expected, auth);
  }
}
