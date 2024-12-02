package io.trishul.iaas.idp.tenant.model;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import io.trishul.tenant.entity.BaseTenant;

public class TenantIaasIdpTenantMapper {
    public static final TenantIaasIdpTenantMapper INSTANCE = new TenantIaasIdpTenantMapper();

    protected TenantIaasIdpTenantMapper() {}

    public Set<String> toIaasTenantIds(Set<UUID> tenantIds) {
        Set<String> iaasTenantIds = null;
        if (tenantIds != null) {
            iaasTenantIds = tenantIds.stream().map(UUID::toString).collect(Collectors.toSet());
        }

        return iaasTenantIds;
    }

    @SuppressWarnings("unchecked")
    public <Idp extends BaseIaasIdpTenant, T extends BaseTenant> List<Idp> fromTenants(List<T> tenants) {
        List<Idp> idpTenants = null;

        if (tenants != null) {
            idpTenants = tenants.stream().map(tenant -> (Idp) fromTenant(tenant)).toList();
        }

        return idpTenants;
    }

    @SuppressWarnings("unchecked")
    public <Idp extends BaseIaasIdpTenant, T extends BaseTenant> Idp fromTenant(T tenant) {
        Idp idpTenant = null;

        if (tenant != null) {
            idpTenant = (Idp) new IaasIdpTenant();

            UUID id = tenant.getId();
            if (id != null) {
                idpTenant.setName(id.toString());
            } else {
                idpTenant.setName(null);
            }
            idpTenant.setIaasRole(null);
        }

        return idpTenant;
    }
}
