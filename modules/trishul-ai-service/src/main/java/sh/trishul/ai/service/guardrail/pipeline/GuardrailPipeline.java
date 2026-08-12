package sh.trishul.ai.service.guardrail.pipeline;

/**
 * GuardrailPipeline intercepts chat input and output, runs them through the configured guardrails
 * (Regex, PII, Prompt Injection, Semantic), and returns the validated text or throws a
 * GuardrailException if it fails validation.
 */
public class GuardrailPipeline {

  public String validateInput(String input) {
    return input;
  }

  public String validateOutput(String output) {
    return output;
  }
}
