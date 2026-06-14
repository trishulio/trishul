package io.trishul.ai.chat.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class AddAiChatModelConfigDtoTest {

  @Test
  void testGettersAndSetters() {
    AddAiChatModelConfigDto dto = new AddAiChatModelConfigDto("name", "provider", "model", "stream",
        "key", 0.7, 100, 0.9, true);

    assertEquals("name", dto.getName());
    assertEquals("provider", dto.getProvider());
    assertEquals("model", dto.getModelName());
    assertEquals("stream", dto.getStreamingModelName());
    assertEquals("key", dto.getApiKey());
    assertEquals(0.7, dto.getTemperature());
    assertEquals(100, dto.getMaxTokens());
    assertEquals(0.9, dto.getTopP());
    assertTrue(dto.getIsDefault());

    dto.setName("new-name").setProvider("new-provider").setModelName("new-model")
        .setStreamingModelName("new-stream").setApiKey("new-key").setTemperature(0.5)
        .setMaxTokens(50).setTopP(0.8).setIsDefault(false);

    assertEquals("new-name", dto.getName());
    assertEquals("new-provider", dto.getProvider());
    assertEquals("new-model", dto.getModelName());
    assertEquals("new-stream", dto.getStreamingModelName());
    assertEquals("new-key", dto.getApiKey());
    assertEquals(0.5, dto.getTemperature());
    assertEquals(50, dto.getMaxTokens());
    assertEquals(0.8, dto.getTopP());
    assertTrue(!dto.getIsDefault());
  }
}
