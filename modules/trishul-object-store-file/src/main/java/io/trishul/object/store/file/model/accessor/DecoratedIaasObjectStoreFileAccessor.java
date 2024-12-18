package io.trishul.object.store.file.model.accessor;

import io.trishul.object.store.file.model.dto.IaasObjectStoreFileDto;
import java.net.URI;

public interface DecoratedIaasObjectStoreFileAccessor {
    URI getImageSrc();

    void setObjectStoreFile(IaasObjectStoreFileDto objectStoreFile);
}
