package io.trishul.communication.model.account;

import io.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateCommunicationAccount<T extends UpdateCommunicationAccount<T>>
    extends BaseCommunicationAccount<T>, UpdatableEntity<String, T> {
}
