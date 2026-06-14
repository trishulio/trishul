package io.trishul.ai.chat.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class AiChatModelConfigTest {

  @Test
  void testGettersAndSetters() {
    LocalDateTime now = LocalDateTime.now();
    AiChatModelConfig config = new AiChatModelConfig(1L, "name", "openai", "gpt-4", "gpt-4-stream",
        "key", 0.7, 100, 0.9, true, now, now, 1);

    assertEquals(1L, config.getId());
    assertEquals("name", config.getName());
    assertEquals("openai", config.getProvider());
    assertEquals("gpt-4", config.getModelName());
    assertEquals("gpt-4-stream", config.getStreamingModelName());
    assertEquals("key", config.getApiKey());
    assertEquals(0.7, config.getTemperature());
    assertEquals(100, config.getMaxTokens());
    assertEquals(0.9, config.getTopP());
    assertTrue(config.getIsDefault());
    assertEquals(now, config.getCreatedAt());
    assertEquals(now, config.getLastUpdated());
    assertEquals(1, config.getVersion());

    config.setId(2L).setName("new-name").setProvider("anthropic").setModelName("claude")
        .setStreamingModelName("claude-stream").setApiKey("new-key").setTemperature(0.5)
        .setMaxTokens(50).setTopP(0.8).setIsDefault(false).setCreatedAt(now.plusDays(1))
        .setLastUpdated(now.plusDays(1)).setVersion(2);

    assertEquals(2L, config.getId());
    assertEquals("new-name", config.getName());
    assertEquals("anthropic", config.getProvider());
    assertEquals("claude", config.getModelName());
    assertEquals("claude-stream", config.getStreamingModelName());
    assertEquals("new-key", config.getApiKey());
    assertEquals(0.5, config.getTemperature());
    assertEquals(50, config.getMaxTokens());
    assertEquals(0.8, config.getTopP());
    assertTrue(!config.getIsDefault());
    assertEquals(now.plusDays(1), config.getCreatedAt());
    assertEquals(now.plusDays(1), config.getLastUpdated());
    assertEquals(2, config.getVersion());
  }
}
