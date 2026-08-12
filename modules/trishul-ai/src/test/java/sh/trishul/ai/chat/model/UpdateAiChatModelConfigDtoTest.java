package sh.trishul.ai.chat.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class UpdateAiChatModelConfigDtoTest {

  @Test
  void testGettersAndSetters() {
    UpdateAiChatModelConfigDto dto = new UpdateAiChatModelConfigDto(1L, "name", "provider", "model",
        "stream", "key", 0.7, 100, 0.9, true, 1);

    assertEquals(1L, dto.getId());
    assertEquals("name", dto.getName());
    assertEquals("provider", dto.getProvider());
    assertEquals("model", dto.getModelName());
    assertEquals("stream", dto.getStreamingModelName());
    assertEquals("key", dto.getApiKey());
    assertEquals(0.7, dto.getTemperature());
    assertEquals(100, dto.getMaxTokens());
    assertEquals(0.9, dto.getTopP());
    assertTrue(dto.getIsDefault());
    assertEquals(1, dto.getVersion());

    dto.setId(2L).setName("new-name").setProvider("new-provider").setModelName("new-model")
        .setStreamingModelName("new-stream").setApiKey("new-key").setTemperature(0.5)
        .setMaxTokens(50).setTopP(0.8).setIsDefault(false).setVersion(2);

    assertEquals(2L, dto.getId());
    assertEquals("new-name", dto.getName());
    assertEquals("new-provider", dto.getProvider());
    assertEquals("new-model", dto.getModelName());
    assertEquals("new-stream", dto.getStreamingModelName());
    assertEquals("new-key", dto.getApiKey());
    assertEquals(0.5, dto.getTemperature());
    assertEquals(50, dto.getMaxTokens());
    assertEquals(0.8, dto.getTopP());
    assertTrue(!dto.getIsDefault());
    assertEquals(2, dto.getVersion());
  }

  @Test
  void testAccessId() throws Exception {
    UpdateAiChatModelConfigDto accessor = new UpdateAiChatModelConfigDto();
    assertSame(accessor, accessor.setId(123L));
    assertEquals(123L, accessor.getId());
  }

  @Test
  void testAccessName() throws Exception {
    UpdateAiChatModelConfigDto accessor = new UpdateAiChatModelConfigDto();
    assertSame(accessor, accessor.setName("testString"));
    assertEquals("testString", accessor.getName());
  }

  @Test
  void testAccessProvider() throws Exception {
    UpdateAiChatModelConfigDto accessor = new UpdateAiChatModelConfigDto();
    assertSame(accessor, accessor.setProvider("testString"));
    assertEquals("testString", accessor.getProvider());
  }

  @Test
  void testAccessModelName() throws Exception {
    UpdateAiChatModelConfigDto accessor = new UpdateAiChatModelConfigDto();
    assertSame(accessor, accessor.setModelName("testString"));
    assertEquals("testString", accessor.getModelName());
  }

  @Test
  void testAccessStreamingModelName() throws Exception {
    UpdateAiChatModelConfigDto accessor = new UpdateAiChatModelConfigDto();
    assertSame(accessor, accessor.setStreamingModelName("testString"));
    assertEquals("testString", accessor.getStreamingModelName());
  }

  @Test
  void testAccessApiKey() throws Exception {
    UpdateAiChatModelConfigDto accessor = new UpdateAiChatModelConfigDto();
    assertSame(accessor, accessor.setApiKey("testString"));
    assertEquals("testString", accessor.getApiKey());
  }

  @Test
  void testAccessTemperature() throws Exception {
    UpdateAiChatModelConfigDto accessor = new UpdateAiChatModelConfigDto();
    assertSame(accessor, accessor.setTemperature(123.45));
    assertEquals(123.45, accessor.getTemperature());
  }

  @Test
  void testAccessMaxTokens() throws Exception {
    UpdateAiChatModelConfigDto accessor = new UpdateAiChatModelConfigDto();
    assertSame(accessor, accessor.setMaxTokens(123));
    assertEquals(123, accessor.getMaxTokens());
  }

  @Test
  void testAccessTopP() throws Exception {
    UpdateAiChatModelConfigDto accessor = new UpdateAiChatModelConfigDto();
    assertSame(accessor, accessor.setTopP(123.45));
    assertEquals(123.45, accessor.getTopP());
  }

  @Test
  void testAccessIsDefault() throws Exception {
    UpdateAiChatModelConfigDto accessor = new UpdateAiChatModelConfigDto();
    assertSame(accessor, accessor.setIsDefault(true));
    assertEquals(true, accessor.getIsDefault());
  }

  @Test
  void testAccessVersion() throws Exception {
    UpdateAiChatModelConfigDto accessor = new UpdateAiChatModelConfigDto();
    assertSame(accessor, accessor.setVersion(123));
    assertEquals(123, accessor.getVersion());
  }

}
