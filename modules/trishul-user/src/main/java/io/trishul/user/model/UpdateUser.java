package io.trishul.user.model;

import io.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateUser<T extends UpdateUser<T>> extends BaseUser<T>, UpdatableEntity<Long, T> {
}
