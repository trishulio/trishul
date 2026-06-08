package io.trishul.ai.memory.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AiChatMemoryConfigMapperTest {
  private AiChatMemoryConfigMapper mapper;

  @BeforeEach
  void init() {
    mapper = AiChatMemoryConfigMapper.INSTANCE;
  }

  @Test
  void testFromDto_ReturnsPojo_WhenIdIsNotNull() {
    AiChatMemoryConfig expected = new AiChatMemoryConfig(1L);
    assertEquals(expected, mapper.fromDto(1L));
  }

  @Test
  void testFromAddDto_ReturnsEntity_WhenAddDtoIsNotNull() {
    AddAiChatMemoryConfigDto dto
        = new AddAiChatMemoryConfigDto("name", "strategy", 10, 100, 30, true);

    AiChatMemoryConfig entity = mapper.fromAddDto(dto);

    AiChatMemoryConfig expected = new AiChatMemoryConfig().setName("name").setStrategy("strategy")
        .setMaxMessages(10).setMaxTokens(100).setTtlMinutes(30).setIsDefault(true);

    assertEquals(expected, entity);
  }

  @Test
  void testFromUpdateDto_ReturnsEntity_WhenUpdateDtoIsNotNull() {
    UpdateAiChatMemoryConfigDto dto
        = new UpdateAiChatMemoryConfigDto(1L, "name", "strategy", 20, 200, 60, false, 1);

    AiChatMemoryConfig entity = mapper.fromUpdateDto(dto);

    AiChatMemoryConfig expected = new AiChatMemoryConfig().setId(1L).setName("name")
        .setStrategy("strategy").setMaxMessages(20).setMaxTokens(200).setTtlMinutes(60)
        .setIsDefault(false).setVersion(1);

    assertEquals(expected, entity);
  }

  @Test
  void testToDto_ReturnsDto_WhenEntityIsNotNull() {
    LocalDateTime now = LocalDateTime.now();
    AiChatMemoryConfig entity
        = new AiChatMemoryConfig(1L, "name", "strategy", 10, 100, 30, true, now, now, 1);

    AiChatMemoryConfigDto dto = mapper.toDto(entity);

    AiChatMemoryConfigDto expected
        = new AiChatMemoryConfigDto(1L, "name", "strategy", 10, 100, 30, true, now, now, 1);

    assertEquals(expected, dto);
  }
}
