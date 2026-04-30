package io.trishul.auth.aws.session.context;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import io.trishul.auth.session.context.PrincipalContext;
import java.util.Arrays;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;

class CognitoPrincipalContextTest {
  private PrincipalContext ctx;
  private Jwt mJwt;

  @BeforeEach
  void init() {
    mJwt = mock(Jwt.class);
    doReturn(Arrays.asList("00000000-0000-0000-0000-000000000001")).when(mJwt)
        .getClaimAsStringList(CognitoPrincipalContext.CLAIM_GROUPS);
    doReturn("USERNAME_1").when(mJwt).getClaimAsString(CognitoPrincipalContext.CLAIM_USERNAME);
    doReturn("SCOPE_1 SCOPE_2").when(mJwt).getClaimAsString(CognitoPrincipalContext.CLAIM_SCOPE);

    ctx = CognitoPrincipalContext.fromJwt(mJwt);
  }

  @Test
  void testGetTenantIds_ReturnsAllGroupValuesInJwt() {
    mJwt = mock(Jwt.class);
    doReturn(Arrays.asList("00000000-0000-0000-0000-000000000001",
        "00000000-0000-0000-0000-000000000002")).when(mJwt)
        .getClaimAsStringList(CognitoPrincipalContext.CLAIM_GROUPS);
    doReturn("USERNAME_1").when(mJwt).getClaimAsString(CognitoPrincipalContext.CLAIM_USERNAME);
    doReturn("SCOPE_1 SCOPE_2").when(mJwt).getClaimAsString(CognitoPrincipalContext.CLAIM_SCOPE);

    ctx = CognitoPrincipalContext.fromJwt(mJwt);

    assertEquals(Arrays.asList(UUID.fromString("00000000-0000-0000-0000-000000000001"),
        UUID.fromString("00000000-0000-0000-0000-000000000002")), ctx.getTenantIds());
  }

  @Test
  void testGetUsername_ReturnsUserValue() {
    assertEquals("USERNAME_1", ctx.getUsername());
  }

  @Test
  void testGetRoles_ReturnsTheCognitoScopes() {
    assertEquals(Arrays.asList("SCOPE_1", "SCOPE_2"), ctx.getRoles());
  }

  @Test
  void testFromJwt_ThrowsIllegalArgumentException_WhenJwtIsNull() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      CognitoPrincipalContext.fromJwt(null);
    });
    assertEquals("Jwt cannot be null", exception.getMessage());
  }
}
