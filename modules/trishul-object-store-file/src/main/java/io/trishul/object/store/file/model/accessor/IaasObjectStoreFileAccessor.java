package io.trishul.object.store.file.model.accessor;

import io.trishul.object.store.file.model.IaasObjectStoreFile;

public interface IaasObjectStoreFileAccessor {
    IaasObjectStoreFile getObjectStoreFile();

    void setObjectStoreFile(IaasObjectStoreFile file);
}
