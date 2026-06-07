package io.trishul.ai.chat.model;

import io.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateAiChatModelConfig<T extends UpdateAiChatModelConfig<T>>
    extends BaseAiChatModelConfig<T>, UpdatableEntity<Long, T> {
}
