package io.trishul.ai.guardrail.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class UpdateAiGuardrailDtoTest {

  @Test
  void testGettersAndSetters() {
    UpdateAiGuardrailDto dto = new UpdateAiGuardrailDto(1L, "name", AiGuardrailType.INPUT,
        "strategy", "config", 1, true, 1);

    assertEquals(1L, dto.getId());
    assertEquals("name", dto.getName());
    assertEquals(AiGuardrailType.INPUT, dto.getType());
    assertEquals("strategy", dto.getStrategy());
    assertEquals("config", dto.getConfiguration());
    assertEquals(1, dto.getPriority());
    assertTrue(dto.getIsEnabled());
    assertEquals(1, dto.getVersion());

    dto.setId(2L).setName("new-name").setType(AiGuardrailType.OUTPUT).setStrategy("new-strategy")
        .setConfiguration("new-config").setPriority(2).setIsEnabled(false).setVersion(2);

    assertEquals(2L, dto.getId());
    assertEquals("new-name", dto.getName());
    assertEquals(AiGuardrailType.OUTPUT, dto.getType());
    assertEquals("new-strategy", dto.getStrategy());
    assertEquals("new-config", dto.getConfiguration());
    assertEquals(2, dto.getPriority());
    assertTrue(!dto.getIsEnabled());
    assertEquals(2, dto.getVersion());
  }
}
