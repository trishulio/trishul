package sh.trishul.ai.service.memory.manager;

import dev.langchain4j.memory.ChatMemory;
import sh.trishul.ai.memory.model.AiChatMemoryConfig;
import sh.trishul.ai.service.agent.factory.AgentFactory;

/**
 * Implementation of AiChatMemoryManager that delegates to AgentFactory to build ChatMemory
 * instances. The factory already holds the TenantChatMemoryStore for persistence. Note: ChatMemory
 * is inherently stateful (per memoryId), so only the construction parameters (from config) are
 * stable enough to key a cache. Each call for a given (config, memoryId) pair may return the same
 * underlying store entry.
 */
public class CachingChatMemoryManager implements AiChatMemoryManager {

  private final AgentFactory agentFactory;

  public CachingChatMemoryManager(AgentFactory agentFactory) {
    this.agentFactory = agentFactory;
  }

  @Override
  public ChatMemory getChatMemory(AiChatMemoryConfig config, Object memoryId) {
    // AgentFactory.buildMemory uses TenantChatMemoryStore keyed by memoryId,
    // so the store provides the persistence/caching layer for message history.
    return agentFactory.buildMemory(config, memoryId);
  }
}
