package sh.trishul.ai.skill.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class AiSkillDtoTest {
  @Test
  void testAccessId() throws Exception {
    AiSkillDto accessor = new AiSkillDto();
    assertSame(accessor, accessor.setId(123L));
    assertEquals(123L, accessor.getId());
  }

  @Test
  void testAccessName() throws Exception {
    AiSkillDto accessor = new AiSkillDto();
    assertSame(accessor, accessor.setName("testString"));
    assertEquals("testString", accessor.getName());
  }

  @Test
  void testAccessDescription() throws Exception {
    AiSkillDto accessor = new AiSkillDto();
    assertSame(accessor, accessor.setDescription("testString"));
    assertEquals("testString", accessor.getDescription());
  }

  @Test
  void testAccessSystemPrompt() throws Exception {
    AiSkillDto accessor = new AiSkillDto();
    assertSame(accessor, accessor.setSystemPrompt("testString"));
    assertEquals("testString", accessor.getSystemPrompt());
  }

  @Test
  void testAccessIsEnabled() throws Exception {
    AiSkillDto accessor = new AiSkillDto();
    assertSame(accessor, accessor.setIsEnabled(true));
    assertEquals(true, accessor.getIsEnabled());

    assertSame(accessor, accessor.setIsEnabled(false));
    assertEquals(false, accessor.getIsEnabled());
  }

  @Test
  void testAccessCreatedAt() throws Exception {
    AiSkillDto accessor = new AiSkillDto();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setCreatedAt(value));
    assertEquals(value, accessor.getCreatedAt());
  }

  @Test
  void testAccessLastUpdated() throws Exception {
    AiSkillDto accessor = new AiSkillDto();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setLastUpdated(value));
    assertEquals(value, accessor.getLastUpdated());
  }

  @Test
  void testAccessVersion() throws Exception {
    AiSkillDto accessor = new AiSkillDto();
    assertSame(accessor, accessor.setVersion(123));
    assertEquals(123, accessor.getVersion());
  }
}
