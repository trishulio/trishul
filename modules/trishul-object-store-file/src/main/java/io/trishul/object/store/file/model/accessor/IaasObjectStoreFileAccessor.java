package io.trishul.object.store.file.model.accessor;

import io.trishul.object.store.file.model.IaasObjectStoreFile;

public interface IaasObjectStoreFileAccessor<T extends IaasObjectStoreFileAccessor<T>> {
  IaasObjectStoreFile getObjectStoreFile();

  T setObjectStoreFile(IaasObjectStoreFile file);
}
