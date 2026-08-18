package sh.trishul.iaas.access.policy.model;

import sh.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateIaasPolicy<T extends UpdateIaasPolicy<T>>
    extends BaseIaasPolicy<T>, UpdatableEntity<String, T> {
}
