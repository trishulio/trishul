package sh.trishul.ai.speech.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AiSpeechConfigMapperTest {
  private AiSpeechConfigMapper mapper;

  @BeforeEach
  void init() {
    mapper = AiSpeechConfigMapper.INSTANCE;
  }

  @Test
  void testFromPojo_ReturnsNull_WhenIdIsNull() {
    assertNull(mapper.fromDto(null));
  }

  @Test
  void testFromPojo_ReturnsPojo_WhenIdIsNotNull() {
    AiSpeechConfig expected = new AiSpeechConfig(1L);
    assertEquals(expected, mapper.fromDto(1L));
  }

  @Test
  void testFromAddDto_ReturnsEntity_WhenAddDtoIsNotNull() {
    AddAiSpeechConfigDto dto = new AddAiSpeechConfigDto("name", "provider", "tts-model",
        "stt-model", "voice", 1.0, true);

    AiSpeechConfig entity = mapper.fromAddDto(dto);

    AiSpeechConfig expected
        = new AiSpeechConfig().setName("name").setProvider("provider").setTtsModelName("tts-model")
            .setSttModelName("stt-model").setVoice("voice").setSpeed(1.0).setIsDefault(true);

    assertEquals(expected, entity);
  }

  @Test
  void testFromUpdateDto_ReturnsEntity_WhenUpdateDtoIsNotNull() {
    UpdateAiSpeechConfigDto dto = new UpdateAiSpeechConfigDto(1L, "name", "provider", "tts-model",
        "stt-model", "voice", 1.0, true, 1);

    AiSpeechConfig entity = mapper.fromUpdateDto(dto);

    AiSpeechConfig expected = new AiSpeechConfig().setId(1L).setName("name").setProvider("provider")
        .setTtsModelName("tts-model").setSttModelName("stt-model").setVoice("voice").setSpeed(1.0)
        .setIsDefault(true).setVersion(1);

    assertEquals(expected, entity);
  }

  @Test
  void testToDto_ReturnsDto_WhenEntityIsNotNull() {
    LocalDateTime now = LocalDateTime.now();
    AiSpeechConfig entity = new AiSpeechConfig(1L, "name", "provider", "tts-model", "stt-model",
        "voice", 1.0, true, now, now, 1);

    AiSpeechConfigDto dto = mapper.toDto(entity);

    AiSpeechConfigDto expected = new AiSpeechConfigDto(1L, "name", "provider", "tts-model",
        "stt-model", "voice", 1.0, true, now, now, 1);

    assertEquals(expected, dto);
  }
}
