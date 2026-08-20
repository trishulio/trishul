package sh.trishul.ai.memory.model;

public interface BaseAiChatMemoryConfig<T extends BaseAiChatMemoryConfig<T>> {
  String ATTR_NAME = "name";
  String ATTR_STRATEGY = "strategy";
  String ATTR_MAX_MESSAGES = "maxMessages";
  String ATTR_MAX_TOKENS = "maxTokens";
  String ATTR_TTL_MINUTES = "ttlMinutes";
  String ATTR_IS_DEFAULT = "isDefault";

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
