package io.trishul.ai.service.memory.manager;

import dev.langchain4j.memory.ChatMemory;
import io.trishul.ai.memory.model.AiChatMemoryConfig;
import io.trishul.ai.service.memory.provider.AiChatMemoryConfigProvider;

/**
 * Top-level entry point for getting a ChatMemory by memoryConfigId. Analogous to
 * TenantDataSourceManagerWrapper: takes an ID, resolves the config via the provider, then delegates
 * to the manager to build the actual ChatMemory.
 */
public class AiChatMemoryManagerWrapper {

  private final AiChatMemoryConfigProvider configProvider;
  private final AiChatMemoryManager memoryManager;

  public AiChatMemoryManagerWrapper(AiChatMemoryConfigProvider configProvider,
      AiChatMemoryManager memoryManager) {
    this.configProvider = configProvider;
    this.memoryManager = memoryManager;
  }

  /**
   * Returns a ChatMemory for the given memoryConfigId and memoryId. The memoryConfigId identifies
   * which AiChatMemoryConfig to use (e.g., max messages, strategy). The memoryId scopes the actual
   * message history per conversation/session.
   *
   * @param memoryConfigId ID of the AiChatMemoryConfig to use; if null, builds memory with default
   *        settings.
   * @param memoryId session/conversation identifier for scoping message history.
   */
  public ChatMemory getChatMemory(Long memoryConfigId, Object memoryId) {
    AiChatMemoryConfig config = null;
    if (memoryConfigId != null) {
      config = this.configProvider.getChatMemoryConfig(memoryConfigId);
    }
    return this.memoryManager.getChatMemory(config, memoryId);
  }
}
