package io.trishul.ai.service.agent.manager;

import io.trishul.ai.agent.model.AiAgentConfig;

/**
 * Manager interface for building and retrieving AI agent instances. Analogous to DataSourceManager.
 */
public interface AiAgentManager {

  /**
   * Returns an agent instance (LangChain4j AiServices proxy) for the given config and memoryId. The
   * agent is configured with the ChatModel and ChatMemory derived from the config.
   *
   * @param config full AiAgentConfig with chatModelConfig and chatMemoryConfig
   * @param memoryId session/conversation identifier passed to the ChatMemory
   */
  Object getAgent(AiAgentConfig config, Object memoryId);
}
