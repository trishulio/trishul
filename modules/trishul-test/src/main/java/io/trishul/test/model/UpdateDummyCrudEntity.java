package io.trishul.test.model;

import io.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateDummyCrudEntity<T extends UpdateDummyCrudEntity<T>>
    extends BaseDummyCrudEntity<T>, UpdatableEntity<Long, T> {
}
