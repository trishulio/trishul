package io.trishul.object.store.file.model;

import io.trishul.base.types.base.pojo.UpdatableEntity;
import java.net.URI;

public interface UpdateIaasObjectStoreFile<T extends UpdateIaasObjectStoreFile<T>>
    extends BaseIaasObjectStoreFile<T>, UpdatableEntity<URI, T> {
}
