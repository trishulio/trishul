package sh.trishul.communication.model.message;

import sh.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateMessage<T extends UpdateMessage<T>>
    extends BaseMessage<T>, UpdatableEntity<String, T> {
}
