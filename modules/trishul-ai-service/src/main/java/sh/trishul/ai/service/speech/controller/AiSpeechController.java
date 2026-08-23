package sh.trishul.ai.service.speech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sh.trishul.ai.service.speech.service.WebRTCSpeechService;

@RestController
@RequestMapping("/api/v1/ai/speech")
public class AiSpeechController {

  private final WebRTCSpeechService speechService;

  @Autowired
  public AiSpeechController(WebRTCSpeechService speechService) {
    this.speechService = speechService;
  }

  @PostMapping(value = "/tts", produces = "audio/mpeg")
  public ResponseEntity<byte[]> textToSpeech(@RequestBody TtsRequest request) {
    byte[] audio = speechService.textToSpeech(request.getText(), request.getVoice());
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.valueOf("audio/mpeg"));
    return new ResponseEntity<>(audio, headers, HttpStatus.OK);
  }

  @PostMapping(value = "/stt", consumes = "audio/mpeg", produces = MediaType.TEXT_PLAIN_VALUE)
  public String speechToText(@RequestBody byte[] audioData) {
    return speechService.speechToText(audioData);
  }

  @org.springframework.web.bind.annotation.GetMapping(value = "/search", consumes = MediaType.ALL_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public sh.trishul.repo.jpa.repository.model.dto.PageDto<Object> search(
      @org.springframework.web.bind.annotation.RequestParam(name = "q", required = false) String query,
      @org.springframework.web.bind.annotation.RequestParam(name = "page", defaultValue = "0") int page,
      @org.springframework.web.bind.annotation.RequestParam(name = "size", defaultValue = "100") int size,
      @org.springframework.web.bind.annotation.RequestParam(name = "sort",
          defaultValue = "id") java.util.SortedSet<String> sort,
      @org.springframework.web.bind.annotation.RequestParam(name = "order_asc",
          defaultValue = "true") boolean orderAscending) {
    return new sh.trishul.repo.jpa.repository.model.dto.PageDto<>(new java.util.ArrayList<>(), 0, 0);
  }
}
