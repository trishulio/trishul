package io.trishul.ai.session.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
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

  @Test
  void testAccessId() throws Exception {
    UpdateAiChatSessionDto accessor = new UpdateAiChatSessionDto();
    assertSame(accessor, accessor.setId(123L));
    assertEquals(123L, accessor.getId());
  }

  @Test
  void testAccessTitle() throws Exception {
    UpdateAiChatSessionDto accessor = new UpdateAiChatSessionDto();
    assertSame(accessor, accessor.setTitle("testString"));
    assertEquals("testString", accessor.getTitle());
  }

  @Test
  void testAccessIsActive() throws Exception {
    UpdateAiChatSessionDto accessor = new UpdateAiChatSessionDto();
    assertSame(accessor, accessor.setIsActive(true));
    assertEquals(true, accessor.getIsActive());
  }

  @Test
  void testAccessAgentConfigId() throws Exception {
    UpdateAiChatSessionDto accessor = new UpdateAiChatSessionDto();
    assertSame(accessor, accessor.setAgentConfigId(123L));
    assertEquals(123L, accessor.getAgentConfigId());
  }

  @Test
  void testAccessChatMemoryConfigId() throws Exception {
    UpdateAiChatSessionDto accessor = new UpdateAiChatSessionDto();
    assertSame(accessor, accessor.setChatMemoryConfigId(123L));
    assertEquals(123L, accessor.getChatMemoryConfigId());
  }

  @Test
  void testAccessVersion() throws Exception {
    UpdateAiChatSessionDto accessor = new UpdateAiChatSessionDto();
    assertSame(accessor, accessor.setVersion(123));
    assertEquals(123, accessor.getVersion());
  }

}
