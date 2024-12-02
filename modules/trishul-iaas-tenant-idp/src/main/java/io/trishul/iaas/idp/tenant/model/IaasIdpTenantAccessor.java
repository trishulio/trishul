package io.trishul.iaas.idp.tenant.model;

public interface IaasIdpTenantAccessor {
    IaasIdpTenant getIdpTenant();

    void setIdpTenant(IaasIdpTenant idpTenant);
}
