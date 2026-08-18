package sh.trishul.object.store.model;

public interface IaasObjectStoreAccessor<T extends IaasObjectStoreAccessor<T>> {
  IaasObjectStore getIaasObjectStore();

  T setIaasObjectStore(IaasObjectStore tenantObjectStore);
}
