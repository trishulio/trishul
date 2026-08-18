package sh.trishul.ai.service.agent.factory;

import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import sh.trishul.ai.chat.model.AiChatModelConfig;

public class StreamingChatModelFactory {
  private final String copilotBaseUrl;
  private final String openRouterBaseUrl;
  private final String openAiBaseUrl;

  public StreamingChatModelFactory() {
    this("https://models.inference.ai.azure.com", "https://openrouter.ai/api/v1", null);
  }

  public StreamingChatModelFactory(String copilotBaseUrl, String openRouterBaseUrl,
      String openAiBaseUrl) {
    this.copilotBaseUrl = copilotBaseUrl;
    this.openRouterBaseUrl = openRouterBaseUrl;
    this.openAiBaseUrl = openAiBaseUrl;
  }

  public StreamingChatLanguageModel getModel(AiProvider provider, AiChatModelConfig config) {
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

  public StreamingChatLanguageModel getGithubCopilotModel(AiChatModelConfig config) {
    if (config.getApiKey() == null || config.getApiKey().isEmpty()) {
      throw new IllegalArgumentException("GitHub Copilot API Key must be provided");
    }
    var builder = OpenAiStreamingChatModel.builder();
    if (this.copilotBaseUrl != null && !this.copilotBaseUrl.isEmpty()) {
      builder.baseUrl(this.copilotBaseUrl);
    }
    return builder.apiKey(config.getApiKey()).modelName("Gemini 3.1 Pro Preview")
        .temperature(config.getTemperature()).topP(config.getTopP()).build();
  }

  public StreamingChatLanguageModel getOpenRouterModel(AiChatModelConfig config) {
    if (config.getApiKey() == null || config.getApiKey().isEmpty()) {
      throw new IllegalArgumentException("OpenRouter API Key must be provided");
    }
    var builder = OpenAiStreamingChatModel.builder();
    if (this.openRouterBaseUrl != null && !this.openRouterBaseUrl.isEmpty()) {
      builder.baseUrl(this.openRouterBaseUrl);
    }
    return builder.apiKey(config.getApiKey())
        .modelName(config.getStreamingModelName() != null ? config.getStreamingModelName()
            : config.getModelName())
        .temperature(config.getTemperature()).topP(config.getTopP()).build();
  }

  public StreamingChatLanguageModel getOpenAiModel(AiChatModelConfig config) {
    if (config.getApiKey() == null || config.getApiKey().isEmpty()) {
      throw new IllegalArgumentException("OpenAI API Key must be provided");
    }
    var builder = OpenAiStreamingChatModel.builder();
    if (this.openAiBaseUrl != null && !this.openAiBaseUrl.isEmpty()) {
      builder.baseUrl(this.openAiBaseUrl);
    }
    return builder.apiKey(config.getApiKey())
        .modelName(config.getStreamingModelName() != null ? config.getStreamingModelName()
            : config.getModelName())
        .temperature(config.getTemperature()).topP(config.getTopP()).build();
  }

  public RuntimeException getAnthropicModel(AiChatModelConfig config) {
    return new UnsupportedOperationException("Anthropic is not yet implemented");
  }
}
