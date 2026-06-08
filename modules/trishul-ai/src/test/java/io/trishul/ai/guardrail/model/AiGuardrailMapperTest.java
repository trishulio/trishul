package io.trishul.ai.guardrail.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AiGuardrailMapperTest {
  private AiGuardrailMapper mapper;

  @BeforeEach
  void init() {
    mapper = AiGuardrailMapper.INSTANCE;
  }

  @Test
  void testFromDto_ReturnsPojo_WhenIdIsNotNull() {
    AiGuardrail expected = new AiGuardrail(1L);
    assertEquals(expected, mapper.fromDto(1L));
  }

  @Test
  void testFromAddDto_ReturnsEntity_WhenAddDtoIsNotNull() {
    AddAiGuardrailDto dto
        = new AddAiGuardrailDto("name", AiGuardrailType.INPUT, "strategy", "config", 1, true);

    AiGuardrail entity = mapper.fromAddDto(dto);

    AiGuardrail expected = new AiGuardrail().setName("name").setType(AiGuardrailType.INPUT)
        .setStrategy("strategy").setConfiguration("config").setPriority(1).setIsEnabled(true);

    assertEquals(expected, entity);
  }

  @Test
  void testFromUpdateDto_ReturnsEntity_WhenUpdateDtoIsNotNull() {
    UpdateAiGuardrailDto dto = new UpdateAiGuardrailDto(1L, "name", AiGuardrailType.OUTPUT,
        "strategy", "config", 2, false, 1);

    AiGuardrail entity = mapper.fromUpdateDto(dto);

    AiGuardrail expected = new AiGuardrail().setId(1L).setName("name")
        .setType(AiGuardrailType.OUTPUT).setStrategy("strategy").setConfiguration("config")
        .setPriority(2).setIsEnabled(false).setVersion(1);

    assertEquals(expected, entity);
  }

  @Test
  void testToDto_ReturnsDto_WhenEntityIsNotNull() {
    LocalDateTime now = LocalDateTime.now();
    AiGuardrail entity = new AiGuardrail(1L, "name", AiGuardrailType.INPUT, "strategy", "config", 1,
        true, now, now, 1);

    AiGuardrailDto dto = mapper.toDto(entity);

    AiGuardrailDto expected = new AiGuardrailDto(1L, "name", AiGuardrailType.INPUT, "strategy",
        "config", 1, true, now, now, 1);

    assertEquals(expected, dto);
  }
}
