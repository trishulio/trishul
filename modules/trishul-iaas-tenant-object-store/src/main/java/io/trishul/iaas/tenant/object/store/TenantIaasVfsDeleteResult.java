package io.trishul.iaas.tenant.object.store;

import io.trishul.model.base.pojo.BaseModel;

public class TenantIaasVfsDeleteResult extends BaseModel {
    private long objectStore;
    private long policy;

    public TenantIaasVfsDeleteResult(long objectStore, long policy) {
        setObjectStore(objectStore);
        setPolicy(policy);
    }

    public long getObjectStore() {
        return objectStore;
    }

    public final void setObjectStore(long objectStore) {
        this.objectStore = objectStore;
    }

    public long getPolicy() {
        return policy;
    }

    public final void setPolicy(long policy) {
        this.policy = policy;
    }
}
