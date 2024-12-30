package io.trishul.iaas.idp.tenant.model;

public interface IaasIdpTenantAccessor<T extends IaasIdpTenantAccessor<T>> {
  IaasIdpTenant getIdpTenant();

  T setIdpTenant(IaasIdpTenant idpTenant);
}
