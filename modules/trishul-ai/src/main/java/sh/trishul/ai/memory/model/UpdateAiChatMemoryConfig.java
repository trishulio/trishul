package sh.trishul.ai.memory.model;

import sh.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateAiChatMemoryConfig<T extends UpdateAiChatMemoryConfig<T>>
    extends BaseAiChatMemoryConfig<T>, UpdatableEntity<Long, T> {
}
