package sh.trishul.test.model;

import sh.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateDummyCrudEntity<T extends UpdateDummyCrudEntity<T>>
    extends BaseDummyCrudEntity<T>, UpdatableEntity<Long, T> {
}
