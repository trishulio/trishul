package sh.trishul.auth.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.sun.net.httpserver.HttpServer;
import jakarta.servlet.Filter;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import sh.trishul.auth.session.context.PrincipalContextBuilder;
import sh.trishul.auth.session.context.holder.ContextHolder;
import sh.trishul.auth.session.context.holder.ThreadLocalContextHolder;
import sh.trishul.auth.session.filters.ContextHolderFilter;

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
  void testLoggingMdcFilter_ReturnsNonNullInstance() {
    ThreadLocalContextHolder contextHolder = new ThreadLocalContextHolder();
    assertNotNull(config.loggingMdcFilter(contextHolder));
  }

  @Test
  void testJwtDecoder_ReturnsNonNullInstance() {
    OAuth2ResourceServerProperties props = mock(OAuth2ResourceServerProperties.class);
    OAuth2ResourceServerProperties.Jwt jwt = mock(OAuth2ResourceServerProperties.Jwt.class);
    when(props.getJwt()).thenReturn(jwt);
    when(jwt.getJwkSetUri()).thenReturn("https://example.com/.well-known/jwks.json");

    assertNotNull(config.jwtDecoder(props));
  }

  @Test
  void testJwtDecoder_DecodesTokenWithoutNoSuchMethodError() throws Exception {
    HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
    server.createContext("/jwks.json", exchange -> {
      byte[] response = "{\"keys\":[]}".getBytes(StandardCharsets.UTF_8);
      exchange.getResponseHeaders().set("Content-Type", "application/json");
      exchange.sendResponseHeaders(200, response.length);
      exchange.getResponseBody().write(response);
      exchange.close();
    });
    server.start();
    try {
      int port = server.getAddress().getPort();
      OAuth2ResourceServerProperties props = mock(OAuth2ResourceServerProperties.class);
      OAuth2ResourceServerProperties.Jwt jwt = mock(OAuth2ResourceServerProperties.Jwt.class);
      when(props.getJwt()).thenReturn(jwt);
      when(jwt.getJwkSetUri()).thenReturn("http://localhost:" + port + "/jwks.json");

      JwtDecoder decoder = config.jwtDecoder(props);
      String header = Base64.getUrlEncoder().withoutPadding()
          .encodeToString("{\"alg\":\"RS256\",\"kid\":\"key-1\"}".getBytes(StandardCharsets.UTF_8));
      String payload = Base64.getUrlEncoder().withoutPadding().encodeToString(
          "{\"sub\":\"test-user\",\"exp\":253402300799}".getBytes(StandardCharsets.UTF_8));
      String signature = Base64.getUrlEncoder().withoutPadding()
          .encodeToString("dummy-sig".getBytes(StandardCharsets.UTF_8));
      String token = header + "." + payload + "." + signature;

      assertThrows(JwtException.class, () -> decoder.decode(token));
    } finally {
      server.stop(0);
    }
  }
}
