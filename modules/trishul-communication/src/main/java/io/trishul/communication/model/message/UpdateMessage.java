package io.trishul.communication.model.message;

import io.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateMessage<T extends UpdateMessage<T>>
    extends BaseMessage<T>, UpdatableEntity<String, T> {
}
