package sh.trishul.iaas.user.model;

import sh.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateIaasUserTenantMembership<T extends UpdateIaasUserTenantMembership<T>>
    extends BaseIaasUserTenantMembership<T>, UpdatableEntity<IaasUserTenantMembershipId, T> {
}
