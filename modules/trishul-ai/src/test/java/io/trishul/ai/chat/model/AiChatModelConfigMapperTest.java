package io.trishul.ai.chat.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AiChatModelConfigMapperTest {
  private AiChatModelConfigMapper mapper;

  @BeforeEach
  void init() {
    mapper = AiChatModelConfigMapper.INSTANCE;
  }

  @Test
  void testFromPojo_ReturnsNull_WhenIdIsNull() {
    assertNull(mapper.fromDto(null));
  }

  @Test
  void testFromPojo_ReturnsPojo_WhenIdIsNotNull() {
    AiChatModelConfig expected = new AiChatModelConfig(1L);
    assertEquals(expected, mapper.fromDto(1L));
  }

  @Test
  void testFromAddDto_ReturnsEntity_WhenAddDtoIsNotNull() {
    AddAiChatModelConfigDto dto = new AddAiChatModelConfigDto("name", "provider", "model", "stream",
        "key", 0.7, 100, 0.9, true);

    AiChatModelConfig entity = mapper.fromAddDto(dto);

    AiChatModelConfig expected = new AiChatModelConfig().setName("name").setProvider("provider")
        .setModelName("model").setStreamingModelName("stream").setApiKey("key").setTemperature(0.7)
        .setMaxTokens(100).setTopP(0.9).setIsDefault(true);

    assertEquals(expected, entity);
  }

  @Test
  void testFromUpdateDto_ReturnsEntity_WhenUpdateDtoIsNotNull() {
    UpdateAiChatModelConfigDto dto = new UpdateAiChatModelConfigDto(1L, "name", "provider", "model",
        "stream", "key", 0.7, 100, 0.9, true, 1);

    AiChatModelConfig entity = mapper.fromUpdateDto(dto);

    AiChatModelConfig expected
        = new AiChatModelConfig().setId(1L).setName("name").setProvider("provider")
            .setModelName("model").setStreamingModelName("stream").setApiKey("key")
            .setTemperature(0.7).setMaxTokens(100).setTopP(0.9).setIsDefault(true).setVersion(1);

    assertEquals(expected, entity);
  }

  @Test
  void testToDto_ReturnsDto_WhenEntityIsNotNull() {
    LocalDateTime now = LocalDateTime.now();
    AiChatModelConfig entity = new AiChatModelConfig(1L, "name", "provider", "model", "stream",
        "key", 0.7, 100, 0.9, true, now, now, 1);

    AiChatModelConfigDto dto = mapper.toDto(entity);

    AiChatModelConfigDto expected = new AiChatModelConfigDto(1L, "name", "provider", "model",
        "stream", 0.7, 100, 0.9, true, now, now, 1);

    assertEquals(expected, dto);
  }
}
