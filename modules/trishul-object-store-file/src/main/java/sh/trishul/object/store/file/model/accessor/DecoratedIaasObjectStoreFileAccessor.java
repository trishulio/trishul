package sh.trishul.object.store.file.model.accessor;

import java.net.URI;
import sh.trishul.object.store.file.model.dto.IaasObjectStoreFileDto;

public interface DecoratedIaasObjectStoreFileAccessor<T extends DecoratedIaasObjectStoreFileAccessor<T>> {
  String ATTR_OBJECT_STORE_FILE = "objectStoreFile";

  URI getImageSrc();

  T setObjectStoreFile(IaasObjectStoreFileDto objectStoreFile);
}
