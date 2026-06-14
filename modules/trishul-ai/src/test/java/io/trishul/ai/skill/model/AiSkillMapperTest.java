package io.trishul.ai.skill.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AiSkillMapperTest {
  private AiSkillMapper mapper;

  @BeforeEach
  void init() {
    mapper = AiSkillMapper.INSTANCE;
  }

  @Test
  void testFromDto_ReturnsPojo_WhenIdIsNotNull() {
    AiSkill expected = new AiSkill(1L);
    assertEquals(expected, mapper.fromDto(1L));
  }

  @Test
  void testFromAddDto_ReturnsEntity_WhenAddDtoIsNotNull() {
    AddAiSkillDto dto = new AddAiSkillDto("name", "desc", "prompt", true);

    AiSkill entity = mapper.fromAddDto(dto);

    AiSkill expected = new AiSkill().setName("name").setDescription("desc")
        .setSystemPrompt("prompt").setIsEnabled(true);

    assertEquals(expected, entity);
  }

  @Test
  void testFromUpdateDto_ReturnsEntity_WhenUpdateDtoIsNotNull() {
    UpdateAiSkillDto dto = new UpdateAiSkillDto(1L, "name", "desc", "prompt", false, 1);

    AiSkill entity = mapper.fromUpdateDto(dto);

    AiSkill expected = new AiSkill().setId(1L).setName("name").setDescription("desc")
        .setSystemPrompt("prompt").setIsEnabled(false).setVersion(1);

    assertEquals(expected, entity);
  }

  @Test
  void testToDto_ReturnsDto_WhenEntityIsNotNull() {
    LocalDateTime now = LocalDateTime.now();
    AiSkill entity = new AiSkill(1L, "name", "desc", "prompt", true, now, now, 1);

    AiSkillDto dto = mapper.toDto(entity);

    AiSkillDto expected = new AiSkillDto(1L, "name", "desc", "prompt", true, now, now, 1);

    assertEquals(expected, dto);
  }
}
