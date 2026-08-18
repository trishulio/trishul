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
}
