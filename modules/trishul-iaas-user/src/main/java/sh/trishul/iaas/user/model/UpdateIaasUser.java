package sh.trishul.iaas.user.model;

import sh.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateIaasUser<T extends UpdateIaasUser<T>>
    extends BaseIaasUser<T>, UpdatableEntity<String, T> {
}
