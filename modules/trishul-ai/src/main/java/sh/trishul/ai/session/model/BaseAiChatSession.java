package sh.trishul.ai.session.model;

import sh.trishul.ai.agent.model.AiAgentConfigAccessor;
import sh.trishul.ai.memory.model.AiChatMemoryConfigAccessor;

public interface BaseAiChatSession<T extends BaseAiChatSession<T>>
    extends AiChatMemoryConfigAccessor<T>, AiAgentConfigAccessor<T> {
  final String ATTR_SESSION_KEY = "sessionKey";
  final String ATTR_TITLE = "title";
  final String ATTR_IS_ACTIVE = "isActive";
  final String ATTR_AGENT_CONFIG = "agentConfig";

  String getSessionKey();

  T setSessionKey(String sessionKey);

  String getTitle();

  T setTitle(String title);

  Boolean getIsActive();

  T setIsActive(Boolean isActive);
}
