package io.trishul.iaas.auth.session.context;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IaasAuthorizationDtoTest {
  private IaasAuthorizationDto dto;

  @BeforeEach
  void init() {
    dto = new IaasAuthorizationDto();
  }

  @Test
  void testNoArgConstructor_SetsNull() {
    assertNull(dto.getAccessKeyId());
    assertNull(dto.getAccessSecretKey());
    assertNull(dto.getExpiration());
    assertNull(dto.getSessionToken());
  }

  @Test
  void testAllArgConstructor() {
    dto = new IaasAuthorizationDto("ACCESS_KEY_ID", "ACCESS_SECRET_KEY", "SESSION_TOKEN",
        LocalDateTime.of(2000, 1, 1, 0, 0));

    assertEquals("ACCESS_KEY_ID", dto.getAccessKeyId());
    assertEquals("ACCESS_SECRET_KEY", dto.getAccessSecretKey());
    assertEquals("SESSION_TOKEN", dto.getSessionToken());
    assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), dto.getExpiration());
  }

  @Test
  void testGetSetAccessKeyId() {
    dto.setAccessKeyId("ACCESS_KEY_ID");
    assertEquals("ACCESS_KEY_ID", dto.getAccessKeyId());
  }

  @Test
  void testGetSetAccessSecretKey() {
    dto.setAccessSecretKey("ACCESS_SECRET_KEY");
    assertEquals("ACCESS_SECRET_KEY", dto.getAccessSecretKey());
  }

  @Test
  void testGetSetSessionToken() {
    dto.setSessionToken("SESSION_TOKEN");
    assertEquals("SESSION_TOKEN", dto.getSessionToken());
  }

  @Test
  void testGetSetExpiration() {
    dto.setExpiration(LocalDateTime.of(2000, 1, 1, 0, 0));
    assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), dto.getExpiration());
  }
}
