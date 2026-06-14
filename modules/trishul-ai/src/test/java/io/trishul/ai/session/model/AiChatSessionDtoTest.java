package io.trishul.ai.session.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.trishul.ai.agent.model.AiAgentConfigDto;
import io.trishul.ai.memory.model.AiChatMemoryConfigDto;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class AiChatSessionDtoTest {

  @Test
  void testGettersAndSetters() {
    LocalDateTime now = LocalDateTime.now();
    AiAgentConfigDto agentConfig = new AiAgentConfigDto(1L);
    AiChatMemoryConfigDto memoryConfig = new AiChatMemoryConfigDto(1L);

    AiChatSessionDto dto
        = new AiChatSessionDto(1L, "key", "title", true, agentConfig, memoryConfig, now, now, 1);

    assertEquals(1L, dto.getId());
    assertEquals("key", dto.getSessionKey());
    assertEquals("title", dto.getTitle());
    assertTrue(dto.getIsActive());
    assertEquals(agentConfig, dto.getAgentConfig());
    assertEquals(memoryConfig, dto.getChatMemoryConfig());
    assertEquals(now, dto.getCreatedAt());
    assertEquals(now, dto.getLastUpdated());
    assertEquals(1, dto.getVersion());

    dto.setId(2L).setSessionKey("new-key").setTitle("new-title").setIsActive(false)
        .setAgentConfig(new AiAgentConfigDto(2L)).setChatMemoryConfig(new AiChatMemoryConfigDto(2L))
        .setCreatedAt(now.plusDays(1)).setLastUpdated(now.plusDays(1)).setVersion(2);

    assertEquals(2L, dto.getId());
    assertEquals("new-key", dto.getSessionKey());
    assertEquals("new-title", dto.getTitle());
    assertTrue(!dto.getIsActive());
    assertEquals(2L, dto.getAgentConfig().getId());
    assertEquals(2L, dto.getChatMemoryConfig().getId());
    assertEquals(now.plusDays(1), dto.getCreatedAt());
    assertEquals(now.plusDays(1), dto.getLastUpdated());
    assertEquals(2, dto.getVersion());
  }
}
