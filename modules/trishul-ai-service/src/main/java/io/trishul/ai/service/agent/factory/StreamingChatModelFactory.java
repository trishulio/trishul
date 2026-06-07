package io.trishul.ai.service.agent.factory;

import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import io.trishul.ai.chat.model.AiChatModelConfig;

public class StreamingChatModelFactory {

  public StreamingChatLanguageModel getModel(AiProvider provider, AiChatModelConfig config) {
    return switch (provider) {
      case OPENAI -> getOpenAiModel(config);
      case ANTHROPIC -> getAnthropicModel(config);
    };
  }

  public StreamingChatLanguageModel getOpenAiModel(AiChatModelConfig config) {
    if (config.getApiKey() == null || config.getApiKey().isEmpty()) {
      throw new IllegalArgumentException("OpenAI API Key must be provided");
    }
    return OpenAiStreamingChatModel.builder()
        .apiKey(config.getApiKey())
        .modelName(config.getStreamingModelName() != null ? config.getStreamingModelName()
            : config.getModelName())
        .temperature(config.getTemperature())
        .topP(config.getTopP())
        .build();
  }

  public StreamingChatLanguageModel getAnthropicModel(AiChatModelConfig config) {
    throw new UnsupportedOperationException("Anthropic is not yet implemented");
  }
}
