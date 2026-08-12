package sh.trishul.iaas.tenant.object.store;

import sh.trishul.iaas.access.role.model.IaasRoleAccessor;
import sh.trishul.object.store.model.IaasObjectStoreAccessor;

public interface BaseTenantIaasVfsResources<T extends BaseTenantIaasVfsResources<T>>
    extends IaasRoleAccessor<T>, IaasObjectStoreAccessor<T> {
}
