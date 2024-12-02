package io.trishul.object.store.model;

public interface IaasObjectStoreAccessor {
    IaasObjectStore getIaasObjectStore();

    void setIaasObjectStore(IaasObjectStore tenantObjectStore);
}
