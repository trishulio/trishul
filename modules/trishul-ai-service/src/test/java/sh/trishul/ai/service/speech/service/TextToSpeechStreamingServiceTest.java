package sh.trishul.ai.service.speech.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class TextToSpeechStreamingServiceTest {

  @Test
  void testStreamChunk_ReturnsEmptyByteArray() {
    TextToSpeechStreamingService service = new TextToSpeechStreamingService();
    assertArrayEquals(new byte[0], service.streamChunk("hello"));
  }
}
