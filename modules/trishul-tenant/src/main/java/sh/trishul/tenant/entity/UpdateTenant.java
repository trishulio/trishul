package sh.trishul.tenant.entity;

import java.util.UUID;
import sh.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateTenant<T extends UpdateTenant<T>>
    extends BaseTenant<T>, UpdatableEntity<UUID, T> {
}
