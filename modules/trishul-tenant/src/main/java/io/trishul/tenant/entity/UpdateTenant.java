package io.trishul.tenant.entity;

import io.trishul.base.types.base.pojo.UpdatableEntity;
import java.util.UUID;

public interface UpdateTenant<T extends UpdateTenant<T>>
    extends BaseTenant<T>, UpdatableEntity<UUID, T> {
}
