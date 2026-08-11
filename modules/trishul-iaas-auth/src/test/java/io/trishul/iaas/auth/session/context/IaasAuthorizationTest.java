package io.trishul.iaas.auth.session.context;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IaasAuthorizationTest {
  private IaasAuthorization authorization;

  @BeforeEach
  void init() {
    authorization = new IaasAuthorization();
  }

  @Test
  void testNoArgConstructor_SetsNull() {
    assertNull(authorization.getAccessKeyId());
    assertNull(authorization.getAccessSecretKey());
    assertNull(authorization.getExpiration());
    assertNull(authorization.getSessionToken());
  }

  @Test
  void testAllArgConstructor() {
    authorization = new IaasAuthorization("ACCESS_KEY_ID", "ACCESS_SECRET_KEY", "SESSION_TOKEN",
        LocalDateTime.of(2000, 1, 1, 0, 0));

    assertEquals("ACCESS_KEY_ID", authorization.getAccessKeyId());
    assertEquals("ACCESS_SECRET_KEY", authorization.getAccessSecretKey());
    assertEquals("SESSION_TOKEN", authorization.getSessionToken());
    assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), authorization.getExpiration());
  }

  @Test
  void testGetSetAccessKeyId() {
    assertSame(authorization, authorization.setAccessKeyId("ACCESS_KEY_ID"));
    assertEquals("ACCESS_KEY_ID", authorization.getAccessKeyId());
    assertEquals("ACCESS_KEY_ID", authorization.getId());
  }

  @Test
  void testGetSetAccessSecretKey() {
    assertSame(authorization, authorization.setAccessSecretKey("ACCESS_SECRET_KEY"));
    assertEquals("ACCESS_SECRET_KEY", authorization.getAccessSecretKey());
  }

  @Test
  void testGetSetSessionToken() {
    assertSame(authorization, authorization.setSessionToken("SESSION_TOKEN"));
    assertEquals("SESSION_TOKEN", authorization.getSessionToken());
  }

  @Test
  void testGetSetExpiration() {
    assertSame(authorization, authorization.setExpiration(LocalDateTime.of(2000, 1, 1, 0, 0)));
    assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), authorization.getExpiration());
  }
}
