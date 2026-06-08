package io.trishul.ai.memory.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class AddAiChatMemoryConfigDtoTest {

  @Test
  void testGettersAndSetters() {
    AddAiChatMemoryConfigDto dto
        = new AddAiChatMemoryConfigDto("name", "strategy", 10, 100, 30, true);

    assertEquals("name", dto.getName());
    assertEquals("strategy", dto.getStrategy());
    assertEquals(10, dto.getMaxMessages());
    assertEquals(100, dto.getMaxTokens());
    assertEquals(30, dto.getTtlMinutes());
    assertTrue(dto.getIsDefault());

    dto.setName("new-name").setStrategy("new-strategy").setMaxMessages(20).setMaxTokens(200)
        .setTtlMinutes(60).setIsDefault(false);

    assertEquals("new-name", dto.getName());
    assertEquals("new-strategy", dto.getStrategy());
    assertEquals(20, dto.getMaxMessages());
    assertEquals(200, dto.getMaxTokens());
    assertEquals(60, dto.getTtlMinutes());
    assertTrue(!dto.getIsDefault());
  }
}
