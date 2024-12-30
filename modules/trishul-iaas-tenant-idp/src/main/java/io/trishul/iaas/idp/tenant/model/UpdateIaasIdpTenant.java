package io.trishul.iaas.idp.tenant.model;

import io.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateIaasIdpTenant<T extends UpdateIaasIdpTenant<T>>
    extends BaseIaasIdpTenant<T>, UpdatableEntity<String, T> {
}
