package sh.trishul.ai.skill.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class AiSkillTest {

  @Test
  void testGettersAndSetters() {
    LocalDateTime now = LocalDateTime.now();
    AiSkill config = new AiSkill(1L, "name", "desc", "prompt", true, now, now, 1);

    assertEquals(1L, config.getId());
    assertEquals("name", config.getName());
    assertEquals("desc", config.getDescription());
    assertEquals("prompt", config.getSystemPrompt());
    assertTrue(config.getIsEnabled());
    assertEquals(now, config.getCreatedAt());
    assertEquals(now, config.getLastUpdated());
    assertEquals(1, config.getVersion());

    config.setId(2L).setName("new-name").setDescription("new-desc").setSystemPrompt("new-prompt")
        .setIsEnabled(false).setCreatedAt(now.plusDays(1)).setLastUpdated(now.plusDays(1))
        .setVersion(2);

    assertEquals(2L, config.getId());
    assertEquals("new-name", config.getName());
    assertEquals("new-desc", config.getDescription());
    assertEquals("new-prompt", config.getSystemPrompt());
    assertTrue(!config.getIsEnabled());
    assertEquals(now.plusDays(1), config.getCreatedAt());
    assertEquals(now.plusDays(1), config.getLastUpdated());
    assertEquals(2, config.getVersion());
  }

  @Test
  void testAccessId() throws Exception {
    AiSkill accessor = new AiSkill();
    assertSame(accessor, accessor.setId(123L));
    assertEquals(123L, accessor.getId());
  }

  @Test
  void testAccessName() throws Exception {
    AiSkill accessor = new AiSkill();
    assertSame(accessor, accessor.setName("testString"));
    assertEquals("testString", accessor.getName());
  }

  @Test
  void testAccessDescription() throws Exception {
    AiSkill accessor = new AiSkill();
    assertSame(accessor, accessor.setDescription("testString"));
    assertEquals("testString", accessor.getDescription());
  }

  @Test
  void testAccessSystemPrompt() throws Exception {
    AiSkill accessor = new AiSkill();
    assertSame(accessor, accessor.setSystemPrompt("testString"));
    assertEquals("testString", accessor.getSystemPrompt());
  }

  @Test
  void testAccessIsEnabled() throws Exception {
    AiSkill accessor = new AiSkill();
    assertSame(accessor, accessor.setIsEnabled(true));
    assertEquals(true, accessor.getIsEnabled());
  }

  @Test
  void testAccessVersion() throws Exception {
    AiSkill accessor = new AiSkill();
    assertSame(accessor, accessor.setVersion(123));
    assertEquals(123, accessor.getVersion());
  }

  @Test
  void testAccessCreatedAt() throws Exception {
    AiSkill accessor = new AiSkill();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setCreatedAt(value));
    assertEquals(value, accessor.getCreatedAt());
  }

  @Test
  void testAccessLastUpdated() throws Exception {
    AiSkill accessor = new AiSkill();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setLastUpdated(value));
    assertEquals(value, accessor.getLastUpdated());
  }

}
