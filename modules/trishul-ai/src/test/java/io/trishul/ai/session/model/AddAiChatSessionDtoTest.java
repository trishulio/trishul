package io.trishul.ai.session.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class AddAiChatSessionDtoTest {

  @Test
  void testGettersAndSetters() {
    AddAiChatSessionDto dto = new AddAiChatSessionDto("key", "title", true, 1L, 1L);

    assertEquals("key", dto.getSessionKey());
    assertEquals("title", dto.getTitle());
    assertTrue(dto.getIsActive());
    assertEquals(1L, dto.getAgentConfigId());
    assertEquals(1L, dto.getChatMemoryConfigId());

    dto.setSessionKey("new-key").setTitle("new-title").setIsActive(false).setAgentConfigId(2L)
        .setChatMemoryConfigId(2L);

    assertEquals("new-key", dto.getSessionKey());
    assertEquals("new-title", dto.getTitle());
    assertTrue(!dto.getIsActive());
    assertEquals(2L, dto.getAgentConfigId());
    assertEquals(2L, dto.getChatMemoryConfigId());
  }
}
