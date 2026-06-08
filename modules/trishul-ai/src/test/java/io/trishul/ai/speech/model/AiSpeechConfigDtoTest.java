package io.trishul.ai.speech.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class AiSpeechConfigDtoTest {

  @Test
  void testGettersAndSetters() {
    LocalDateTime now = LocalDateTime.now();
    AiSpeechConfigDto dto = new AiSpeechConfigDto(1L, "name", "provider", "tts-model", "stt-model",
        "voice", 1.0, true, now, now, 1);

    assertEquals(1L, dto.getId());
    assertEquals("name", dto.getName());
    assertEquals("provider", dto.getProvider());
    assertEquals("tts-model", dto.getTtsModelName());
    assertEquals("stt-model", dto.getSttModelName());
    assertEquals("voice", dto.getVoice());
    assertEquals(1.0, dto.getSpeed());
    assertTrue(dto.getIsDefault());
    assertEquals(now, dto.getCreatedAt());
    assertEquals(now, dto.getLastUpdated());
    assertEquals(1, dto.getVersion());

    dto.setId(2L).setName("new-name").setProvider("new-provider").setTtsModelName("new-tts")
        .setSttModelName("new-stt").setVoice("new-voice").setSpeed(0.5).setIsDefault(false)
        .setCreatedAt(now.plusDays(1)).setLastUpdated(now.plusDays(1)).setVersion(2);

    assertEquals(2L, dto.getId());
    assertEquals("new-name", dto.getName());
    assertEquals("new-provider", dto.getProvider());
    assertEquals("new-tts", dto.getTtsModelName());
    assertEquals("new-stt", dto.getSttModelName());
    assertEquals("new-voice", dto.getVoice());
    assertEquals(0.5, dto.getSpeed());
    assertTrue(!dto.getIsDefault());
    assertEquals(now.plusDays(1), dto.getCreatedAt());
    assertEquals(now.plusDays(1), dto.getLastUpdated());
    assertEquals(2, dto.getVersion());
  }
}
