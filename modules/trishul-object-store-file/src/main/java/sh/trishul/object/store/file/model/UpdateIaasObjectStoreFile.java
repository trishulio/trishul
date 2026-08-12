package sh.trishul.object.store.file.model;

import java.net.URI;
import sh.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateIaasObjectStoreFile<T extends UpdateIaasObjectStoreFile<T>>
    extends BaseIaasObjectStoreFile<T>, UpdatableEntity<URI, T> {
}
