package io.trishul.ai.agent.model;

import io.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateAiAgentConfig<T extends UpdateAiAgentConfig<T>>
    extends BaseAiAgentConfig<T>, UpdatableEntity<Long, T> {
}
