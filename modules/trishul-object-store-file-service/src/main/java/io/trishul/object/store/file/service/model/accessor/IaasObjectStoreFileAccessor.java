package io.trishul.object.store.file.service.model.accessor;

import io.trishul.object.store.file.service.model.entity.IaasObjectStoreFile;

public interface IaasObjectStoreFileAccessor {
    IaasObjectStoreFile getObjectStoreFile();

    void setObjectStoreFile(IaasObjectStoreFile file);
}
