package io.trishul.ai.service.speech.model.controller;

import io.trishul.ai.service.speech.model.service.AiSpeechConfigService;



import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ai/speech-configs")
public class AiSpeechConfigController {

  public AiSpeechConfigController(AiSpeechConfigService service) {
    // Empty
  }
}
