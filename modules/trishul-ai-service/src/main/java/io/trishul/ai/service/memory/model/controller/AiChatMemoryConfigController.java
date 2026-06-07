package io.trishul.ai.service.memory.model.controller;

import io.trishul.ai.service.memory.model.service.AiChatMemoryConfigService;



import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ai/chat-memory-configs")
public class AiChatMemoryConfigController {

  public AiChatMemoryConfigController(AiChatMemoryConfigService service) {
    // Empty
  }
}
