package sh.trishul.ai.chat.model;

public interface AiChatModelConfigAccessor<T extends AiChatModelConfigAccessor<T>> {
  String ATTR_CHAT_MODEL_CONFIG = "chatModelConfig";

  AiChatModelConfig getChatModelConfig();

  T setChatModelConfig(AiChatModelConfig chatModelConfig);
}
