package sh.trishul.ai.agent.model;

public interface AiAgentConfigAccessor<T extends AiAgentConfigAccessor<T>> {
  String ATTR_AGENT_CONFIG = "agentConfig";

  AiAgentConfig getAgentConfig();

  T setAgentConfig(AiAgentConfig agentConfig);
}
