package sh.trishul.ai.agent.model;

import sh.trishul.ai.chat.model.AiChatModelConfigAccessor;
import sh.trishul.ai.memory.model.AiChatMemoryConfigAccessor;

public interface BaseAiAgentConfig<T extends BaseAiAgentConfig<T>>
    extends AiChatModelConfigAccessor<T>, AiChatMemoryConfigAccessor<T> {
  String ATTR_NAME = "name";
  String ATTR_DESCRIPTION = "description";
  String ATTR_IS_ACTIVE = "isActive";

  String getName();

  T setName(String name);

  String getDescription();

  T setDescription(String description);

  Boolean getIsActive();

  T setIsActive(Boolean isActive);
}
