package sh.trishul.ai.guardrail.model;

public interface AiGuardrailAccessor<T extends AiGuardrailAccessor<T>> {
  String ATTR_GUARDRAIL = "guardrail";

  AiGuardrail getGuardrail();

  T setGuardrail(AiGuardrail guardrail);
}
