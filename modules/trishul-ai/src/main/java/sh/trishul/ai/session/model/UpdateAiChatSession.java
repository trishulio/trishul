package sh.trishul.ai.session.model;

import sh.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateAiChatSession<T extends UpdateAiChatSession<T>>
    extends BaseAiChatSession<T>, UpdatableEntity<Long, T> {
}
