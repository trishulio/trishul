package io.trishul.ai.skill.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
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

  @Test
  void testAccessName() throws Exception {
    AddAiSkillDto accessor = new AddAiSkillDto();
    assertSame(accessor, accessor.setName("testString"));
    assertEquals("testString", accessor.getName());
  }

  @Test
  void testAccessDescription() throws Exception {
    AddAiSkillDto accessor = new AddAiSkillDto();
    assertSame(accessor, accessor.setDescription("testString"));
    assertEquals("testString", accessor.getDescription());
  }

  @Test
  void testAccessSystemPrompt() throws Exception {
    AddAiSkillDto accessor = new AddAiSkillDto();
    assertSame(accessor, accessor.setSystemPrompt("testString"));
    assertEquals("testString", accessor.getSystemPrompt());
  }

  @Test
  void testAccessIsEnabled() throws Exception {
    AddAiSkillDto accessor = new AddAiSkillDto();
    assertSame(accessor, accessor.setIsEnabled(true));
    assertEquals(true, accessor.getIsEnabled());
  }

}
