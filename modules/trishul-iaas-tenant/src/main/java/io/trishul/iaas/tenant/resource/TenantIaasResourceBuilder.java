package io.trishul.iaas.tenant.resource;

import io.trishul.iaas.access.role.model.BaseIaasRole;
import io.trishul.iaas.idp.tenant.model.BaseIaasIdpTenant;

public interface TenantIaasResourceBuilder {
    String getRoleId(String iaasIdpTenantId);

    <R extends BaseIaasRole, T extends BaseIaasIdpTenant> R buildRole(T iaasIdpTenant);
}
