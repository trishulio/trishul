package io.trishul.ai.memory.model;

public interface BaseAiChatMemoryConfig<T extends BaseAiChatMemoryConfig<T>> {
  final String ATTR_NAME = "name";
  final String ATTR_STRATEGY = "strategy";
  final String ATTR_MAX_MESSAGES = "maxMessages";
  final String ATTR_MAX_TOKENS = "maxTokens";
  final String ATTR_TTL_MINUTES = "ttlMinutes";
  final String ATTR_IS_DEFAULT = "isDefault";

  String getName();

  T setName(String name);

  String getStrategy();

  T setStrategy(String strategy);

  Integer getMaxMessages();

  T setMaxMessages(Integer maxMessages);

  Integer getMaxTokens();

  T setMaxTokens(Integer maxTokens);

  Integer getTtlMinutes();

  T setTtlMinutes(Integer ttlMinutes);

  Boolean getIsDefault();

  T setIsDefault(Boolean isDefault);
}
