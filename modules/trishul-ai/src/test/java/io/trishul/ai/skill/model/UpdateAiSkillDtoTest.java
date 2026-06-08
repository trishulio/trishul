package io.trishul.ai.skill.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class UpdateAiSkillDtoTest {

  @Test
  void testGettersAndSetters() {
    UpdateAiSkillDto dto = new UpdateAiSkillDto(1L, "name", "desc", "prompt", true, 1);

    assertEquals(1L, dto.getId());
    assertEquals("name", dto.getName());
    assertEquals("desc", dto.getDescription());
    assertEquals("prompt", dto.getSystemPrompt());
    assertTrue(dto.getIsEnabled());
    assertEquals(1, dto.getVersion());

    dto.setId(2L).setName("new-name").setDescription("new-desc").setSystemPrompt("new-prompt")
        .setIsEnabled(false).setVersion(2);

    assertEquals(2L, dto.getId());
    assertEquals("new-name", dto.getName());
    assertEquals("new-desc", dto.getDescription());
    assertEquals("new-prompt", dto.getSystemPrompt());
    assertTrue(!dto.getIsEnabled());
    assertEquals(2, dto.getVersion());
  }
}
