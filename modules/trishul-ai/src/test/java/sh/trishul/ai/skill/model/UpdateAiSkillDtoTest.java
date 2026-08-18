package sh.trishul.ai.skill.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
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

  @Test
  void testAccessId() throws Exception {
    UpdateAiSkillDto accessor = new UpdateAiSkillDto();
    assertSame(accessor, accessor.setId(123L));
    assertEquals(123L, accessor.getId());
  }

  @Test
  void testAccessName() throws Exception {
    UpdateAiSkillDto accessor = new UpdateAiSkillDto();
    assertSame(accessor, accessor.setName("testString"));
    assertEquals("testString", accessor.getName());
  }

  @Test
  void testAccessDescription() throws Exception {
    UpdateAiSkillDto accessor = new UpdateAiSkillDto();
    assertSame(accessor, accessor.setDescription("testString"));
    assertEquals("testString", accessor.getDescription());
  }

  @Test
  void testAccessSystemPrompt() throws Exception {
    UpdateAiSkillDto accessor = new UpdateAiSkillDto();
    assertSame(accessor, accessor.setSystemPrompt("testString"));
    assertEquals("testString", accessor.getSystemPrompt());
  }

  @Test
  void testAccessIsEnabled() throws Exception {
    UpdateAiSkillDto accessor = new UpdateAiSkillDto();
    assertSame(accessor, accessor.setIsEnabled(true));
    assertEquals(true, accessor.getIsEnabled());
  }

  @Test
  void testAccessVersion() throws Exception {
    UpdateAiSkillDto accessor = new UpdateAiSkillDto();
    assertSame(accessor, accessor.setVersion(123));
    assertEquals(123, accessor.getVersion());
  }

}
