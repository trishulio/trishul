package io.trishul.object.service.model.accessor;

import io.trishul.object.service.model.entity.IaasObjectStoreFile;

public interface IaasObjectStoreFileAccessor {
    IaasObjectStoreFile getObjectStoreFile();

    void setObjectStoreFile(IaasObjectStoreFile file);
}
