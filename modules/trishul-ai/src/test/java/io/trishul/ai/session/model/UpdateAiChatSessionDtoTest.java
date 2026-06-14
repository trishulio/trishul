package io.trishul.ai.session.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class UpdateAiChatSessionDtoTest {

  @Test
  void testGettersAndSetters() {
    UpdateAiChatSessionDto dto = new UpdateAiChatSessionDto(1L, "title", true, 1L, 1L, 1);

    assertEquals(1L, dto.getId());
    assertEquals("title", dto.getTitle());
    assertTrue(dto.getIsActive());
    assertEquals(1L, dto.getAgentConfigId());
    assertEquals(1L, dto.getChatMemoryConfigId());
    assertEquals(1, dto.getVersion());

    dto.setId(2L).setTitle("new-title").setIsActive(false).setAgentConfigId(2L)
        .setChatMemoryConfigId(2L).setVersion(2);

    assertEquals(2L, dto.getId());
    assertEquals("new-title", dto.getTitle());
    assertTrue(!dto.getIsActive());
    assertEquals(2L, dto.getAgentConfigId());
    assertEquals(2L, dto.getChatMemoryConfigId());
    assertEquals(2, dto.getVersion());
  }
}
