package io.trishul.ai.guardrail.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class AddAiGuardrailDtoTest {

  @Test
  void testGettersAndSetters() {
    AddAiGuardrailDto dto
        = new AddAiGuardrailDto("name", AiGuardrailType.INPUT, "strategy", "config", 1, true);

    assertEquals("name", dto.getName());
    assertEquals(AiGuardrailType.INPUT, dto.getType());
    assertEquals("strategy", dto.getStrategy());
    assertEquals("config", dto.getConfiguration());
    assertEquals(1, dto.getPriority());
    assertTrue(dto.getIsEnabled());

    dto.setName("new-name").setType(AiGuardrailType.OUTPUT).setStrategy("new-strategy")
        .setConfiguration("new-config").setPriority(2).setIsEnabled(false);

    assertEquals("new-name", dto.getName());
    assertEquals(AiGuardrailType.OUTPUT, dto.getType());
    assertEquals("new-strategy", dto.getStrategy());
    assertEquals("new-config", dto.getConfiguration());
    assertEquals(2, dto.getPriority());
    assertTrue(!dto.getIsEnabled());
  }
}
