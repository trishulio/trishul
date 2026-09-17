package sh.trishul.ai.service.agent.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import sh.trishul.ai.chat.model.AiChatModelConfig;

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

    ChatModel model = factory.getModel(AiProvider.OPENAI, config);

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

    ChatModel model = factory.getModel(AiProvider.GITHUB_COPILOT, config);

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
  void testGetModel_ReturnsOpenRouterModel_WhenProviderIsOpenRouter() {
    AiChatModelConfig config = new AiChatModelConfig();
    config.setProvider("openrouter");
    config.setApiKey("test-openrouter-key");
    config.setModelName("meta-llama/llama-3");

    ChatModel model = factory.getModel(AiProvider.OPENROUTER, config);

    assertNotNull(model);
    assert (model instanceof OpenAiChatModel);
  }

  @Test
  void testGetOpenRouterModel_ThrowsException_WhenApiKeyIsMissing() {
    AiChatModelConfig config = new AiChatModelConfig();
    assertThrows(IllegalArgumentException.class, () -> factory.getOpenRouterModel(config));
  }

  @Test
  void testGetOpenRouterModel_ThrowsException_WhenApiKeyIsEmpty() {
    AiChatModelConfig config = new AiChatModelConfig();
    config.setApiKey("");
    assertThrows(IllegalArgumentException.class, () -> factory.getOpenRouterModel(config));
  }

  @Test
  void testCustomBaseUrlConstructor() {
    ChatModelFactory customFactory = new ChatModelFactory("https://custom-copilot",
        "https://custom-openrouter", "https://custom-openai");
    AiChatModelConfig config = new AiChatModelConfig();
    config.setApiKey("key");
    config.setModelName("m");

    ChatModel copilotModel = customFactory.getGithubCopilotModel(config);
    ChatModel openRouterModel = customFactory.getOpenRouterModel(config);
    ChatModel openAiModel = customFactory.getOpenAiModel(config);

    assertNotNull(copilotModel);
    assertNotNull(openRouterModel);
    assertNotNull(openAiModel);

    assertEquals("https://custom-copilot", getBaseUrl(copilotModel));
    assertEquals("https://custom-openrouter", getBaseUrl(openRouterModel));
    assertEquals("https://custom-openai", getBaseUrl(openAiModel));
  }

  @Test
  void testDefaultBaseUrls_WhenUrlsAreEmptyOrNull() {
    ChatModelFactory emptyFactory = new ChatModelFactory("", "", "");
    AiChatModelConfig config = new AiChatModelConfig();
    config.setApiKey("key");
    config.setModelName("m");

    ChatModel copilotModel = emptyFactory.getGithubCopilotModel(config);
    ChatModel openRouterModel = emptyFactory.getOpenRouterModel(config);
    ChatModel openAiModel = emptyFactory.getOpenAiModel(config);

    assertEquals("https://api.openai.com/v1", getBaseUrl(copilotModel));
    assertEquals("https://api.openai.com/v1", getBaseUrl(openRouterModel));
    assertEquals("https://api.openai.com/v1", getBaseUrl(openAiModel));

    ChatModelFactory nullFactory = new ChatModelFactory(null, null, null);
    copilotModel = nullFactory.getGithubCopilotModel(config);
    openRouterModel = nullFactory.getOpenRouterModel(config);
    openAiModel = nullFactory.getOpenAiModel(config);

    assertEquals("https://api.openai.com/v1", getBaseUrl(copilotModel));
    assertEquals("https://api.openai.com/v1", getBaseUrl(openRouterModel));
    assertEquals("https://api.openai.com/v1", getBaseUrl(openAiModel));
  }

  private String getBaseUrl(ChatModel model) {
    Object client = ReflectionTestUtils.getField(model, "client");
    return (String) ReflectionTestUtils.getField(client, "baseUrl");
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
