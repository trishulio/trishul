package io.trishul.ai.skill.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class AddAiSkillDtoTest {

  @Test
  void testGettersAndSetters() {
    AddAiSkillDto dto = new AddAiSkillDto("name", "desc", "prompt", true);

    assertEquals("name", dto.getName());
    assertEquals("desc", dto.getDescription());
    assertEquals("prompt", dto.getSystemPrompt());
    assertTrue(dto.getIsEnabled());

    dto.setName("new-name").setDescription("new-desc").setSystemPrompt("new-prompt")
        .setIsEnabled(false);

    assertEquals("new-name", dto.getName());
    assertEquals("new-desc", dto.getDescription());
    assertEquals("new-prompt", dto.getSystemPrompt());
    assertTrue(!dto.getIsEnabled());
  }
}
