package io.trishul.iaas.auth.session.context;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IaasAuthorizationDtoTest {
  private IaasAuthorizationDto dto;

  @BeforeEach
  public void init() {
    dto = new IaasAuthorizationDto();
  }

  @Test
  public void testNoArgConstructor_SetsNull() {
    assertNull(dto.getAccessKeyId());
    assertNull(dto.getAccessSecretKey());
    assertNull(dto.getExpiration());
    assertNull(dto.getSessionToken());
  }

  @Test
  public void testAllArgConstructor() {
    dto = new IaasAuthorizationDto("ACCESS_KEY_ID", "ACCESS_SECRET_KEY", "SESSION_TOKEN",
        LocalDateTime.of(2000, 1, 1, 0, 0));

    assertEquals("ACCESS_KEY_ID", dto.getAccessKeyId());
    assertEquals("ACCESS_SECRET_KEY", dto.getAccessSecretKey());
    assertEquals("SESSION_TOKEN", dto.getSessionToken());
    assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), dto.getExpiration());
  }

  @Test
  public void testGetSetAccessKeyId() {
    dto.setAccessKeyId("ACCESS_KEY_ID");
    assertEquals("ACCESS_KEY_ID", dto.getAccessKeyId());
  }

  @Test
  public void testGetSetAccessSecretKey() {
    dto.setAccessSecretKey("ACCESS_SECRET_KEY");
    assertEquals("ACCESS_SECRET_KEY", dto.getAccessSecretKey());
  }

  @Test
  public void testGetSetSessionToken() {
    dto.setSessionToken("SESSION_TOKEN");
    assertEquals("SESSION_TOKEN", dto.getSessionToken());
  }

  @Test
  public void testGetSetExpiration() {
    dto.setExpiration(LocalDateTime.of(2000, 1, 1, 0, 0));
    assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), dto.getExpiration());
  }
}
