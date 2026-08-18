package sh.trishul.ai.session.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
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

  @Test
  void testAccessSessionKey() throws Exception {
    AddAiChatSessionDto accessor = new AddAiChatSessionDto();
    assertSame(accessor, accessor.setSessionKey("testString"));
    assertEquals("testString", accessor.getSessionKey());
  }

  @Test
  void testAccessTitle() throws Exception {
    AddAiChatSessionDto accessor = new AddAiChatSessionDto();
    assertSame(accessor, accessor.setTitle("testString"));
    assertEquals("testString", accessor.getTitle());
  }

  @Test
  void testAccessIsActive() throws Exception {
    AddAiChatSessionDto accessor = new AddAiChatSessionDto();
    assertSame(accessor, accessor.setIsActive(true));
    assertEquals(true, accessor.getIsActive());
  }

  @Test
  void testAccessAgentConfigId() throws Exception {
    AddAiChatSessionDto accessor = new AddAiChatSessionDto();
    assertSame(accessor, accessor.setAgentConfigId(123L));
    assertEquals(123L, accessor.getAgentConfigId());
  }

  @Test
  void testAccessChatMemoryConfigId() throws Exception {
    AddAiChatSessionDto accessor = new AddAiChatSessionDto();
    assertSame(accessor, accessor.setChatMemoryConfigId(123L));
    assertEquals(123L, accessor.getChatMemoryConfigId());
  }

}
