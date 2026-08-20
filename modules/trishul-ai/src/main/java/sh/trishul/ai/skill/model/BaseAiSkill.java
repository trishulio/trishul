package sh.trishul.ai.skill.model;

public interface BaseAiSkill<T extends BaseAiSkill<T>> {
  String ATTR_NAME = "name";
  String ATTR_DESCRIPTION = "description";
  String ATTR_SYSTEM_PROMPT = "systemPrompt";
  String ATTR_IS_ENABLED = "isEnabled";

  String getName();

  T setName(String name);

  String getDescription();

  T setDescription(String description);

  String getSystemPrompt();

  T setSystemPrompt(String systemPrompt);

  Boolean getIsEnabled();

  T setIsEnabled(Boolean isEnabled);
}
