package sh.trishul.ai.chat.model;

import sh.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateAiChatModelConfig<T extends UpdateAiChatModelConfig<T>>
    extends BaseAiChatModelConfig<T>, UpdatableEntity<Long, T> {
}
