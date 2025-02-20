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

  public TenantIaasVfsDeleteResult setObjectStore(long objectStore) {
    this.objectStore = objectStore;
    return this;
  }

  public long getPolicy() {
    return policy;
  }

  public TenantIaasVfsDeleteResult setPolicy(long policy) {
    this.policy = policy;
    return this;
  }
}
