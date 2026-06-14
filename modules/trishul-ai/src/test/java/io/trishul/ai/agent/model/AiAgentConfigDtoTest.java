package io.trishul.ai.agent.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.trishul.ai.chat.model.AiChatModelConfigDto;
import io.trishul.ai.memory.model.AiChatMemoryConfigDto;
import java.time.LocalDateTime;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AiAgentConfigDtoTest {

  @Test
  void testGettersAndSetters() {
    LocalDateTime now = LocalDateTime.now();
    AiChatModelConfigDto chatModel = new AiChatModelConfigDto(1L);
    AiChatMemoryConfigDto memoryConfig = new AiChatMemoryConfigDto(1L);

    AiAgentConfigDto dto = new AiAgentConfigDto(1L, "name", "desc", true, chatModel, memoryConfig,
        Set.of(), Set.of(), Set.of(), now, now, 1);

    assertEquals(1L, dto.getId());
    assertEquals("name", dto.getName());
    assertEquals("desc", dto.getDescription());
    assertTrue(dto.getIsActive());
    assertEquals(chatModel, dto.getChatModelConfig());
    assertEquals(memoryConfig, dto.getChatMemoryConfig());
    assertEquals(now, dto.getCreatedAt());
    assertEquals(now, dto.getLastUpdated());
    assertEquals(1, dto.getVersion());

    dto.setId(2L).setName("new-name").setDescription("new-desc").setIsActive(false)
        .setChatModelConfig(new AiChatModelConfigDto(2L))
        .setChatMemoryConfig(new AiChatMemoryConfigDto(2L)).setCreatedAt(now.plusDays(1))
        .setLastUpdated(now.plusDays(1)).setVersion(2);

    assertEquals(2L, dto.getId());
    assertEquals("new-name", dto.getName());
    assertEquals("new-desc", dto.getDescription());
    assertTrue(!dto.getIsActive());
    assertEquals(2L, dto.getChatModelConfig().getId());
    assertEquals(2L, dto.getChatMemoryConfig().getId());
    assertEquals(now.plusDays(1), dto.getCreatedAt());
    assertEquals(now.plusDays(1), dto.getLastUpdated());
    assertEquals(2, dto.getVersion());
  }
}
