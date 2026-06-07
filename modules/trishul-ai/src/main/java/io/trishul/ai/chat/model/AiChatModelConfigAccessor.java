package io.trishul.ai.chat.model;

public interface AiChatModelConfigAccessor<T extends AiChatModelConfigAccessor<T>> {
  final String ATTR_CHAT_MODEL_CONFIG = "chatModelConfig";

  AiChatModelConfig getChatModelConfig();

  T setChatModelConfig(AiChatModelConfig chatModelConfig);
}
