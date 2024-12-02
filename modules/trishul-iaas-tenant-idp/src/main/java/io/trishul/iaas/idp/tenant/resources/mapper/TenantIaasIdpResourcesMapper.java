package io.trishul.iaas.idp.tenant.resources.mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.trishul.iaas.idp.tenant.model.IaasIdpTenant;
import io.trishul.iaas.idp.tenant.resources.TenantIaasIdpResources;

public class TenantIaasIdpResourcesMapper {
    public static final TenantIaasIdpResourcesMapper INSTANCE = new TenantIaasIdpResourcesMapper();

    protected TenantIaasIdpResourcesMapper() {}

    public List<TenantIaasIdpResources> fromComponents(List<IaasIdpTenant> idpTenants) {
        List<TenantIaasIdpResources> resources = new ArrayList<>();

        Iterator<IaasIdpTenant> idpTenantsIterator = idpTenants.iterator();
        while (idpTenantsIterator.hasNext()) {
            resources.add(fromComponents(idpTenantsIterator.next()));
        }

        return resources;
    }

    public TenantIaasIdpResources fromComponents(IaasIdpTenant idpTenant) {
        return new TenantIaasIdpResources(idpTenant);
    }
}
