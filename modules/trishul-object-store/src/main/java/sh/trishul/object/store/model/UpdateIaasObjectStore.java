package sh.trishul.object.store.model;

import sh.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateIaasObjectStore<T extends UpdateIaasObjectStore<T>>
    extends BaseIaasObjectStore<T>, UpdatableEntity<String, T> {
}
