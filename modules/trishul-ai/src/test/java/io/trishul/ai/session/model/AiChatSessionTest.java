package io.trishul.ai.session.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

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

  @Test
  void testAccessId() throws Exception {
    AiChatSession accessor = new AiChatSession();
    assertSame(accessor, accessor.setId(123L));
    assertEquals(123L, accessor.getId());
  }

  @Test
  void testAccessSessionKey() throws Exception {
    AiChatSession accessor = new AiChatSession();
    assertSame(accessor, accessor.setSessionKey("testString"));
    assertEquals("testString", accessor.getSessionKey());
  }

  @Test
  void testAccessTitle() throws Exception {
    AiChatSession accessor = new AiChatSession();
    assertSame(accessor, accessor.setTitle("testString"));
    assertEquals("testString", accessor.getTitle());
  }

  @Test
  void testAccessIsActive() throws Exception {
    AiChatSession accessor = new AiChatSession();
    assertSame(accessor, accessor.setIsActive(true));
    assertEquals(true, accessor.getIsActive());
  }

  @Test
  void testAccessAgentConfig() throws Exception {
    AiChatSession accessor = new AiChatSession();
    AiAgentConfig value = mock(AiAgentConfig.class);
    assertSame(accessor, accessor.setAgentConfig(value));
    assertEquals(value, accessor.getAgentConfig());
  }

  @Test
  void testAccessChatMemoryConfig() throws Exception {
    AiChatSession accessor = new AiChatSession();
    AiChatMemoryConfig value = mock(AiChatMemoryConfig.class);
    assertSame(accessor, accessor.setChatMemoryConfig(value));
    assertEquals(value, accessor.getChatMemoryConfig());
  }

  @Test
  void testAccessVersion() throws Exception {
    AiChatSession accessor = new AiChatSession();
    assertSame(accessor, accessor.setVersion(123));
    assertEquals(123, accessor.getVersion());
  }

  @Test
  void testAccessCreatedAt() throws Exception {
    AiChatSession accessor = new AiChatSession();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setCreatedAt(value));
    assertEquals(value, accessor.getCreatedAt());
  }

  @Test
  void testAccessLastUpdated() throws Exception {
    AiChatSession accessor = new AiChatSession();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setLastUpdated(value));
    assertEquals(value, accessor.getLastUpdated());
  }

}
