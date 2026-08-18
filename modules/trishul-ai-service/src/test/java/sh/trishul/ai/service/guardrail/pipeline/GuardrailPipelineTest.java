package sh.trishul.ai.service.guardrail.pipeline;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GuardrailPipelineTest {

  @Test
  void testValidateInput_ReturnsInput() {
    GuardrailPipeline pipeline = new GuardrailPipeline();
    assertEquals("test-input", pipeline.validateInput("test-input"));
  }

  @Test
  void testValidateOutput_ReturnsOutput() {
    GuardrailPipeline pipeline = new GuardrailPipeline();
    assertEquals("test-output", pipeline.validateOutput("test-output"));
  }
}
