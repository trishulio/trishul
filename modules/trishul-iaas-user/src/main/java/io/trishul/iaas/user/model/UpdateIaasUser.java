package io.trishul.iaas.user.model;

import io.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateIaasUser<T extends UpdateIaasUser<T>>
    extends BaseIaasUser<T>, UpdatableEntity<String, T> {
}
