package io.trishul.ai.service.memory.manager;

import dev.langchain4j.memory.ChatMemory;
import io.trishul.ai.memory.model.AiChatMemoryConfig;

/**
 * Manager interface for building ChatMemory instances from a given configuration. Analogous to
 * DataSourceManager.
 */
public interface AiChatMemoryManager {

  /**
   * Returns a ChatMemory for the given config and memory ID (e.g., session ID). The memory ID
   * scopes the history per conversation/user.
   */
  ChatMemory getChatMemory(AiChatMemoryConfig config, Object memoryId);
}
