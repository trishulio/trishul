package sh.trishul.ai.chat.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class AiChatModelConfigDtoTest {

  @Test
  void testGettersAndSetters() {
    LocalDateTime now = LocalDateTime.now();
    AiChatModelConfigDto dto = new AiChatModelConfigDto(1L, "name", "provider", "model", "stream",
        0.7, 100, 0.9, true, now, now, 1);

    assertEquals(1L, dto.getId());
    assertEquals("name", dto.getName());
    assertEquals("provider", dto.getProvider());
    assertEquals("model", dto.getModelName());
    assertEquals("stream", dto.getStreamingModelName());
    assertEquals(0.7, dto.getTemperature());
    assertEquals(100, dto.getMaxTokens());
    assertEquals(0.9, dto.getTopP());
    assertTrue(dto.getIsDefault());
    assertEquals(now, dto.getCreatedAt());
    assertEquals(now, dto.getLastUpdated());
    assertEquals(1, dto.getVersion());

    dto.setId(2L).setName("new-name").setProvider("new-provider").setModelName("new-model")
        .setStreamingModelName("new-stream").setTemperature(0.5).setMaxTokens(50).setTopP(0.8)
        .setIsDefault(false).setCreatedAt(now.plusDays(1)).setLastUpdated(now.plusDays(1))
        .setVersion(2);

    assertEquals(2L, dto.getId());
    assertEquals("new-name", dto.getName());
    assertEquals("new-provider", dto.getProvider());
    assertEquals("new-model", dto.getModelName());
    assertEquals("new-stream", dto.getStreamingModelName());
    assertEquals(0.5, dto.getTemperature());
    assertEquals(50, dto.getMaxTokens());
    assertEquals(0.8, dto.getTopP());
    assertTrue(!dto.getIsDefault());
    assertEquals(now.plusDays(1), dto.getCreatedAt());
    assertEquals(now.plusDays(1), dto.getLastUpdated());
    assertEquals(2, dto.getVersion());
  }

  @Test
  void testAccessId() throws Exception {
    AiChatModelConfigDto accessor = new AiChatModelConfigDto();
    assertSame(accessor, accessor.setId(123L));
    assertEquals(123L, accessor.getId());
  }

  @Test
  void testAccessName() throws Exception {
    AiChatModelConfigDto accessor = new AiChatModelConfigDto();
    assertSame(accessor, accessor.setName("testString"));
    assertEquals("testString", accessor.getName());
  }

  @Test
  void testAccessProvider() throws Exception {
    AiChatModelConfigDto accessor = new AiChatModelConfigDto();
    assertSame(accessor, accessor.setProvider("testString"));
    assertEquals("testString", accessor.getProvider());
  }

  @Test
  void testAccessModelName() throws Exception {
    AiChatModelConfigDto accessor = new AiChatModelConfigDto();
    assertSame(accessor, accessor.setModelName("testString"));
    assertEquals("testString", accessor.getModelName());
  }

  @Test
  void testAccessStreamingModelName() throws Exception {
    AiChatModelConfigDto accessor = new AiChatModelConfigDto();
    assertSame(accessor, accessor.setStreamingModelName("testString"));
    assertEquals("testString", accessor.getStreamingModelName());
  }

  @Test
  void testAccessTemperature() throws Exception {
    AiChatModelConfigDto accessor = new AiChatModelConfigDto();
    assertSame(accessor, accessor.setTemperature(123.45));
    assertEquals(123.45, accessor.getTemperature());
  }

  @Test
  void testAccessMaxTokens() throws Exception {
    AiChatModelConfigDto accessor = new AiChatModelConfigDto();
    assertSame(accessor, accessor.setMaxTokens(123));
    assertEquals(123, accessor.getMaxTokens());
  }

  @Test
  void testAccessTopP() throws Exception {
    AiChatModelConfigDto accessor = new AiChatModelConfigDto();
    assertSame(accessor, accessor.setTopP(123.45));
    assertEquals(123.45, accessor.getTopP());
  }

  @Test
  void testAccessIsDefault() throws Exception {
    AiChatModelConfigDto accessor = new AiChatModelConfigDto();
    assertSame(accessor, accessor.setIsDefault(true));
    assertEquals(true, accessor.getIsDefault());
  }

  @Test
  void testAccessCreatedAt() throws Exception {
    AiChatModelConfigDto accessor = new AiChatModelConfigDto();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setCreatedAt(value));
    assertEquals(value, accessor.getCreatedAt());
  }

  @Test
  void testAccessLastUpdated() throws Exception {
    AiChatModelConfigDto accessor = new AiChatModelConfigDto();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setLastUpdated(value));
    assertEquals(value, accessor.getLastUpdated());
  }

  @Test
  void testAccessVersion() throws Exception {
    AiChatModelConfigDto accessor = new AiChatModelConfigDto();
    assertSame(accessor, accessor.setVersion(123));
    assertEquals(123, accessor.getVersion());
  }

}
