package io.trishul.ai.service.session.model.controller;

import io.trishul.ai.service.session.model.service.AiChatSessionService;



import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ai/chat-sessions")
public class AiChatSessionController {

  public AiChatSessionController(AiChatSessionService service) {
    // Empty
  }
}
