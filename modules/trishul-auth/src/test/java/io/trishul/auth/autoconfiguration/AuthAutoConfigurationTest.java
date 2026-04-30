package io.trishul.auth.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import io.trishul.auth.session.context.PrincipalContextBuilder;
import io.trishul.auth.session.context.holder.ContextHolder;
import io.trishul.auth.session.context.holder.ThreadLocalContextHolder;
import io.trishul.auth.session.filters.ContextHolderFilter;
import jakarta.servlet.Filter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;

class AuthAutoConfigurationTest {
  private AuthAutoConfiguration config;

  @BeforeEach
  void init() {
    config = new AuthAutoConfiguration();
  }

  @Test
  void testcontextHolder_ReturnsInstanceOfTypeThreadLocalContextHolder() {
    ContextHolder holder = config.contextHolder();
    assertTrue(holder instanceof ThreadLocalContextHolder);
  }

  @Test
  void testContextHolder_ReturnsNonNullInstance() {
    ContextHolder holder = config.contextHolder();
    assertNotNull(holder);
  }

  @Test
  void testContextHolderFilter_ReturnsNonNullInstance() {
    ThreadLocalContextHolder contextHolder = new ThreadLocalContextHolder();
    PrincipalContextBuilder principalContextBuilder = mock(PrincipalContextBuilder.class);

    Filter filter = config.contextHolderFilter(contextHolder, principalContextBuilder);

    assertNotNull(filter);
  }

  @Test
  void testContextHolderFilter_ReturnsInstanceOfContextHolderFilter() {
    ThreadLocalContextHolder contextHolder = new ThreadLocalContextHolder();
    PrincipalContextBuilder principalContextBuilder = mock(PrincipalContextBuilder.class);

    Filter filter = config.contextHolderFilter(contextHolder, principalContextBuilder);

    assertTrue(filter instanceof ContextHolderFilter);
  }

  @Test
  void testJwtDecoder_ReturnsNonNullInstance() {
    OAuth2ResourceServerProperties props = mock(OAuth2ResourceServerProperties.class);
    OAuth2ResourceServerProperties.Jwt jwt = mock(OAuth2ResourceServerProperties.Jwt.class);
    when(props.getJwt()).thenReturn(jwt);
    when(jwt.getJwkSetUri()).thenReturn("https://example.com/.well-known/jwks.json");

    assertNotNull(config.jwtDecoder(props));
  }
}
