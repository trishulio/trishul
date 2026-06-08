package io.trishul.ai.speech.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class AddAiSpeechConfigDtoTest {

  @Test
  void testGettersAndSetters() {
    AddAiSpeechConfigDto dto = new AddAiSpeechConfigDto("name", "provider", "tts-model",
        "stt-model", "voice", 1.0, true);

    assertEquals("name", dto.getName());
    assertEquals("provider", dto.getProvider());
    assertEquals("tts-model", dto.getTtsModelName());
    assertEquals("stt-model", dto.getSttModelName());
    assertEquals("voice", dto.getVoice());
    assertEquals(1.0, dto.getSpeed());
    assertTrue(dto.getIsDefault());

    dto.setName("new-name").setProvider("new-provider").setTtsModelName("new-tts")
        .setSttModelName("new-stt").setVoice("new-voice").setSpeed(0.5).setIsDefault(false);

    assertEquals("new-name", dto.getName());
    assertEquals("new-provider", dto.getProvider());
    assertEquals("new-tts", dto.getTtsModelName());
    assertEquals("new-stt", dto.getSttModelName());
    assertEquals("new-voice", dto.getVoice());
    assertEquals(0.5, dto.getSpeed());
    assertTrue(!dto.getIsDefault());
  }
}
