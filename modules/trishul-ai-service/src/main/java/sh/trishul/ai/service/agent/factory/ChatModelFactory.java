package sh.trishul.ai.service.agent.factory;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import sh.trishul.ai.chat.model.AiChatModelConfig;

/**
 * Factory that builds synchronous ChatModel instances for use with AiServices. Complements
 * StreamingChatModelFactory which builds StreamingChatModel instances.
 */
public class ChatModelFactory {

  public ChatModel getModel(AiProvider provider, AiChatModelConfig config) {
    if (provider == AiProvider.GITHUB_COPILOT) {
      return getGithubCopilotModel(config);
    }
    if (provider == AiProvider.OPENAI) {
      return getOpenAiModel(config);
    }
    if (provider == AiProvider.OPENROUTER) {
      return getOpenRouterModel(config);
    }
    if (provider == AiProvider.ANTHROPIC) {
      throw getAnthropicModel(config);
    }
    throw new IllegalArgumentException("Unsupported AI Provider: " + provider);
  }

  public ChatModel getGithubCopilotModel(AiChatModelConfig config) {
    if (config.getApiKey() == null || config.getApiKey().isEmpty()) {
      throw new IllegalArgumentException("GitHub Copilot API Key must be provided");
    }
    return OpenAiChatModel.builder().baseUrl("https://models.inference.ai.azure.com")
        .apiKey(config.getApiKey()).modelName(config.getModelName())
        .temperature(config.getTemperature()).topP(config.getTopP()).build();
  }

  public ChatModel getOpenRouterModel(AiChatModelConfig config) {
    if (config.getApiKey() == null || config.getApiKey().isEmpty()) {
      throw new IllegalArgumentException("OpenRouter API Key must be provided");
    }
    return OpenAiChatModel.builder().baseUrl("https://openrouter.ai/api/v1")
        .apiKey(config.getApiKey()).modelName(config.getModelName())
        .temperature(config.getTemperature()).topP(config.getTopP()).build();
  }

  public ChatModel getOpenAiModel(AiChatModelConfig config) {
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
