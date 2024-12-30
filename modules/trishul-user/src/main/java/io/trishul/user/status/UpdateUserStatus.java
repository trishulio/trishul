package io.trishul.user.status;

import io.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateUserStatus<T extends UpdateUserStatus<T>>
    extends BaseUserStatus<T>, UpdatableEntity<Long, T> {
}
