package sh.trishul.ai.agent.model;

import sh.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateAiAgentConfig<T extends UpdateAiAgentConfig<T>>
    extends BaseAiAgentConfig<T>, UpdatableEntity<Long, T> {
}
