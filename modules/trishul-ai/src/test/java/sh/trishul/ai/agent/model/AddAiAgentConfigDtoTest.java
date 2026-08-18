package sh.trishul.ai.agent.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import org.junit.jupiter.api.Test;

class AddAiAgentConfigDtoTest {

  @Test
  void testGettersAndSetters() {
    AddAiAgentConfigDto dto
        = new AddAiAgentConfigDto("name", "desc", true, 1L, 1L, Set.of(1L), Set.of(2L), Set.of(3L));

    assertEquals("name", dto.getName());
    assertEquals("desc", dto.getDescription());
    assertTrue(dto.getIsActive());
    assertEquals(1L, dto.getChatModelConfigId());
    assertEquals(1L, dto.getChatMemoryConfigId());
    assertEquals(Set.of(1L), dto.getGuardrailIds());
    assertEquals(Set.of(2L), dto.getSkillIds());
    assertEquals(Set.of(3L), dto.getToolIds());

    dto.setName("new-name").setDescription("new-desc").setIsActive(false).setChatModelConfigId(2L)
        .setChatMemoryConfigId(2L).setGuardrailIds(Set.of(4L)).setSkillIds(Set.of(5L))
        .setToolIds(Set.of(6L));

    assertEquals("new-name", dto.getName());
    assertEquals("new-desc", dto.getDescription());
    assertTrue(!dto.getIsActive());
    assertEquals(2L, dto.getChatModelConfigId());
    assertEquals(2L, dto.getChatMemoryConfigId());
    assertEquals(Set.of(4L), dto.getGuardrailIds());
    assertEquals(Set.of(5L), dto.getSkillIds());
    assertEquals(Set.of(6L), dto.getToolIds());
  }

  @Test
  void testAccessName() throws Exception {
    AddAiAgentConfigDto accessor = new AddAiAgentConfigDto();
    assertSame(accessor, accessor.setName("testString"));
    assertEquals("testString", accessor.getName());
  }

  @Test
  void testAccessDescription() throws Exception {
    AddAiAgentConfigDto accessor = new AddAiAgentConfigDto();
    assertSame(accessor, accessor.setDescription("testString"));
    assertEquals("testString", accessor.getDescription());
  }

  @Test
  void testAccessIsActive() throws Exception {
    AddAiAgentConfigDto accessor = new AddAiAgentConfigDto();
    assertSame(accessor, accessor.setIsActive(true));
    assertEquals(true, accessor.getIsActive());
  }

  @Test
  void testAccessChatModelConfigId() throws Exception {
    AddAiAgentConfigDto accessor = new AddAiAgentConfigDto();
    assertSame(accessor, accessor.setChatModelConfigId(123L));
    assertEquals(123L, accessor.getChatModelConfigId());
  }

  @Test
  void testAccessChatMemoryConfigId() throws Exception {
    AddAiAgentConfigDto accessor = new AddAiAgentConfigDto();
    assertSame(accessor, accessor.setChatMemoryConfigId(123L));
    assertEquals(123L, accessor.getChatMemoryConfigId());
  }

  @Test
  void testAccessGuardrailIds() throws Exception {
    AddAiAgentConfigDto accessor = new AddAiAgentConfigDto();
    Set<Long> value = Set.of();
    assertSame(accessor, accessor.setGuardrailIds(value));
    assertEquals(value, accessor.getGuardrailIds());
  }

  @Test
  void testAccessSkillIds() throws Exception {
    AddAiAgentConfigDto accessor = new AddAiAgentConfigDto();
    Set<Long> value = Set.of();
    assertSame(accessor, accessor.setSkillIds(value));
    assertEquals(value, accessor.getSkillIds());
  }

  @Test
  void testAccessToolIds() throws Exception {
    AddAiAgentConfigDto accessor = new AddAiAgentConfigDto();
    Set<Long> value = Set.of();
    assertSame(accessor, accessor.setToolIds(value));
    assertEquals(value, accessor.getToolIds());
  }

}
