package io.trishul.iaas.tenant.object.store;

import io.trishul.iaas.access.role.model.IaasRoleAccessor;
import io.trishul.object.store.model.IaasObjectStoreAccessor;

public interface BaseTenantIaasVfsResources<T extends BaseTenantIaasVfsResources<T>>
    extends IaasRoleAccessor<T>, IaasObjectStoreAccessor<T> {
}
