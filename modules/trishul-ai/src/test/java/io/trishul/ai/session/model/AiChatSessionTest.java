package io.trishul.ai.session.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import io.trishul.ai.agent.model.AiAgentConfig;
import io.trishul.ai.memory.model.AiChatMemoryConfig;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class AiChatSessionTest {

  @Test
  void testGettersAndSetters() {
    LocalDateTime now = LocalDateTime.now();
    AiAgentConfig agentConfig = new AiAgentConfig(1L);
    AiChatMemoryConfig memoryConfig = new AiChatMemoryConfig(1L);

    AiChatSession session
        = new AiChatSession(1L, "key", "title", true, agentConfig, memoryConfig, now, now, 1);

    assertEquals(1L, session.getId());
    assertEquals("key", session.getSessionKey());
    assertEquals("title", session.getTitle());
    assertTrue(session.getIsActive());
    assertEquals(agentConfig, session.getAgentConfig());
    assertEquals(memoryConfig, session.getChatMemoryConfig());
    assertEquals(now, session.getCreatedAt());
    assertEquals(now, session.getLastUpdated());
    assertEquals(1, session.getVersion());

    session.setId(2L).setSessionKey("new-key").setTitle("new-title").setIsActive(false)
        .setAgentConfig(new AiAgentConfig(2L)).setChatMemoryConfig(new AiChatMemoryConfig(2L))
        .setCreatedAt(now.plusDays(1)).setLastUpdated(now.plusDays(1)).setVersion(2);

    assertEquals(2L, session.getId());
    assertEquals("new-key", session.getSessionKey());
    assertEquals("new-title", session.getTitle());
    assertTrue(!session.getIsActive());
    assertEquals(2L, session.getAgentConfig().getId());
    assertEquals(2L, session.getChatMemoryConfig().getId());
    assertEquals(now.plusDays(1), session.getCreatedAt());
    assertEquals(now.plusDays(1), session.getLastUpdated());
    assertEquals(2, session.getVersion());
  }
}
