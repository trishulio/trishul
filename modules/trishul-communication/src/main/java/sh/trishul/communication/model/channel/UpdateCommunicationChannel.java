package sh.trishul.communication.model.channel;

import sh.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateCommunicationChannel<T extends UpdateCommunicationChannel<T>>
    extends BaseCommunicationChannel<T>, UpdatableEntity<String, T> {
}
