package io.trishul.ai.service.chat.model.controller;

import io.trishul.ai.service.chat.model.service.AiChatModelConfigService;



import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ai/chat-model-configs")
public class AiChatModelConfigController {

  public AiChatModelConfigController(AiChatModelConfigService service) {
    // Empty
  }
}
