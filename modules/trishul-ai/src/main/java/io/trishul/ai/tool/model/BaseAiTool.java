package io.trishul.ai.tool.model;

public interface BaseAiTool<T extends BaseAiTool<T>> {
  final String ATTR_NAME = "name";
  final String ATTR_BEAN_NAME = "beanName";
  final String ATTR_DESCRIPTION = "description";
  final String ATTR_IS_ENABLED = "isEnabled";

  String getName();

  T setName(String name);

  String getBeanName();

  T setBeanName(String beanName);

  String getDescription();

  T setDescription(String description);

  Boolean getIsEnabled();

  T setIsEnabled(Boolean isEnabled);
}
