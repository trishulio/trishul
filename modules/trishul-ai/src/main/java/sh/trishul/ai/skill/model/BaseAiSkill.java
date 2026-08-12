package sh.trishul.ai.skill.model;

public interface BaseAiSkill<T extends BaseAiSkill<T>> {
  final String ATTR_NAME = "name";
  final String ATTR_DESCRIPTION = "description";
  final String ATTR_SYSTEM_PROMPT = "systemPrompt";
  final String ATTR_IS_ENABLED = "isEnabled";

  String getName();

  T setName(String name);

  String getDescription();

  T setDescription(String description);

  String getSystemPrompt();

  T setSystemPrompt(String systemPrompt);

  Boolean getIsEnabled();

  T setIsEnabled(Boolean isEnabled);
}
