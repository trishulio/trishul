package io.trishul.iaas.tenant.resource;

import io.trishul.iaas.access.role.model.IaasRole;
import io.trishul.iaas.idp.tenant.model.BaseIaasIdpTenant;

public interface TenantIaasResourceBuilder {
  String getRoleId(String iaasIdpTenantId);

  IaasRole buildRole(BaseIaasIdpTenant<?> iaasIdpTenant);
}
