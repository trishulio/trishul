package io.trishul.ai.speech.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class AiSpeechConfigTest {

  @Test
  void testGettersAndSetters() {
    LocalDateTime now = LocalDateTime.now();
    AiSpeechConfig config = new AiSpeechConfig(1L, "name", "provider", "tts-model", "stt-model",
        "voice", 1.0, true, now, now, 1);

    assertEquals(1L, config.getId());
    assertEquals("name", config.getName());
    assertEquals("provider", config.getProvider());
    assertEquals("tts-model", config.getTtsModelName());
    assertEquals("stt-model", config.getSttModelName());
    assertEquals("voice", config.getVoice());
    assertEquals(1.0, config.getSpeed());
    assertTrue(config.getIsDefault());
    assertEquals(now, config.getCreatedAt());
    assertEquals(now, config.getLastUpdated());
    assertEquals(1, config.getVersion());

    config.setId(2L).setName("new-name").setProvider("new-provider").setTtsModelName("new-tts")
        .setSttModelName("new-stt").setVoice("new-voice").setSpeed(0.5).setIsDefault(false)
        .setCreatedAt(now.plusDays(1)).setLastUpdated(now.plusDays(1)).setVersion(2);

    assertEquals(2L, config.getId());
    assertEquals("new-name", config.getName());
    assertEquals("new-provider", config.getProvider());
    assertEquals("new-tts", config.getTtsModelName());
    assertEquals("new-stt", config.getSttModelName());
    assertEquals("new-voice", config.getVoice());
    assertEquals(0.5, config.getSpeed());
    assertTrue(!config.getIsDefault());
    assertEquals(now.plusDays(1), config.getCreatedAt());
    assertEquals(now.plusDays(1), config.getLastUpdated());
    assertEquals(2, config.getVersion());
  }
}
