package sh.trishul.ai.service.speech.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sh.trishul.ai.service.speech.service.WebRTCSpeechService;
import sh.trishul.model.base.dto.BaseDto;
import sh.trishul.repo.jpa.repository.model.dto.PageDto;

class AiSpeechControllerTest {

  private AiSpeechController controller;
  private WebRTCSpeechService mockSpeechService;

  @BeforeEach
  void setUp() {
    mockSpeechService = mock(WebRTCSpeechService.class);
    controller = new AiSpeechController(mockSpeechService);
  }

  @Test
  void testTextToSpeech_ReturnsAudioData() {
    TtsRequest request = new TtsRequest();
    request.setText("Hello");
    request.setVoice("alloy");

    byte[] expectedAudio = new byte[] {1, 2, 3};
    when(mockSpeechService.textToSpeech("Hello", "alloy")).thenReturn(expectedAudio);

    ResponseEntity<byte[]> response = controller.textToSpeech(request);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("audio/mpeg", response.getHeaders().getContentType().toString());
    assertEquals(expectedAudio, response.getBody());
  }

  @Test
  void testSpeechToText_ReturnsText() {
    byte[] audioData = new byte[] {1, 2, 3};
    when(mockSpeechService.speechToText(audioData)).thenReturn("Transcribed Text");

    String result = controller.speechToText(audioData);

    assertEquals("Transcribed Text", result);
  }

  @Test
  void testSearch_ReturnsPageDto() {
    PageDto<BaseDto> result = controller.search("test", 0, 100, new TreeSet<>(Set.of("id")), true);
    PageDto<BaseDto> expected = new PageDto<>(new ArrayList<>(), 0, 0);
    assertEquals(expected, result);
  }
}
