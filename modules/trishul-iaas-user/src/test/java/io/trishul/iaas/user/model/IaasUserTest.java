package io.trishul.iaas.user.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IaasUserTest {
  private IaasUser iaasUser;

  @BeforeEach
  void init() {
    iaasUser = new IaasUser();
  }

  @Test
  void testNoArgConstructor() {
    assertNull(iaasUser.getId());
    assertNull(iaasUser.getUserName());
    assertNull(iaasUser.getEmail());
    assertNull(iaasUser.getPhoneNumber());
    assertNull(iaasUser.getLastUpdated());
    assertNull(iaasUser.getCreatedAt());
    assertNull(iaasUser.getVersion());
  }

  @Test
  void testAllArgConstructor() {
    iaasUser = new IaasUser("USERNAME", "EMAIL", "PHONE_NUMBER", LocalDateTime.of(2000, 1, 1, 0, 0),
        LocalDateTime.of(2001, 1, 1, 0, 0));

    assertEquals("EMAIL", iaasUser.getId());
    assertEquals("USERNAME", iaasUser.getUserName());
    assertEquals("EMAIL", iaasUser.getEmail());
    assertEquals("PHONE_NUMBER", iaasUser.getPhoneNumber());
    assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), iaasUser.getCreatedAt());
    assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), iaasUser.getLastUpdated());
  }

  @Test
  void testGetSetId() {
    iaasUser.setId("ID");
    assertEquals("ID", iaasUser.getId());
  }

  @Test
  void testGetSetUserName() {
    iaasUser.setUserName("USERNAME");
    assertEquals("USERNAME", iaasUser.getUserName());
  }

  @Test
  void testGetSetEmail() {
    iaasUser.setEmail("EMAIL");
    assertEquals("EMAIL", iaasUser.getEmail());
  }

  @Test
  void testGetSetPhoneNumber() {
    iaasUser.setPhoneNumber("PHONE_NUMBER");
    assertEquals("PHONE_NUMBER", iaasUser.getPhoneNumber());
  }

  @Test
  void testGetSetLastUpdated() {
    iaasUser.setLastUpdated(LocalDateTime.of(2000, 1, 1, 0, 0));
    assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), iaasUser.getLastUpdated());
  }

  @Test
  void testGetSetCreatedAt() {
    iaasUser.setCreatedAt(LocalDateTime.of(2000, 1, 1, 0, 0));
    assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), iaasUser.getCreatedAt());
  }
}
