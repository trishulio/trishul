package sh.trishul.ai.service.agent.factory;

import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import sh.trishul.ai.chat.model.AiChatModelConfig;

public class StreamingChatModelFactory {

  public StreamingChatModel getModel(AiProvider provider, AiChatModelConfig config) {
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

  public StreamingChatModel getGithubCopilotModel(AiChatModelConfig config) {
    if (config.getApiKey() == null || config.getApiKey().isEmpty()) {
      throw new IllegalArgumentException("GitHub Copilot API Key must be provided");
    }
    return OpenAiStreamingChatModel.builder().baseUrl("https://models.inference.ai.azure.com")
        .apiKey(config.getApiKey()).modelName("Gemini 3.1 Pro Preview")
        .temperature(config.getTemperature()).topP(config.getTopP()).build();
  }

  public StreamingChatModel getOpenRouterModel(AiChatModelConfig config) {
    if (config.getApiKey() == null || config.getApiKey().isEmpty()) {
      throw new IllegalArgumentException("OpenRouter API Key must be provided");
    }
    return OpenAiStreamingChatModel.builder().baseUrl("https://openrouter.ai/api/v1")
        .apiKey(config.getApiKey())
        .modelName(config.getStreamingModelName() != null ? config.getStreamingModelName()
            : config.getModelName())
        .temperature(config.getTemperature()).topP(config.getTopP()).build();
  }

  public StreamingChatModel getOpenAiModel(AiChatModelConfig config) {
    if (config.getApiKey() == null || config.getApiKey().isEmpty()) {
      throw new IllegalArgumentException("OpenAI API Key must be provided");
    }
    return OpenAiStreamingChatModel.builder().apiKey(config.getApiKey())
        .modelName(config.getStreamingModelName() != null ? config.getStreamingModelName()
            : config.getModelName())
        .temperature(config.getTemperature()).topP(config.getTopP()).build();
  }

  public RuntimeException getAnthropicModel(AiChatModelConfig config) {
    return new UnsupportedOperationException("Anthropic is not yet implemented");
  }
}
