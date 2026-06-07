package io.trishul.ai.agent.model;

import io.trishul.ai.chat.model.AiChatModelConfigAccessor;
import io.trishul.ai.memory.model.AiChatMemoryConfigAccessor;

public interface BaseAiAgentConfig<T extends BaseAiAgentConfig<T>>
    extends AiChatModelConfigAccessor<T>, AiChatMemoryConfigAccessor<T> {
  final String ATTR_NAME = "name";
  final String ATTR_DESCRIPTION = "description";
  final String ATTR_IS_ACTIVE = "isActive";

  String getName();

  T setName(String name);

  String getDescription();

  T setDescription(String description);

  Boolean getIsActive();

  T setIsActive(Boolean isActive);
}
