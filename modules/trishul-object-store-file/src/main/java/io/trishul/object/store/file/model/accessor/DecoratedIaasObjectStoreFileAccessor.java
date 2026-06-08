package io.trishul.object.store.file.model.accessor;

import io.trishul.object.store.file.model.dto.IaasObjectStoreFileDto;
import java.net.URI;

public interface DecoratedIaasObjectStoreFileAccessor<T extends DecoratedIaasObjectStoreFileAccessor<T>> {
  String ATTR_OBJECT_STORE_FILE = "objectStoreFile";

  URI getImageSrc();

  T setObjectStoreFile(IaasObjectStoreFileDto objectStoreFile);
}
