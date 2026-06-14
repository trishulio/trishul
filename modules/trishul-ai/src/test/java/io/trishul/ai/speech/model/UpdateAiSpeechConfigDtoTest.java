package io.trishul.ai.speech.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class UpdateAiSpeechConfigDtoTest {

  @Test
  void testGettersAndSetters() {
    UpdateAiSpeechConfigDto dto = new UpdateAiSpeechConfigDto(1L, "name", "provider", "tts-model",
        "stt-model", "voice", 1.0, true, 1);

    assertEquals(1L, dto.getId());
    assertEquals("name", dto.getName());
    assertEquals("provider", dto.getProvider());
    assertEquals("tts-model", dto.getTtsModelName());
    assertEquals("stt-model", dto.getSttModelName());
    assertEquals("voice", dto.getVoice());
    assertEquals(1.0, dto.getSpeed());
    assertTrue(dto.getIsDefault());
    assertEquals(1, dto.getVersion());

    dto.setId(2L).setName("new-name").setProvider("new-provider").setTtsModelName("new-tts")
        .setSttModelName("new-stt").setVoice("new-voice").setSpeed(0.5).setIsDefault(false)
        .setVersion(2);

    assertEquals(2L, dto.getId());
    assertEquals("new-name", dto.getName());
    assertEquals("new-provider", dto.getProvider());
    assertEquals("new-tts", dto.getTtsModelName());
    assertEquals("new-stt", dto.getSttModelName());
    assertEquals("new-voice", dto.getVoice());
    assertEquals(0.5, dto.getSpeed());
    assertTrue(!dto.getIsDefault());
    assertEquals(2, dto.getVersion());
  }
}
