package io.trishul.ai.guardrail.model;

public interface BaseAiGuardrail<T extends BaseAiGuardrail<T>> {
  final String ATTR_NAME = "name";
  final String ATTR_TYPE = "type";
  final String ATTR_STRATEGY = "strategy";
  final String ATTR_CONFIGURATION = "configuration";
  final String ATTR_PRIORITY = "priority";
  final String ATTR_IS_ENABLED = "isEnabled";

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
