package io.trishul.tenant.entity;

import io.trishul.base.types.base.pojo.IdentityAccessor;
import java.util.UUID;

public interface BaseTenant<T extends BaseTenant<T>>
    extends TenantData, MutableTenant<T>, IdentityAccessor<UUID, T> {
}
