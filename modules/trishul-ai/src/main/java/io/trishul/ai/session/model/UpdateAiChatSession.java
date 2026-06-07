package io.trishul.ai.session.model;

import io.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateAiChatSession<T extends UpdateAiChatSession<T>>
    extends BaseAiChatSession<T>, UpdatableEntity<Long, T> {
}
