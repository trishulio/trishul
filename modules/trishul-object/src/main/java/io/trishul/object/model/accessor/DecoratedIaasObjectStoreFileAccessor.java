package io.trishul.object.model.accessor;

import java.net.URI;

import io.trishul.object.model.dto.IaasObjectStoreFileDto;

public interface DecoratedIaasObjectStoreFileAccessor {
    URI getImageSrc();

    void setObjectStoreFile(IaasObjectStoreFileDto objectStoreFile);
}
