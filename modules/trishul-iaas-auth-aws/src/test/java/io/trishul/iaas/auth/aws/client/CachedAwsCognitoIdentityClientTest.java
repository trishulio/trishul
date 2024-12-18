package io.trishul.iaas.auth.aws.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.amazonaws.services.cognitoidentity.model.Credentials;
import com.amazonaws.services.cognitoidentity.model.IdentityPoolShortDescription;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CachedAwsCognitoIdentityClientTest {
    private AwsCognitoIdentityClient client;

    private AwsCognitoIdentityClient mDelegate;

    @BeforeEach
    public void init() {
        mDelegate = mock(AwsCognitoIdentityClient.class);

        client = new CachedAwsCognitoIdentityClient(mDelegate, 1);
    }

    @Test
    public void testGetIdentityPools_ReturnsCachedEntities() {
        List<IdentityPoolShortDescription> ret =
                List.of(
                        new IdentityPoolShortDescription()
                                .withIdentityPoolId("POOL_ID")
                                .withIdentityPoolName("POOL_NAME"));
        doReturn(ret).when(mDelegate).getIdentityPools(1);

        List<IdentityPoolShortDescription> pools = client.getIdentityPools(1);
        client.getIdentityPools(1);
        client.getIdentityPools(1);

        List<IdentityPoolShortDescription> expected =
                List.of(
                        new IdentityPoolShortDescription()
                                .withIdentityPoolId("POOL_ID")
                                .withIdentityPoolName("POOL_NAME"));
        assertEquals(expected, pools);
        verify(mDelegate, times(1)).getIdentityPools(anyInt());
    }

    @Test
    public void testGetIdentityPools_ThrowsException_WhenClientThrowsException() {
        doThrow(RuntimeException.class).when(mDelegate).getIdentityPools(1);

        assertThrows(RuntimeException.class, () -> client.getIdentityPools(1));
    }

    @Test
    public void testGetIdentityId_ReturnsCachedEntities() {
        doAnswer(
                        inv ->
                                inv.getArgument(0, String.class)
                                        + "_"
                                        + inv.getArgument(1, Map.class).get("K"))
                .when(mDelegate)
                .getIdentityId(anyString(), anyMap());

        String poolId = client.getIdentityId("POOL_ID", Map.of("K", "1"));
        client.getIdentityId("POOL_ID", Map.of("K", "1"));
        client.getIdentityId("POOL_ID", Map.of("K", "1"));

        assertEquals("POOL_ID_1", poolId);
        verify(mDelegate, times(1)).getIdentityId(anyString(), anyMap());
    }

    @Test
    public void testGetIdentityId_ThrowsException_WhenClientThrowsException() {
        doThrow(RuntimeException.class)
                .when(mDelegate)
                .getIdentityId("IDENTITY_POOL_ID", Map.of("login", "creds"));

        assertThrows(
                RuntimeException.class,
                () -> client.getIdentityId("IDENTITY_POOL_ID", Map.of("login", "creds")));
    }

    @Test
    public void testGetCredentialsForIdentity_ReturnsCachedEntities() {
        doReturn(
                        new Credentials()
                                .withAccessKeyId("ACCESS_KEY_ID")
                                .withSecretKey("ACCESS_SECRET_KEY")
                                .withSessionToken("SESSION_TOKEN"))
                .when(mDelegate)
                .getCredentialsForIdentity("POOL_ID", Map.of("K", "V"));

        Credentials creds = client.getCredentialsForIdentity("POOL_ID", Map.of("K", "V"));
        client.getCredentialsForIdentity("POOL_ID", Map.of("K", "V"));
        client.getCredentialsForIdentity("POOL_ID", Map.of("K", "V"));

        assertEquals(
                new Credentials()
                        .withAccessKeyId("ACCESS_KEY_ID")
                        .withSecretKey("ACCESS_SECRET_KEY")
                        .withSessionToken("SESSION_TOKEN"),
                creds);
        verify(mDelegate, times(1)).getCredentialsForIdentity(anyString(), anyMap());
    }

    @Test
    public void testGetCredentialsForIdentity_ThrowsException_WhenClientThrowsException() {
        doThrow(RuntimeException.class)
                .when(mDelegate)
                .getCredentialsForIdentity("IDENTITY_POOL_ID", Map.of("login", "creds"));

        assertThrows(
                RuntimeException.class,
                () ->
                        client.getCredentialsForIdentity(
                                "IDENTITY_POOL_ID", Map.of("login", "creds")));
    }
}
