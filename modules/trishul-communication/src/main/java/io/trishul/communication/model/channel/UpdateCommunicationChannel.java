package io.trishul.communication.model.channel;

import io.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateCommunicationChannel<T extends UpdateCommunicationChannel<T>>
    extends BaseCommunicationChannel<T>, UpdatableEntity<String, T> {
}
