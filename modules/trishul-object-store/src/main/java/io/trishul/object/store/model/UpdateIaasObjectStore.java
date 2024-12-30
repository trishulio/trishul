package io.trishul.object.store.model;

import io.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateIaasObjectStore<T extends UpdateIaasObjectStore<T>>
    extends BaseIaasObjectStore<T>, UpdatableEntity<String, T> {
}
