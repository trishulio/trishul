package io.trishul.ai.tool.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AiToolMapperTest {
  private AiToolMapper mapper;

  @BeforeEach
  void init() {
    mapper = AiToolMapper.INSTANCE;
  }

  @Test
  void testFromDto_ReturnsPojo_WhenIdIsNotNull() {
    AiTool expected = new AiTool(1L);
    assertEquals(expected, mapper.fromDto(1L));
  }

  @Test
  void testFromAddDto_ReturnsEntity_WhenAddDtoIsNotNull() {
    AddAiToolDto dto = new AddAiToolDto("name", "bean", "desc", true);

    AiTool entity = mapper.fromAddDto(dto);

    AiTool expected = new AiTool().setName("name").setBeanName("bean").setDescription("desc")
        .setIsEnabled(true);

    assertEquals(expected, entity);
  }

  @Test
  void testFromUpdateDto_ReturnsEntity_WhenUpdateDtoIsNotNull() {
    UpdateAiToolDto dto = new UpdateAiToolDto(1L, "name", "bean", "desc", false, 1);

    AiTool entity = mapper.fromUpdateDto(dto);

    AiTool expected = new AiTool().setId(1L).setName("name").setBeanName("bean")
        .setDescription("desc").setIsEnabled(false).setVersion(1);

    assertEquals(expected, entity);
  }

  @Test
  void testToDto_ReturnsDto_WhenEntityIsNotNull() {
    LocalDateTime now = LocalDateTime.now();
    AiTool entity = new AiTool(1L, "name", "bean", "desc", true, now, now, 1);

    AiToolDto dto = mapper.toDto(entity);

    AiToolDto expected = new AiToolDto(1L, "name", "bean", "desc", true, now, now, 1);

    assertEquals(expected, dto);
  }
}
