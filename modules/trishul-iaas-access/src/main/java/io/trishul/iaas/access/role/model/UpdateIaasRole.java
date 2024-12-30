package io.trishul.iaas.access.role.model;

import io.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateIaasRole<T extends UpdateIaasRole<T>>
    extends BaseIaasRole<T>, UpdatableEntity<String, T> {
}
