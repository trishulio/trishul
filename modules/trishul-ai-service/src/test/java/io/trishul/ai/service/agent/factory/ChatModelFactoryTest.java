package io.trishul.ai.service.agent.factory;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import io.trishul.ai.chat.model.AiChatModelConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChatModelFactoryTest {

  private ChatModelFactory factory;

  @BeforeEach
  void setUp() {
    factory = new ChatModelFactory();
  }

  @Test
  void testGetModel_ReturnsOpenAiModel_WhenProviderIsOpenAi() {
    AiChatModelConfig config = new AiChatModelConfig();
    config.setProvider("openai");
    config.setApiKey("test-key");
    config.setModelName("gpt-4");

    ChatLanguageModel model = factory.getModel(AiProvider.OPENAI, config);

    assertNotNull(model);
    assert (model instanceof OpenAiChatModel);
  }

  @Test
  void testGetOpenAiModel_ThrowsException_WhenApiKeyIsMissing() {
    AiChatModelConfig config = new AiChatModelConfig();
    config.setProvider("openai");

    assertThrows(IllegalArgumentException.class, () -> factory.getOpenAiModel(config));
  }

  @Test
  void testGetOpenAiModel_ThrowsException_WhenApiKeyIsEmpty() {
    AiChatModelConfig config = new AiChatModelConfig();
    config.setProvider("openai");
    config.setApiKey("");

    assertThrows(IllegalArgumentException.class, () -> factory.getOpenAiModel(config));
  }

  @Test
  void testGetModel_ReturnsGithubCopilotModel_WhenProviderIsGithubCopilot() {
    AiChatModelConfig config = new AiChatModelConfig();
    config.setProvider("github_copilot");
    config.setApiKey("test-copilot-key");
    config.setModelName("gpt-4");

    ChatLanguageModel model = factory.getModel(AiProvider.GITHUB_COPILOT, config);

    assertNotNull(model);
    assert (model instanceof OpenAiChatModel);
  }

  @Test
  void testGetGithubCopilotModel_ThrowsException_WhenApiKeyIsMissing() {
    AiChatModelConfig config = new AiChatModelConfig();
    assertThrows(IllegalArgumentException.class, () -> factory.getGithubCopilotModel(config));
  }

  @Test
  void testGetGithubCopilotModel_ThrowsException_WhenApiKeyIsEmpty() {
    AiChatModelConfig config = new AiChatModelConfig();
    config.setApiKey("");
    assertThrows(IllegalArgumentException.class, () -> factory.getGithubCopilotModel(config));
  }

  @Test
  void testGetAnthropicModel_ThrowsException_AsNotImplemented() {
    AiChatModelConfig config = new AiChatModelConfig();
    assertThrows(UnsupportedOperationException.class,
        () -> factory.getModel(AiProvider.ANTHROPIC, config));
  }

  @Test
  void testGetModel_ThrowsException_WhenProviderIsNull() {
    AiChatModelConfig config = new AiChatModelConfig();
    assertThrows(IllegalArgumentException.class, () -> factory.getModel(null, config));
  }
}
