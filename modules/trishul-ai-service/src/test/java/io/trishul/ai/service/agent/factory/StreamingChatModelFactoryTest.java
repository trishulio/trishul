package io.trishul.ai.service.agent.factory;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import io.trishul.ai.chat.model.AiChatModelConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StreamingChatModelFactoryTest {

  private StreamingChatModelFactory factory;

  @BeforeEach
  void setUp() {
    factory = new StreamingChatModelFactory();
  }

  @Test
  void testGetModel_ReturnsOpenAiModel_WhenProviderIsOpenAi() {
    AiChatModelConfig config = new AiChatModelConfig();
    config.setProvider("openai");
    config.setApiKey("test-key");
    config.setModelName("gpt-4");

    StreamingChatLanguageModel model = factory.getModel(AiProvider.OPENAI, config);

    assertNotNull(model);
    assert (model instanceof OpenAiStreamingChatModel);
  }

  @Test
  void testGetModel_ReturnsOpenAiModel_WhenStreamingModelNameIsNull() {
    AiChatModelConfig config = new AiChatModelConfig();
    config.setProvider("openai");
    config.setApiKey("test-key");
    config.setModelName("gpt-4");
    config.setStreamingModelName(null);

    StreamingChatLanguageModel model = factory.getModel(AiProvider.OPENAI, config);

    assertNotNull(model);
    assert (model instanceof OpenAiStreamingChatModel);
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
  void testGetModel_ReturnsOpenAiModel_WhenStreamingModelNameIsNotNull() {
    AiChatModelConfig config = new AiChatModelConfig();
    config.setProvider("openai");
    config.setApiKey("test-key");
    config.setModelName("gpt-4");
    config.setStreamingModelName("gpt-4-stream");

    StreamingChatLanguageModel model = factory.getModel(AiProvider.OPENAI, config);

    assertNotNull(model);
    assert (model instanceof OpenAiStreamingChatModel);
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
