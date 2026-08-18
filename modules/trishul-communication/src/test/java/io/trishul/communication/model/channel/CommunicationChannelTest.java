package io.trishul.communication.model.channel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class CommunicationChannelTest {
  @Test
  void testAccessId() throws Exception {
    CommunicationChannel accessor = new CommunicationChannel();
    assertSame(accessor, accessor.setId("testString"));
    assertEquals("testString", accessor.getId());
  }

  @Test
  void testAccessAddress() throws Exception {
    CommunicationChannel accessor = new CommunicationChannel();
    assertSame(accessor, accessor.setAddress("testString"));
    assertEquals("testString", accessor.getAddress());
  }

  @Test
  void testAccessChannelType() throws Exception {
    CommunicationChannel accessor = new CommunicationChannel();
    ChannelType value = mock(ChannelType.class);
    assertSame(accessor, accessor.setChannelType(value));
    assertEquals(value, accessor.getChannelType());
  }

  @Test
  void testAccessDisplayName() throws Exception {
    CommunicationChannel accessor = new CommunicationChannel();
    assertSame(accessor, accessor.setDisplayName("testString"));
    assertEquals("testString", accessor.getDisplayName());
  }

  @Test
  void testAccessCapabilities() throws Exception {
    CommunicationChannel accessor = new CommunicationChannel();
    assertSame(accessor, accessor.setCapabilities("testString"));
    assertEquals("testString", accessor.getCapabilities());
  }

  @Test
  void testAccessCreatedAt() throws Exception {
    CommunicationChannel accessor = new CommunicationChannel();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setCreatedAt(value));
    assertEquals(value, accessor.getCreatedAt());
  }

  @Test
  void testAccessLastUpdated() throws Exception {
    CommunicationChannel accessor = new CommunicationChannel();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setLastUpdated(value));
    assertEquals(value, accessor.getLastUpdated());
  }

  @Test
  void testAccessVersion() throws Exception {
    CommunicationChannel accessor = new CommunicationChannel();
    assertSame(accessor, accessor.setVersion(123));
    assertNull(accessor.getVersion());
  }
}
