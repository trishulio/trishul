package sh.trishul.ai.session.model;

import sh.trishul.ai.agent.model.AiAgentConfigAccessor;
import sh.trishul.ai.memory.model.AiChatMemoryConfigAccessor;

public interface BaseAiChatSession<T extends BaseAiChatSession<T>>
    extends AiChatMemoryConfigAccessor<T>, AiAgentConfigAccessor<T> {
  String ATTR_SESSION_KEY = "sessionKey";
  String ATTR_TITLE = "title";
  String ATTR_IS_ACTIVE = "isActive";
  String ATTR_AGENT_CONFIG = "agentConfig";

  String getSessionKey();

  T setSessionKey(String sessionKey);

  String getTitle();

  T setTitle(String title);

  Boolean getIsActive();

  T setIsActive(Boolean isActive);
}
