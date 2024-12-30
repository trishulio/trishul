package io.trishul.user.salutation.model;

import io.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateUserSalutation<T extends UpdateUserSalutation<T>>
    extends BaseUserSalutation<T>, UpdatableEntity<Long, T> {
}
