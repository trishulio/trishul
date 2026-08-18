package io.trishul.ai.service.memory.provider;

import io.trishul.ai.memory.model.AiChatMemoryConfig;

/**
 * Provider interface for retrieving AiChatMemoryConfig by ID. Analogous to
 * DataSourceConfigurationProvider.
 */
public interface AiChatMemoryConfigProvider {
  AiChatMemoryConfig getChatMemoryConfig(Long memoryConfigId);
}
