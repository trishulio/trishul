package io.trishul.ai.memory.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class UpdateAiChatMemoryConfigDtoTest {

  @Test
  void testGettersAndSetters() {
    UpdateAiChatMemoryConfigDto dto
        = new UpdateAiChatMemoryConfigDto(1L, "name", "strategy", 10, 100, 30, true, 1);

    assertEquals(1L, dto.getId());
    assertEquals("name", dto.getName());
    assertEquals("strategy", dto.getStrategy());
    assertEquals(10, dto.getMaxMessages());
    assertEquals(100, dto.getMaxTokens());
    assertEquals(30, dto.getTtlMinutes());
    assertTrue(dto.getIsDefault());
    assertEquals(1, dto.getVersion());

    dto.setId(2L).setName("new-name").setStrategy("new-strategy").setMaxMessages(20)
        .setMaxTokens(200).setTtlMinutes(60).setIsDefault(false).setVersion(2);

    assertEquals(2L, dto.getId());
    assertEquals("new-name", dto.getName());
    assertEquals("new-strategy", dto.getStrategy());
    assertEquals(20, dto.getMaxMessages());
    assertEquals(200, dto.getMaxTokens());
    assertEquals(60, dto.getTtlMinutes());
    assertTrue(!dto.getIsDefault());
    assertEquals(2, dto.getVersion());
  }
}
