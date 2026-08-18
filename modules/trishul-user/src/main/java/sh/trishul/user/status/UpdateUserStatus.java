package sh.trishul.user.status;

import sh.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateUserStatus<T extends UpdateUserStatus<T>>
    extends BaseUserStatus<T>, UpdatableEntity<Long, T> {
}
