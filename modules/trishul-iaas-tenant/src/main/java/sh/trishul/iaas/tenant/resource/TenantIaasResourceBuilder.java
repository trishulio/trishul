package sh.trishul.iaas.tenant.resource;

import sh.trishul.iaas.access.role.model.IaasRole;
import sh.trishul.iaas.idp.tenant.model.BaseIaasIdpTenant;

public interface TenantIaasResourceBuilder {
  String getRoleId(String iaasIdpTenantId);

  IaasRole buildRole(BaseIaasIdpTenant<?> iaasIdpTenant);
}
