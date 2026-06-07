package io.trishul.ai.service.agent.model.controller;

import io.trishul.ai.service.agent.model.service.AiAgentConfigService;



import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ai/agent-configs")
public class AiAgentConfigController {

  public AiAgentConfigController(AiAgentConfigService service) {
    // Empty
  }
}
