package io.trishul.iaas.idp.tenant.model;

import io.trishul.tenant.entity.BaseTenant;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

  public List<? extends BaseIaasIdpTenant<?>> fromTenants(List<? extends BaseTenant<?>> tenants) {
    List<? extends BaseIaasIdpTenant<?>> idpTenants = null;

    if (tenants != null) {
      idpTenants = tenants.stream().map(tenant -> (BaseIaasIdpTenant<?>) fromTenant(tenant)).toList();
    }

    return idpTenants;
  }

  public BaseIaasIdpTenant<?> fromTenant(BaseTenant<?> tenant) {
    BaseIaasIdpTenant<?> idpTenant = null;

    if (tenant != null) {
      idpTenant = (BaseIaasIdpTenant<?>) new IaasIdpTenant();

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
