package io.trishul.iaas.tenant.object.store.management.service;

import io.trishul.iaas.access.policy.model.IaasPolicy;
import io.trishul.model.base.pojo.BaseModel;
import io.trishul.object.store.model.IaasObjectStore;

public class TenantIaasVfsResources extends BaseModel {
    private IaasObjectStore objectStore;
    private IaasPolicy policy;

    public TenantIaasVfsResources() {
    }

    public TenantIaasVfsResources(IaasObjectStore objectStore, IaasPolicy policy) {
        this();
        setObjectStore(objectStore);
        setPolicy(policy);
    }

    public IaasObjectStore getObjectStore() {
        return objectStore;
    }

    public void setObjectStore(IaasObjectStore objectStore) {
        this.objectStore = objectStore;
    }

    public IaasPolicy getPolicy() {
        return policy;
    }

    public void setPolicy(IaasPolicy policy) {
        this.policy = policy;
    }
}
