package sh.trishul.ai.speech.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
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

  @Test
  void testAccessName() throws Exception {
    AddAiSpeechConfigDto accessor = new AddAiSpeechConfigDto();
    assertSame(accessor, accessor.setName("testString"));
    assertEquals("testString", accessor.getName());
  }

  @Test
  void testAccessProvider() throws Exception {
    AddAiSpeechConfigDto accessor = new AddAiSpeechConfigDto();
    assertSame(accessor, accessor.setProvider("testString"));
    assertEquals("testString", accessor.getProvider());
  }

  @Test
  void testAccessTtsModelName() throws Exception {
    AddAiSpeechConfigDto accessor = new AddAiSpeechConfigDto();
    assertSame(accessor, accessor.setTtsModelName("testString"));
    assertEquals("testString", accessor.getTtsModelName());
  }

  @Test
  void testAccessSttModelName() throws Exception {
    AddAiSpeechConfigDto accessor = new AddAiSpeechConfigDto();
    assertSame(accessor, accessor.setSttModelName("testString"));
    assertEquals("testString", accessor.getSttModelName());
  }

  @Test
  void testAccessVoice() throws Exception {
    AddAiSpeechConfigDto accessor = new AddAiSpeechConfigDto();
    assertSame(accessor, accessor.setVoice("testString"));
    assertEquals("testString", accessor.getVoice());
  }

  @Test
  void testAccessSpeed() throws Exception {
    AddAiSpeechConfigDto accessor = new AddAiSpeechConfigDto();
    assertSame(accessor, accessor.setSpeed(123.45));
    assertEquals(123.45, accessor.getSpeed());
  }

  @Test
  void testAccessIsDefault() throws Exception {
    AddAiSpeechConfigDto accessor = new AddAiSpeechConfigDto();
    assertSame(accessor, accessor.setIsDefault(true));
    assertEquals(true, accessor.getIsDefault());
  }

}
