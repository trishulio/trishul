package io.trishul.ai.service.agent.factory;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import io.trishul.ai.chat.model.AiChatModelConfig;

/**
 * Factory that builds synchronous ChatLanguageModel instances for use with AiServices. Complements
 * StreamingChatModelFactory which builds StreamingChatLanguageModel instances.
 */
public class ChatModelFactory {

  public ChatLanguageModel getModel(AiProvider provider, AiChatModelConfig config) {
    if (provider == AiProvider.GITHUB_COPILOT) {
      return getGithubCopilotModel(config);
    }
    if (provider == AiProvider.OPENAI) {
      return getOpenAiModel(config);
    }
    if (provider == AiProvider.ANTHROPIC) {
      throw getAnthropicModel(config);
    }
    throw new IllegalArgumentException("Unsupported AI Provider: " + provider);
  }

  public ChatLanguageModel getGithubCopilotModel(AiChatModelConfig config) {
    if (config.getApiKey() == null || config.getApiKey().isEmpty()) {
      throw new IllegalArgumentException("GitHub Copilot API Key must be provided");
    }
    return OpenAiChatModel.builder().baseUrl("https://models.inference.ai.azure.com")
        .apiKey(config.getApiKey()).modelName(config.getModelName())
        .temperature(config.getTemperature()).topP(config.getTopP()).build();
  }

  public ChatLanguageModel getOpenAiModel(AiChatModelConfig config) {
    if (config.getApiKey() == null || config.getApiKey().isEmpty()) {
      throw new IllegalArgumentException("OpenAI API Key must be provided");
    }
    return OpenAiChatModel.builder().apiKey(config.getApiKey()).modelName(config.getModelName())
        .temperature(config.getTemperature()).topP(config.getTopP()).build();
  }

  public RuntimeException getAnthropicModel(AiChatModelConfig config) {
    return new UnsupportedOperationException("Anthropic is not yet implemented");
  }
}
