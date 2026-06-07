package io.trishul.ai.memory.model;

import io.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateAiChatMemoryConfig<T extends UpdateAiChatMemoryConfig<T>>
    extends BaseAiChatMemoryConfig<T>, UpdatableEntity<Long, T> {
}
