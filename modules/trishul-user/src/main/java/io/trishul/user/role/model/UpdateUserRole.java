package io.trishul.user.role.model;

import io.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateUserRole<T extends UpdateUserRole<T>>
    extends BaseUserRole<T>, UpdatableEntity<Long, T> {
}
