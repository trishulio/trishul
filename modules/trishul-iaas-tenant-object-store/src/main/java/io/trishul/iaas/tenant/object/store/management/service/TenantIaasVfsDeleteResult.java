package io.trishul.iaas.tenant.object.store.management.service;

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

    public void setObjectStore(long objectStore) {
        this.objectStore = objectStore;
    }

    public long getPolicy() {
        return policy;
    }

    public void setPolicy(long policy) {
        this.policy = policy;
    }
}
