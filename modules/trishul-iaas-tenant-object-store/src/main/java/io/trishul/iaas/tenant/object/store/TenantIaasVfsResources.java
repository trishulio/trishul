package io.trishul.iaas.tenant.object.store;

import io.trishul.iaas.access.policy.model.IaasPolicy;
import io.trishul.model.base.pojo.BaseModel;
import io.trishul.object.store.model.IaasObjectStore;

public class TenantIaasVfsResources extends BaseModel {
  private IaasObjectStore objectStore;
  private IaasPolicy policy;

  public TenantIaasVfsResources() {}

  public TenantIaasVfsResources(IaasObjectStore objectStore, IaasPolicy policy) {
    this();
    setObjectStore(objectStore);
    setPolicy(policy);
  }

  public IaasObjectStore getObjectStore() {
    return objectStore == null ? null : objectStore.deepClone();
  }

  public TenantIaasVfsResources setObjectStore(IaasObjectStore objectStore) {
    this.objectStore = objectStore == null ? null : objectStore.deepClone();
    return this;
  }

  public IaasPolicy getPolicy() {
    return policy == null ? null : policy.deepClone();
  }

  public TenantIaasVfsResources setPolicy(IaasPolicy policy) {
    this.policy = policy == null ? null : policy.deepClone();
    return this;
  }
}
