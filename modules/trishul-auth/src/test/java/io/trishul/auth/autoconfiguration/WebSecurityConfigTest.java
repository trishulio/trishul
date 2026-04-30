package io.trishul.auth.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

class WebSecurityConfigTest {
  private WebSecurityConfig config;

  @BeforeEach
  void init() {
    config = new WebSecurityConfig();
  }

  @Test
  @SuppressWarnings("unchecked")
  void testSecurityFilterChain_ReturnsNonNullInstance() throws Exception {
    HttpSecurity httpSecurity = mock(HttpSecurity.class);
    AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry =
        mock(AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry.class);
    OAuth2ResourceServerConfigurer<HttpSecurity> oauth2Configurer =
        mock(OAuth2ResourceServerConfigurer.class);
    DefaultSecurityFilterChain filterChain = mock(DefaultSecurityFilterChain.class);

    when(httpSecurity.authorizeHttpRequests(any())).thenReturn(httpSecurity);
    when(httpSecurity.oauth2ResourceServer(any())).thenReturn(httpSecurity);
    when(httpSecurity.build()).thenReturn(filterChain);

    SecurityFilterChain result = config.securityFilterChain(httpSecurity);

    assertNotNull(result);
  }
}
