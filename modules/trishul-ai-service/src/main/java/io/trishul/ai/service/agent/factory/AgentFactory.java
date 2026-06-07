package io.trishul.ai.service.agent.factory;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import io.trishul.ai.agent.model.AiAgentConfig;
import io.trishul.ai.chat.model.AiChatModelConfig;
import io.trishul.ai.memory.model.AiChatMemoryConfig;
import io.trishul.ai.service.memory.store.TenantChatMemoryStore;
import io.trishul.ai.service.tool.registry.AiToolRegistry;

/**
 * AgentFactory builds the final AiServices instance based on the provided AiAgentConfig. It ties
 * together the Model, Memory, Skills, Tools, and Guardrails.
 */
public class AgentFactory {

  private final TenantChatMemoryStore memoryStore;
  private final AiToolRegistry toolRegistry;

  public AgentFactory(TenantChatMemoryStore memoryStore, AiToolRegistry toolRegistry) {
    this.memoryStore = memoryStore;
    this.toolRegistry = toolRegistry;
  }

  public Object buildAgent(AiAgentConfig config) {
    // In actual implementation, returns something like:
    // AiServices.builder(MyAgentInterface.class)
    // .chatLanguageModel(buildModel(config.getChatModelConfig()))
    // .chatMemory(buildMemory(config.getChatMemoryConfig()))
    // .tools(toolRegistry.getToolsByIds(config.getToolIds()))
    // ...
    // .build();
    
    // For now, return the chat language model since AiChatController uses it directly
    return buildModel(config != null ? config.getChatModelConfig() : null);
  }

  public StreamingChatLanguageModel buildModel(AiChatModelConfig modelConfig) {
    if (modelConfig == null) {
      return OpenAiStreamingChatModel.builder()
          .apiKey("demo")
          .modelName("gpt-4o-mini")
          .build();
    }
    
    if ("openai".equalsIgnoreCase(modelConfig.getProvider())) {
      return OpenAiStreamingChatModel.builder()
          .apiKey(modelConfig.getApiKey() != null ? modelConfig.getApiKey() : "demo")
          .modelName(modelConfig.getStreamingModelName() != null ? modelConfig.getStreamingModelName() : modelConfig.getModelName())
          .temperature(modelConfig.getTemperature())
          .topP(modelConfig.getTopP())
          .build();
    }
    throw new IllegalArgumentException("Unsupported provider: " + modelConfig.getProvider());
  }

  public ChatMemory buildMemory(AiChatMemoryConfig memoryConfig, Object memoryId) {
    int maxMessages = 10;
    if (memoryConfig != null && memoryConfig.getMaxMessages() != null) {
      maxMessages = memoryConfig.getMaxMessages();
    }
    
    return MessageWindowChatMemory.builder()
        .id(memoryId)
        .maxMessages(maxMessages)
        .chatMemoryStore(memoryStore)
        .build();
  }
}
