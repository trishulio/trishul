package sh.trishul.ai.tool.model;

public interface BaseAiTool<T extends BaseAiTool<T>> {
  String ATTR_NAME = "name";
  String ATTR_BEAN_NAME = "beanName";
  String ATTR_DESCRIPTION = "description";
  String ATTR_IS_ENABLED = "isEnabled";

  String getName();

  T setName(String name);

  String getBeanName();

  T setBeanName(String beanName);

  String getDescription();

  T setDescription(String description);

  Boolean getIsEnabled();

  T setIsEnabled(Boolean isEnabled);
}
