package sh.trishul.ai.service.speech.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.TreeSet;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

class TextToSpeechStreamingServiceTest {

  @Test
  void testStreamChunk_ReturnsEmptyByteArray() {
    TextToSpeechStreamingService service = new TextToSpeechStreamingService();
    assertArrayEquals(new byte[0], service.streamChunk("hello"));
  }

  @Test
  void testSearch_ReturnsEmptyPage() {
    TextToSpeechStreamingService service = new TextToSpeechStreamingService();
    Page<Object> result = service.search("query", new TreeSet<>(), true, 0, 10);
    assertEquals(Page.empty(), result);
  }
}
