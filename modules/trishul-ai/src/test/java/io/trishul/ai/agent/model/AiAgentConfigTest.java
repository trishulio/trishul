package io.trishul.ai.agent.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import io.trishul.ai.chat.model.AiChatModelConfig;
import io.trishul.ai.memory.model.AiChatMemoryConfig;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class AiAgentConfigTest {

  @Test
  void testGettersAndSetters() {
    LocalDateTime now = LocalDateTime.now();
    AiChatModelConfig chatModel = new AiChatModelConfig(1L);
    AiChatMemoryConfig memoryConfig = new AiChatMemoryConfig(1L);

    AiAgentConfig config
        = new AiAgentConfig(1L, "name", "desc", true, chatModel, memoryConfig, now, now, 1);

    assertEquals(1L, config.getId());
    assertEquals("name", config.getName());
    assertEquals("desc", config.getDescription());
    assertTrue(config.getIsActive());
    assertEquals(chatModel, config.getChatModelConfig());
    assertEquals(memoryConfig, config.getChatMemoryConfig());
    assertEquals(now, config.getCreatedAt());
    assertEquals(now, config.getLastUpdated());
    assertEquals(1, config.getVersion());

    config.setId(2L).setName("new-name").setDescription("new-desc").setIsActive(false)
        .setChatModelConfig(new AiChatModelConfig(2L))
        .setChatMemoryConfig(new AiChatMemoryConfig(2L)).setCreatedAt(now.plusDays(1))
        .setLastUpdated(now.plusDays(1)).setVersion(2);

    assertEquals(2L, config.getId());
    assertEquals("new-name", config.getName());
    assertEquals("new-desc", config.getDescription());
    assertTrue(!config.getIsActive());
    assertEquals(2L, config.getChatModelConfig().getId());
    assertEquals(2L, config.getChatMemoryConfig().getId());
    assertEquals(now.plusDays(1), config.getCreatedAt());
    assertEquals(now.plusDays(1), config.getLastUpdated());
    assertEquals(2, config.getVersion());
  }

  @Test
  void testAccessId() throws Exception {
    AiAgentConfig accessor = new AiAgentConfig();
    assertSame(accessor, accessor.setId(123L));
    assertEquals(123L, accessor.getId());
  }

  @Test
  void testAccessName() throws Exception {
    AiAgentConfig accessor = new AiAgentConfig();
    assertSame(accessor, accessor.setName("testString"));
    assertEquals("testString", accessor.getName());
  }

  @Test
  void testAccessDescription() throws Exception {
    AiAgentConfig accessor = new AiAgentConfig();
    assertSame(accessor, accessor.setDescription("testString"));
    assertEquals("testString", accessor.getDescription());
  }

  @Test
  void testAccessIsActive() throws Exception {
    AiAgentConfig accessor = new AiAgentConfig();
    assertSame(accessor, accessor.setIsActive(true));
    assertEquals(true, accessor.getIsActive());
  }

  @Test
  void testAccessChatModelConfig() throws Exception {
    AiAgentConfig accessor = new AiAgentConfig();
    AiChatModelConfig value = mock(AiChatModelConfig.class);
    assertSame(accessor, accessor.setChatModelConfig(value));
    assertEquals(value, accessor.getChatModelConfig());
  }

  @Test
  void testAccessChatMemoryConfig() throws Exception {
    AiAgentConfig accessor = new AiAgentConfig();
    AiChatMemoryConfig value = mock(AiChatMemoryConfig.class);
    assertSame(accessor, accessor.setChatMemoryConfig(value));
    assertEquals(value, accessor.getChatMemoryConfig());
  }

  @Test
  void testAccessVersion() throws Exception {
    AiAgentConfig accessor = new AiAgentConfig();
    assertSame(accessor, accessor.setVersion(123));
    assertEquals(123, accessor.getVersion());
  }

  @Test
  void testAccessCreatedAt() throws Exception {
    AiAgentConfig accessor = new AiAgentConfig();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setCreatedAt(value));
    assertEquals(value, accessor.getCreatedAt());
  }

  @Test
  void testAccessLastUpdated() throws Exception {
    AiAgentConfig accessor = new AiAgentConfig();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setLastUpdated(value));
    assertEquals(value, accessor.getLastUpdated());
  }

}
