package sh.trishul.tenant.entity;

import java.util.UUID;
import sh.trishul.base.types.base.pojo.IdentityAccessor;

public interface BaseTenant<T extends BaseTenant<T>>
    extends TenantData, MutableTenant<T>, IdentityAccessor<UUID, T> {
}
