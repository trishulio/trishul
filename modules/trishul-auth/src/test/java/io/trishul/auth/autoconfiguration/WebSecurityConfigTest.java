package io.trishul.auth.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer.AuthorizedUrl;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer.JwtConfigurer;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

class WebSecurityConfigTest {
  private WebSecurityConfig config;

  @BeforeEach
  void init() {
    config = new WebSecurityConfig();
  }

  @Test
  void testAccessAppUrls() {
    assertNull(config.getAppUrls());
    config.setAppUrls(List.of("http://test.com"));
    assertEquals(List.of("http://test.com"), config.getAppUrls());
  }

  @Test
  void testCorsConfigurationSource_WithUrls() {
    config.setAppUrls(List.of("http://test.com"));
    UrlBasedCorsConfigurationSource source
        = (UrlBasedCorsConfigurationSource) config.corsConfigurationSource();
    assertNotNull(source);
    CorsConfiguration corsConfig = source.getCorsConfigurations().get("/**");
    assertNotNull(corsConfig);
    assertEquals(List.of("http://test.com"), corsConfig.getAllowedOrigins());
    assertEquals(List.of("*"), corsConfig.getAllowedMethods());
    assertEquals(List.of("*"), corsConfig.getAllowedHeaders());
    assertEquals(true, corsConfig.getAllowCredentials());
  }

  @Test
  void testCorsConfigurationSource_WithoutUrls() {
    config.setAppUrls(null);
    UrlBasedCorsConfigurationSource source
        = (UrlBasedCorsConfigurationSource) config.corsConfigurationSource();
    assertNotNull(source);
    CorsConfiguration corsConfig = source.getCorsConfigurations().get("/**");
    assertNotNull(corsConfig);
    assertNull(corsConfig.getAllowedOrigins());
    assertEquals(List.of("*"), corsConfig.getAllowedMethods());
    assertEquals(List.of("*"), corsConfig.getAllowedHeaders());
    assertEquals(true, corsConfig.getAllowCredentials());
  }

  @Test
  void testCorsConfigurationSource_WithEmptyUrls() {
    config.setAppUrls(List.of());
    UrlBasedCorsConfigurationSource source
        = (UrlBasedCorsConfigurationSource) config.corsConfigurationSource();
    assertNotNull(source);
    CorsConfiguration corsConfig = source.getCorsConfigurations().get("/**");
    assertNotNull(corsConfig);
    assertNull(corsConfig.getAllowedOrigins());
    assertEquals(List.of("*"), corsConfig.getAllowedMethods());
    assertEquals(List.of("*"), corsConfig.getAllowedHeaders());
    assertEquals(true, corsConfig.getAllowCredentials());
  }

  @Test
  void testSecurityFilterChain_ReturnsNonNullInstance() throws Exception {
    HttpSecurity httpSecurity = mock(HttpSecurity.class);
    DefaultSecurityFilterChain filterChain = mock(DefaultSecurityFilterChain.class);

    // Mock for cors
    when(httpSecurity.cors(any(Customizer.class))).thenReturn(httpSecurity);

    // Mock for authorizeHttpRequests
    AuthorizationManagerRequestMatcherRegistry registry
        = mock(AuthorizationManagerRequestMatcherRegistry.class);
    AuthorizedUrl authorizedUrl = mock(AuthorizedUrl.class);

    when(registry.requestMatchers(any(String[].class))).thenReturn(authorizedUrl);
    when(authorizedUrl.permitAll()).thenReturn(registry);
    when(registry.anyRequest()).thenReturn(authorizedUrl);
    when(authorizedUrl.authenticated()).thenReturn(registry);

    when(httpSecurity.authorizeHttpRequests(any(Customizer.class))).thenAnswer(invocation -> {
      Customizer<AuthorizationManagerRequestMatcherRegistry> customizer = invocation.getArgument(0);
      customizer.customize(registry);
      return httpSecurity;
    });

    // Mock for oauth2ResourceServer
    OAuth2ResourceServerConfigurer<?> oauth2Configurer = mock(OAuth2ResourceServerConfigurer.class);
    when(httpSecurity.oauth2ResourceServer(any(Customizer.class))).thenAnswer(invocation -> {
      Customizer customizer = invocation.getArgument(0);
      customizer.customize(oauth2Configurer);
      return httpSecurity;
    });

    JwtConfigurer jwtConfigurer = mock(JwtConfigurer.class);
    when(oauth2Configurer.jwt(any(Customizer.class))).thenAnswer(invocation -> {
      Customizer customizer = invocation.getArgument(0);
      customizer.customize(jwtConfigurer);
      return oauth2Configurer;
    });

    when(httpSecurity.build()).thenReturn(filterChain);

    SecurityFilterChain result = config.securityFilterChain(httpSecurity);

    assertNotNull(result);
  }
}
