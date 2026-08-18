package io.trishul.ai.service.agent.factory;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.service.AiServices;
import io.trishul.ai.agent.model.AiAgentConfig;
import io.trishul.ai.chat.model.AiChatModelConfig;
import io.trishul.ai.memory.model.AiChatMemoryConfig;
import io.trishul.ai.service.agent.Assistant;
import io.trishul.ai.service.memory.store.TenantChatMemoryStore;
import io.trishul.ai.service.tool.registry.AiToolRegistry;
import java.util.List;

public class AgentFactory {

  private final TenantChatMemoryStore memoryStore;
  private final AiToolRegistry toolRegistry;
  private final StreamingChatModelFactory streamingModelFactory;
  private final ChatModelFactory chatModelFactory;

  public AgentFactory(TenantChatMemoryStore memoryStore, AiToolRegistry toolRegistry,
      StreamingChatModelFactory streamingModelFactory, ChatModelFactory chatModelFactory) {
    this.memoryStore = memoryStore;
    this.toolRegistry = toolRegistry;
    this.streamingModelFactory = streamingModelFactory;
    this.chatModelFactory = chatModelFactory;
  }

  /**
   * Builds a LangChain4j AiServices proxy (Assistant) configured with the ChatLanguageModel and
   * ChatMemory derived from the given AiAgentConfig. The ChatMemory is pre-built and wired in
   * directly, using a ChatMemoryProvider keyed by memoryId for per-conversation isolation.
   *
   * @param config the full AiAgentConfig (must not be null).
   * @param chatMemory a pre-built ChatMemory scoped to the current session/memoryId.
   * @return an Assistant proxy built via AiServices.
   */
  public Assistant buildAgent(AiAgentConfig config, ChatMemory chatMemory) {
    if (config == null) {
      throw new IllegalArgumentException("AiAgentConfig must not be null");
    }
    ChatLanguageModel chatModel = buildChatModel(config.getChatModelConfig());
    StreamingChatLanguageModel streamingChatModel
        = buildStreamingModel(config.getChatModelConfig());
    List<Object> tools = resolveTool(config);
    AiServices<Assistant> builder = AiServices.builder(Assistant.class).chatLanguageModel(chatModel)
        .streamingChatLanguageModel(streamingChatModel).chatMemory(chatMemory);
    if (!tools.isEmpty()) {
      builder.tools(tools);
    }
    return builder.build();
  }

  /**
   * Legacy single-arg overload kept for backward compatibility with AgentCache. Builds an agent
   * without a pre-wired ChatMemory; the returned Assistant uses a ChatMemoryProvider backed by
   * TenantChatMemoryStore instead so memory is still persisted per memoryId.
   */
  public Object buildAgent(AiAgentConfig config) {
    if (config == null) {
      throw new IllegalArgumentException("AiAgentConfig must not be null");
    }
    ChatLanguageModel chatModel = buildChatModel(config.getChatModelConfig());
    StreamingChatLanguageModel streamingChatModel
        = buildStreamingModel(config.getChatModelConfig());
    AiChatMemoryConfig memCfg = config.getChatMemoryConfig();
    List<Object> tools = resolveTool(config);
    AiServices<Assistant> builder = AiServices.builder(Assistant.class).chatLanguageModel(chatModel)
        .streamingChatLanguageModel(streamingChatModel)
        .chatMemoryProvider(memoryId -> buildMemory(memCfg, memoryId));
    if (!tools.isEmpty()) {
      builder.tools(tools);
    }
    return builder.build();
  }

  public ChatLanguageModel buildChatModel(AiChatModelConfig modelConfig) {
    if (modelConfig == null) {
      throw new IllegalArgumentException("AiChatModelConfig must not be null");
    }
    AiProvider provider = AiProvider.fromString(modelConfig.getProvider());
    return chatModelFactory.getModel(provider, modelConfig);
  }

  /**
   * Kept for callers that explicitly need a StreamingChatLanguageModel (e.g., AiChatController SSE
   * path).
   */
  public StreamingChatLanguageModel buildStreamingModel(AiChatModelConfig modelConfig) {
    if (modelConfig == null) {
      throw new IllegalArgumentException("AiChatModelConfig must not be null");
    }
    AiProvider provider = AiProvider.fromString(modelConfig.getProvider());
    return streamingModelFactory.getModel(provider, modelConfig);
  }

  public ChatMemory buildMemory(AiChatMemoryConfig memoryConfig, Object memoryId) {
    int maxMessages = 10;
    if (memoryConfig != null && memoryConfig.getMaxMessages() != null) {
      maxMessages = memoryConfig.getMaxMessages();
    }
    return MessageWindowChatMemory.builder().id(memoryId).maxMessages(maxMessages)
        .chatMemoryStore(memoryStore).build();
  }

  private List<Object> resolveTool(AiAgentConfig config) {
    return toolRegistry.getToolsByIds(List.of());
  }
}
