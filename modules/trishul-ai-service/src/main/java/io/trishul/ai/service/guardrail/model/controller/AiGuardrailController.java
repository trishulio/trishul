package io.trishul.ai.service.guardrail.model.controller;

import io.trishul.ai.service.guardrail.model.service.AiGuardrailService;



import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ai/guardrails")
public class AiGuardrailController {

  public AiGuardrailController(AiGuardrailService service) {
    // Empty
  }
}
