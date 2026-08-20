package sh.trishul.ai.guardrail.model;

public interface BaseAiGuardrail<T extends BaseAiGuardrail<T>> {
  String ATTR_NAME = "name";
  String ATTR_TYPE = "type";
  String ATTR_STRATEGY = "strategy";
  String ATTR_CONFIGURATION = "configuration";
  String ATTR_PRIORITY = "priority";
  String ATTR_IS_ENABLED = "isEnabled";

  String getName();

  T setName(String name);

  AiGuardrailType getType();

  T setType(AiGuardrailType type);

  String getStrategy();

  T setStrategy(String strategy);

  String getConfiguration();

  T setConfiguration(String configuration);

  Integer getPriority();

  T setPriority(Integer priority);

  Boolean getIsEnabled();

  T setIsEnabled(Boolean isEnabled);
}
