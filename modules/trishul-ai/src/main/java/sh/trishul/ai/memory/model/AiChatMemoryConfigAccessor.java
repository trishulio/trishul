package sh.trishul.ai.memory.model;

public interface AiChatMemoryConfigAccessor<T extends AiChatMemoryConfigAccessor<T>> {
  String ATTR_CHAT_MEMORY_CONFIG = "chatMemoryConfig";

  AiChatMemoryConfig getChatMemoryConfig();

  T setChatMemoryConfig(AiChatMemoryConfig chatMemoryConfig);
}
