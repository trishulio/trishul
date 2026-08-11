package io.trishul.ai.speech.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
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

  @Test
  void testAccessId() throws Exception {
    AiSpeechConfigDto accessor = new AiSpeechConfigDto();
    assertSame(accessor, accessor.setId(123L));
    assertEquals(123L, accessor.getId());
  }

  @Test
  void testAccessName() throws Exception {
    AiSpeechConfigDto accessor = new AiSpeechConfigDto();
    assertSame(accessor, accessor.setName("testString"));
    assertEquals("testString", accessor.getName());
  }

  @Test
  void testAccessProvider() throws Exception {
    AiSpeechConfigDto accessor = new AiSpeechConfigDto();
    assertSame(accessor, accessor.setProvider("testString"));
    assertEquals("testString", accessor.getProvider());
  }

  @Test
  void testAccessTtsModelName() throws Exception {
    AiSpeechConfigDto accessor = new AiSpeechConfigDto();
    assertSame(accessor, accessor.setTtsModelName("testString"));
    assertEquals("testString", accessor.getTtsModelName());
  }

  @Test
  void testAccessSttModelName() throws Exception {
    AiSpeechConfigDto accessor = new AiSpeechConfigDto();
    assertSame(accessor, accessor.setSttModelName("testString"));
    assertEquals("testString", accessor.getSttModelName());
  }

  @Test
  void testAccessVoice() throws Exception {
    AiSpeechConfigDto accessor = new AiSpeechConfigDto();
    assertSame(accessor, accessor.setVoice("testString"));
    assertEquals("testString", accessor.getVoice());
  }

  @Test
  void testAccessSpeed() throws Exception {
    AiSpeechConfigDto accessor = new AiSpeechConfigDto();
    assertSame(accessor, accessor.setSpeed(123.45));
    assertEquals(123.45, accessor.getSpeed());
  }

  @Test
  void testAccessIsDefault() throws Exception {
    AiSpeechConfigDto accessor = new AiSpeechConfigDto();
    assertSame(accessor, accessor.setIsDefault(true));
    assertEquals(true, accessor.getIsDefault());
  }

  @Test
  void testAccessCreatedAt() throws Exception {
    AiSpeechConfigDto accessor = new AiSpeechConfigDto();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setCreatedAt(value));
    assertEquals(value, accessor.getCreatedAt());
  }

  @Test
  void testAccessLastUpdated() throws Exception {
    AiSpeechConfigDto accessor = new AiSpeechConfigDto();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setLastUpdated(value));
    assertEquals(value, accessor.getLastUpdated());
  }

  @Test
  void testAccessVersion() throws Exception {
    AiSpeechConfigDto accessor = new AiSpeechConfigDto();
    assertSame(accessor, accessor.setVersion(123));
    assertEquals(123, accessor.getVersion());
  }

}
