package io.trishul.ai.session.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

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

  @Test
  void testAccessId() throws Exception {
    AiChatSessionDto accessor = new AiChatSessionDto();
    assertSame(accessor, accessor.setId(123L));
    assertEquals(123L, accessor.getId());
  }

  @Test
  void testAccessSessionKey() throws Exception {
    AiChatSessionDto accessor = new AiChatSessionDto();
    assertSame(accessor, accessor.setSessionKey("testString"));
    assertEquals("testString", accessor.getSessionKey());
  }

  @Test
  void testAccessTitle() throws Exception {
    AiChatSessionDto accessor = new AiChatSessionDto();
    assertSame(accessor, accessor.setTitle("testString"));
    assertEquals("testString", accessor.getTitle());
  }

  @Test
  void testAccessIsActive() throws Exception {
    AiChatSessionDto accessor = new AiChatSessionDto();
    assertSame(accessor, accessor.setIsActive(true));
    assertEquals(true, accessor.getIsActive());
  }

  @Test
  void testAccessAgentConfig() throws Exception {
    AiChatSessionDto accessor = new AiChatSessionDto();
    AiAgentConfigDto value = mock(AiAgentConfigDto.class);
    assertSame(accessor, accessor.setAgentConfig(value));
    assertEquals(value, accessor.getAgentConfig());
  }

  @Test
  void testAccessChatMemoryConfig() throws Exception {
    AiChatSessionDto accessor = new AiChatSessionDto();
    AiChatMemoryConfigDto value = mock(AiChatMemoryConfigDto.class);
    assertSame(accessor, accessor.setChatMemoryConfig(value));
    assertEquals(value, accessor.getChatMemoryConfig());
  }

  @Test
  void testAccessCreatedAt() throws Exception {
    AiChatSessionDto accessor = new AiChatSessionDto();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setCreatedAt(value));
    assertEquals(value, accessor.getCreatedAt());
  }

  @Test
  void testAccessLastUpdated() throws Exception {
    AiChatSessionDto accessor = new AiChatSessionDto();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setLastUpdated(value));
    assertEquals(value, accessor.getLastUpdated());
  }

  @Test
  void testAccessVersion() throws Exception {
    AiChatSessionDto accessor = new AiChatSessionDto();
    assertSame(accessor, accessor.setVersion(123));
    assertEquals(123, accessor.getVersion());
  }

}
