package io.trishul.auth.aws.session.context;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;

import io.trishul.auth.session.context.PrincipalContext;
import io.trishul.auth.aws.session.context.CognitoPrincipalContext;

public class CognitoPrincipalContextTest {
    static final String IAAS_TOKEN = "IAAS_TOKEN";

    private PrincipalContext ctx;
    private Jwt mJwt;

    @BeforeEach
    public void init() {
        mJwt = mock(Jwt.class);
        doReturn(Arrays.asList("00000000-0000-0000-0000-000000000001")).when(mJwt).getClaimAsStringList(CognitoPrincipalContext.CLAIM_GROUPS);
        doReturn("USERNAME_1").when(mJwt).getClaimAsString(CognitoPrincipalContext.CLAIM_USERNAME);
        doReturn("SCOPE_1 SCOPE_2").when(mJwt).getClaimAsString(CognitoPrincipalContext.CLAIM_SCOPE);

        ctx = new CognitoPrincipalContext(mJwt);
    }

    @Test
    public void testConstructor_ThrowsError_WhenMultipleGroupValuesArePresent() {
        mJwt = mock(Jwt.class);
        doReturn(Arrays.asList("00000000-0000-0000-0000-000000000001", "00000000-0000-0000-0000-000000000002")).when(mJwt).getClaimAsStringList(CognitoPrincipalContext.CLAIM_GROUPS);
        doReturn("USERNAME_1").when(mJwt).getClaimAsString(CognitoPrincipalContext.CLAIM_USERNAME);
        doReturn("SCOPE_1 SCOPE_2").when(mJwt).getClaimAsString(CognitoPrincipalContext.CLAIM_SCOPE);

        assertThrows(IllegalArgumentException.class, () -> new CognitoPrincipalContext(mJwt), "ach user should only belong to a single cognito group. Instead found 2");
    }

    @Test
    public void testGetGroupId_ReturnsTheFirstGroupValueInJwt_WhenSingleGroupIsPresent() {
        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000001"), ctx.getGroupId());
    }

    @Test
    public void testGetUsername_ReturnsUserValue() {
        assertEquals("USERNAME_1", ctx.getUsername());
    }

    @Test
    public void testGetRoles_ReturnsTheCognitoScopes() {
        assertEquals(Arrays.asList("SCOPE_1", "SCOPE_2"), ctx.getRoles());
    }

    // TODO: Move this test to where this code was moved to
    // @Test
    // public void testGetIaasLogin_ReturnsIaasToken() {
    //     assertEquals(new IaasAuthorizationCredentials(IAAS_TOKEN), ctx.getIaasLogin());
    // }
}
