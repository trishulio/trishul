package io.trishul.object.store.configuration.access.model;

public interface IaasObjectStoreAccessConfigAccessor<T extends IaasObjectStoreAccessConfigAccessor<T>> {

  IaasObjectStoreAccessConfig getIaasObjectStoreAccessConfig();

  T setIaasObjectStoreAccessConfig(IaasObjectStoreAccessConfig iaasObjectStoreAccessConfig);
}
