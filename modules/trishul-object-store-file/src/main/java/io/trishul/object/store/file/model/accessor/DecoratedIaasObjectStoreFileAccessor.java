package io.trishul.object.store.file.model.accessor;

import java.net.URI;

import io.trishul.object.store.file.model.dto.IaasObjectStoreFileDto;

public interface DecoratedIaasObjectStoreFileAccessor {
    URI getImageSrc();

    void setObjectStoreFile(IaasObjectStoreFileDto objectStoreFile);
}
