package sh.trishul.ai.service.agent.provider;

import sh.trishul.ai.agent.model.AiAgentConfig;

/**
 * Provider interface for retrieving AiAgentConfig by ID. Analogous to
 * DataSourceConfigurationProvider.
 */
public interface AiAgentConfigProvider {
  AiAgentConfig getAgentConfig(Long agentConfigId);
}
