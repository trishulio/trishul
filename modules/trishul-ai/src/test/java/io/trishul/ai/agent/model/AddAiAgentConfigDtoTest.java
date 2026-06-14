package io.trishul.ai.agent.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
