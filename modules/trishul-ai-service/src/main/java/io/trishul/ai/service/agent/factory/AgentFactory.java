package io.trishul.ai.service.agent.factory;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import io.trishul.ai.agent.model.AiAgentConfig;
import io.trishul.ai.chat.model.AiChatModelConfig;
import io.trishul.ai.memory.model.AiChatMemoryConfig;
import io.trishul.ai.service.memory.store.TenantChatMemoryStore;
import io.trishul.ai.service.tool.registry.AiToolRegistry;

public class AgentFactory {

  private final TenantChatMemoryStore memoryStore;
  private final AiToolRegistry toolRegistry;
  private final StreamingChatModelFactory modelFactory;

  public AgentFactory(TenantChatMemoryStore memoryStore, AiToolRegistry toolRegistry,
      StreamingChatModelFactory modelFactory) {
    this.memoryStore = memoryStore;
    this.toolRegistry = toolRegistry;
    this.modelFactory = modelFactory;
  }

  public Object buildAgent(AiAgentConfig config) {
    return buildModel(config != null ? config.getChatModelConfig() : null);
  }

  public StreamingChatLanguageModel buildModel(AiChatModelConfig modelConfig) {
    if (modelConfig == null) {
      throw new IllegalArgumentException("AiChatModelConfig must not be null");
    }
    AiProvider provider = AiProvider.fromString(modelConfig.getProvider());
    return modelFactory.getModel(provider, modelConfig);
  }

  public ChatMemory buildMemory(AiChatMemoryConfig memoryConfig, Object memoryId) {
    int maxMessages = 10;
    if (memoryConfig != null && memoryConfig.getMaxMessages() != null) {
      maxMessages = memoryConfig.getMaxMessages();
    }

    return MessageWindowChatMemory.builder().id(memoryId).maxMessages(maxMessages)
        .chatMemoryStore(memoryStore).build();
  }
}
