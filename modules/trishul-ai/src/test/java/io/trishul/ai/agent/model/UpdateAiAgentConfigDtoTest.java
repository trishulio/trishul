package io.trishul.ai.agent.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import org.junit.jupiter.api.Test;

class UpdateAiAgentConfigDtoTest {

  @Test
  void testGettersAndSetters() {
    UpdateAiAgentConfigDto dto = new UpdateAiAgentConfigDto(1L, "name", "desc", true, 1L, 1L,
        Set.of(1L), Set.of(2L), Set.of(3L), 1);

    assertEquals(1L, dto.getId());
    assertEquals("name", dto.getName());
    assertEquals("desc", dto.getDescription());
    assertTrue(dto.getIsActive());
    assertEquals(1L, dto.getChatModelConfigId());
    assertEquals(1L, dto.getChatMemoryConfigId());
    assertEquals(Set.of(1L), dto.getGuardrailIds());
    assertEquals(Set.of(2L), dto.getSkillIds());
    assertEquals(Set.of(3L), dto.getToolIds());
    assertEquals(1, dto.getVersion());

    dto.setId(2L).setName("new-name").setDescription("new-desc").setIsActive(false)
        .setChatModelConfigId(2L).setChatMemoryConfigId(2L).setGuardrailIds(Set.of(4L))
        .setSkillIds(Set.of(5L)).setToolIds(Set.of(6L)).setVersion(2);

    assertEquals(2L, dto.getId());
    assertEquals("new-name", dto.getName());
    assertEquals("new-desc", dto.getDescription());
    assertTrue(!dto.getIsActive());
    assertEquals(2L, dto.getChatModelConfigId());
    assertEquals(2L, dto.getChatMemoryConfigId());
    assertEquals(Set.of(4L), dto.getGuardrailIds());
    assertEquals(Set.of(5L), dto.getSkillIds());
    assertEquals(Set.of(6L), dto.getToolIds());
    assertEquals(2, dto.getVersion());
  }

  @Test
  void testAccessId() throws Exception {
    UpdateAiAgentConfigDto accessor = new UpdateAiAgentConfigDto();
    assertSame(accessor, accessor.setId(123L));
    assertEquals(123L, accessor.getId());
  }

  @Test
  void testAccessName() throws Exception {
    UpdateAiAgentConfigDto accessor = new UpdateAiAgentConfigDto();
    assertSame(accessor, accessor.setName("testString"));
    assertEquals("testString", accessor.getName());
  }

  @Test
  void testAccessDescription() throws Exception {
    UpdateAiAgentConfigDto accessor = new UpdateAiAgentConfigDto();
    assertSame(accessor, accessor.setDescription("testString"));
    assertEquals("testString", accessor.getDescription());
  }

  @Test
  void testAccessIsActive() throws Exception {
    UpdateAiAgentConfigDto accessor = new UpdateAiAgentConfigDto();
    assertSame(accessor, accessor.setIsActive(true));
    assertEquals(true, accessor.getIsActive());
  }

  @Test
  void testAccessChatModelConfigId() throws Exception {
    UpdateAiAgentConfigDto accessor = new UpdateAiAgentConfigDto();
    assertSame(accessor, accessor.setChatModelConfigId(123L));
    assertEquals(123L, accessor.getChatModelConfigId());
  }

  @Test
  void testAccessChatMemoryConfigId() throws Exception {
    UpdateAiAgentConfigDto accessor = new UpdateAiAgentConfigDto();
    assertSame(accessor, accessor.setChatMemoryConfigId(123L));
    assertEquals(123L, accessor.getChatMemoryConfigId());
  }

  @Test
  void testAccessGuardrailIds() throws Exception {
    UpdateAiAgentConfigDto accessor = new UpdateAiAgentConfigDto();
    Set<Long> value = Set.of();
    assertSame(accessor, accessor.setGuardrailIds(value));
    assertEquals(value, accessor.getGuardrailIds());
  }

  @Test
  void testAccessSkillIds() throws Exception {
    UpdateAiAgentConfigDto accessor = new UpdateAiAgentConfigDto();
    Set<Long> value = Set.of();
    assertSame(accessor, accessor.setSkillIds(value));
    assertEquals(value, accessor.getSkillIds());
  }

  @Test
  void testAccessToolIds() throws Exception {
    UpdateAiAgentConfigDto accessor = new UpdateAiAgentConfigDto();
    Set<Long> value = Set.of();
    assertSame(accessor, accessor.setToolIds(value));
    assertEquals(value, accessor.getToolIds());
  }

  @Test
  void testAccessVersion() throws Exception {
    UpdateAiAgentConfigDto accessor = new UpdateAiAgentConfigDto();
    assertSame(accessor, accessor.setVersion(123));
    assertEquals(123, accessor.getVersion());
  }

}
