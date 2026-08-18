package io.trishul.communication.model.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;

import io.trishul.communication.model.channel.ChannelType;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class MessageTest {
  @Test
  void testAccessId() throws Exception {
    Message accessor = new Message();
    assertSame(accessor, accessor.setId("testString"));
    assertEquals("testString", accessor.getId());
  }

  @Test
  void testAccessFrom() throws Exception {
    Message accessor = new Message();
    assertSame(accessor, accessor.setFrom("testString"));
    assertEquals("testString", accessor.getFrom());
  }

  @Test
  void testAccessTo() throws Exception {
    Message accessor = new Message();
    assertSame(accessor, accessor.setTo("testString"));
    assertEquals("testString", accessor.getTo());
  }

  @Test
  void testAccessBody() throws Exception {
    Message accessor = new Message();
    assertSame(accessor, accessor.setBody("testString"));
    assertEquals("testString", accessor.getBody());
  }

  @Test
  void testAccessChannelType() throws Exception {
    Message accessor = new Message();
    ChannelType value = mock(ChannelType.class);
    assertSame(accessor, accessor.setChannelType(value));
    assertEquals(value, accessor.getChannelType());
  }

  @Test
  void testAccessStatus() throws Exception {
    Message accessor = new Message();
    MessageStatus value = mock(MessageStatus.class);
    assertSame(accessor, accessor.setStatus(value));
    assertEquals(value, accessor.getStatus());
  }

  @Test
  void testAccessDirection() throws Exception {
    Message accessor = new Message();
    MessageDirection value = mock(MessageDirection.class);
    assertSame(accessor, accessor.setDirection(value));
    assertEquals(value, accessor.getDirection());
  }

  @Test
  void testAccessMediaUrls() throws Exception {
    Message accessor = new Message();
    List<String> value = List.of("url1", "url2");
    assertSame(accessor, accessor.setMediaUrls(value));
    assertEquals(value, accessor.getMediaUrls());
  }

  @Test
  void testAccessErrorCode() throws Exception {
    Message accessor = new Message();
    assertSame(accessor, accessor.setErrorCode("testString"));
    assertEquals("testString", accessor.getErrorCode());
  }

  @Test
  void testAccessErrorMessage() throws Exception {
    Message accessor = new Message();
    assertSame(accessor, accessor.setErrorMessage("testString"));
    assertEquals("testString", accessor.getErrorMessage());
  }

  @Test
  void testAccessPrice() throws Exception {
    Message accessor = new Message();
    assertSame(accessor, accessor.setPrice("testString"));
    assertEquals("testString", accessor.getPrice());
  }

  @Test
  void testAccessPriceUnit() throws Exception {
    Message accessor = new Message();
    assertSame(accessor, accessor.setPriceUnit("testString"));
    assertEquals("testString", accessor.getPriceUnit());
  }

  @Test
  void testAccessCreatedAt() throws Exception {
    Message accessor = new Message();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setCreatedAt(value));
    assertEquals(value, accessor.getCreatedAt());
  }

  @Test
  void testAccessLastUpdated() throws Exception {
    Message accessor = new Message();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setLastUpdated(value));
    assertEquals(value, accessor.getLastUpdated());
  }

  @Test
  void testAccessVersion() throws Exception {
    Message accessor = new Message();
    assertSame(accessor, accessor.setVersion(123));
    assertNull(accessor.getVersion());
  }
}
