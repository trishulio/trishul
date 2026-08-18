package sh.trishul.iaas.access.role.model;

import sh.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateIaasRole<T extends UpdateIaasRole<T>>
    extends BaseIaasRole<T>, UpdatableEntity<String, T> {
}
