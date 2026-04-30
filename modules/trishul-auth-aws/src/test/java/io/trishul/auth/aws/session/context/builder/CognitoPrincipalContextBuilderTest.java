package io.trishul.auth.aws.session.context.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import io.trishul.auth.aws.session.context.CognitoPrincipalContext;
import io.trishul.auth.session.context.PrincipalContext;
import java.util.Arrays;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;

class CognitoPrincipalContextBuilderTest {
  private CognitoPrincipalContextBuilder builder;

  @BeforeEach
  void init() {
    builder = new CognitoPrincipalContextBuilder();
  }

  @Test
  void testBuild_ReturnsNonNullPrincipalContext() {
    Jwt mJwt = mock(Jwt.class);
    doReturn(Arrays.asList("00000000-0000-0000-0000-000000000001")).when(mJwt)
        .getClaimAsStringList(CognitoPrincipalContext.CLAIM_GROUPS);
    doReturn("USERNAME_1").when(mJwt).getClaimAsString(CognitoPrincipalContext.CLAIM_USERNAME);
    doReturn("SCOPE_1 SCOPE_2").when(mJwt).getClaimAsString(CognitoPrincipalContext.CLAIM_SCOPE);

    PrincipalContext ctx = builder.build(mJwt);

    assertNotNull(ctx);
  }

  @Test
  void testBuild_ReturnsPrincipalContextWithCorrectUsername() {
    Jwt mJwt = mock(Jwt.class);
    doReturn(Arrays.asList("00000000-0000-0000-0000-000000000001")).when(mJwt)
        .getClaimAsStringList(CognitoPrincipalContext.CLAIM_GROUPS);
    doReturn("USERNAME_1").when(mJwt).getClaimAsString(CognitoPrincipalContext.CLAIM_USERNAME);
    doReturn("SCOPE_1 SCOPE_2").when(mJwt).getClaimAsString(CognitoPrincipalContext.CLAIM_SCOPE);

    PrincipalContext ctx = builder.build(mJwt);

    assertEquals("USERNAME_1", ctx.getUsername());
  }

  @Test
  void testBuild_ReturnsPrincipalContextWithCorrectRoles() {
    Jwt mJwt = mock(Jwt.class);
    doReturn(Arrays.asList("00000000-0000-0000-0000-000000000001")).when(mJwt)
        .getClaimAsStringList(CognitoPrincipalContext.CLAIM_GROUPS);
    doReturn("USERNAME_1").when(mJwt).getClaimAsString(CognitoPrincipalContext.CLAIM_USERNAME);
    doReturn("SCOPE_1 SCOPE_2").when(mJwt).getClaimAsString(CognitoPrincipalContext.CLAIM_SCOPE);

    PrincipalContext ctx = builder.build(mJwt);

    assertEquals(Arrays.asList("SCOPE_1", "SCOPE_2"), ctx.getRoles());
  }

  @Test
  void testBuild_ReturnsPrincipalContextWithCorrectTenantIds() {
    Jwt mJwt = mock(Jwt.class);
    doReturn(Arrays.asList("00000000-0000-0000-0000-000000000001")).when(mJwt)
        .getClaimAsStringList(CognitoPrincipalContext.CLAIM_GROUPS);
    doReturn("USERNAME_1").when(mJwt).getClaimAsString(CognitoPrincipalContext.CLAIM_USERNAME);
    doReturn("SCOPE_1 SCOPE_2").when(mJwt).getClaimAsString(CognitoPrincipalContext.CLAIM_SCOPE);

    PrincipalContext ctx = builder.build(mJwt);

    assertEquals(Arrays.asList(UUID.fromString("00000000-0000-0000-0000-000000000001")),
        ctx.getTenantIds());
  }
}
