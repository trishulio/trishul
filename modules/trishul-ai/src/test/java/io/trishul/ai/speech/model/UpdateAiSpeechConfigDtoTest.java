package io.trishul.ai.speech.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
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

  @Test
  void testAccessId() throws Exception {
    UpdateAiSpeechConfigDto accessor = new UpdateAiSpeechConfigDto();
    assertSame(accessor, accessor.setId(123L));
    assertEquals(123L, accessor.getId());
  }

  @Test
  void testAccessName() throws Exception {
    UpdateAiSpeechConfigDto accessor = new UpdateAiSpeechConfigDto();
    assertSame(accessor, accessor.setName("testString"));
    assertEquals("testString", accessor.getName());
  }

  @Test
  void testAccessProvider() throws Exception {
    UpdateAiSpeechConfigDto accessor = new UpdateAiSpeechConfigDto();
    assertSame(accessor, accessor.setProvider("testString"));
    assertEquals("testString", accessor.getProvider());
  }

  @Test
  void testAccessTtsModelName() throws Exception {
    UpdateAiSpeechConfigDto accessor = new UpdateAiSpeechConfigDto();
    assertSame(accessor, accessor.setTtsModelName("testString"));
    assertEquals("testString", accessor.getTtsModelName());
  }

  @Test
  void testAccessSttModelName() throws Exception {
    UpdateAiSpeechConfigDto accessor = new UpdateAiSpeechConfigDto();
    assertSame(accessor, accessor.setSttModelName("testString"));
    assertEquals("testString", accessor.getSttModelName());
  }

  @Test
  void testAccessVoice() throws Exception {
    UpdateAiSpeechConfigDto accessor = new UpdateAiSpeechConfigDto();
    assertSame(accessor, accessor.setVoice("testString"));
    assertEquals("testString", accessor.getVoice());
  }

  @Test
  void testAccessSpeed() throws Exception {
    UpdateAiSpeechConfigDto accessor = new UpdateAiSpeechConfigDto();
    assertSame(accessor, accessor.setSpeed(123.45));
    assertEquals(123.45, accessor.getSpeed());
  }

  @Test
  void testAccessIsDefault() throws Exception {
    UpdateAiSpeechConfigDto accessor = new UpdateAiSpeechConfigDto();
    assertSame(accessor, accessor.setIsDefault(true));
    assertEquals(true, accessor.getIsDefault());
  }

  @Test
  void testAccessVersion() throws Exception {
    UpdateAiSpeechConfigDto accessor = new UpdateAiSpeechConfigDto();
    assertSame(accessor, accessor.setVersion(123));
    assertEquals(123, accessor.getVersion());
  }

}
