package io.trishul.communication.model.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class CommunicationAccountTest {
  @Test
  void testAccessId() throws Exception {
    CommunicationAccount accessor = new CommunicationAccount();
    assertSame(accessor, accessor.setId("testString"));
    assertEquals("testString", accessor.getId());
  }

  @Test
  void testAccessFriendlyName() throws Exception {
    CommunicationAccount accessor = new CommunicationAccount();
    assertSame(accessor, accessor.setFriendlyName("testString"));
    assertEquals("testString", accessor.getFriendlyName());
  }

  @Test
  void testAccessAccountStatus() throws Exception {
    CommunicationAccount accessor = new CommunicationAccount();
    CommunicationAccountStatus value = mock(CommunicationAccountStatus.class);
    assertSame(accessor, accessor.setAccountStatus(value));
    assertEquals(value, accessor.getAccountStatus());
  }

  @Test
  void testAccessAuthToken() throws Exception {
    CommunicationAccount accessor = new CommunicationAccount();
    assertSame(accessor, accessor.setAuthToken("testString"));
    assertEquals("testString", accessor.getAuthToken());
  }

  @Test
  void testAccessCreatedAt() throws Exception {
    CommunicationAccount accessor = new CommunicationAccount();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setCreatedAt(value));
    assertEquals(value, accessor.getCreatedAt());
  }

  @Test
  void testAccessLastUpdated() throws Exception {
    CommunicationAccount accessor = new CommunicationAccount();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setLastUpdated(value));
    assertEquals(value, accessor.getLastUpdated());
  }

  @Test
  void testAccessVersion() throws Exception {
    CommunicationAccount accessor = new CommunicationAccount();
    assertSame(accessor, accessor.setVersion(123));
    assertNull(accessor.getVersion());
  }
}
