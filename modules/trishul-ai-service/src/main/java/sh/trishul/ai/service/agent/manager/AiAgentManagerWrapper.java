package sh.trishul.ai.service.agent.manager;

import dev.langchain4j.memory.ChatMemory;
import sh.trishul.ai.agent.model.AiAgentConfig;
import sh.trishul.ai.service.agent.factory.AgentFactory;
import sh.trishul.ai.service.agent.provider.AiAgentConfigProvider;
import sh.trishul.ai.service.memory.manager.AiChatMemoryManagerWrapper;

/**
 * Top-level entry point for building an AI agent (LangChain4j AiServices proxy) identified by a
 * Long agentConfigId and a memoryId. Analogous to TenantDataSourceManagerWrapper:
 *
 * <ol>
 * <li>Resolves the full AiAgentConfig from CachingAiAgentConfigProvider.
 * <li>Resolves/builds the ChatMemory via AiChatMemoryManagerWrapper (which itself lazily loads
 * AiChatMemoryConfig).
 * <li>Delegates to AgentFactory to build the final LangChain4j AiServices assistant.
 * </ol>
 */
public class AiAgentManagerWrapper {

  private final AiAgentConfigProvider agentConfigProvider;
  private final AiChatMemoryManagerWrapper chatMemoryManagerWrapper;
  private final AgentFactory agentFactory;

  public AiAgentManagerWrapper(AiAgentConfigProvider agentConfigProvider,
      AiChatMemoryManagerWrapper chatMemoryManagerWrapper, AgentFactory agentFactory) {
    this.agentConfigProvider = agentConfigProvider;
    this.chatMemoryManagerWrapper = chatMemoryManagerWrapper;
    this.agentFactory = agentFactory;
  }

  /**
   * Returns a LangChain4j AiServices-based agent proxy for the given agentConfigId and memoryId.
   *
   * @param agentConfigId ID of the AiAgentConfig to use.
   * @param memoryId session/conversation identifier for scoping chat memory.
   */
  public Object getAgent(Long agentConfigId, Object memoryId) {
    AiAgentConfig config = this.agentConfigProvider.getAgentConfig(agentConfigId);

    Long memoryConfigId
        = (config.getChatMemoryConfig() != null) ? config.getChatMemoryConfig().getId() : null;
    ChatMemory chatMemory = this.chatMemoryManagerWrapper.getChatMemory(memoryConfigId, memoryId);

    return this.agentFactory.buildAgent(config, chatMemory);
  }
}
