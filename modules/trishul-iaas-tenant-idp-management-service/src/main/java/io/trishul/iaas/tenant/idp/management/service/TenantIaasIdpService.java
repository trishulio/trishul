package io.trishul.iaas.tenant.idp.management.service;

import io.trishul.iaas.idp.tenant.model.BaseIaasIdpTenant;
import io.trishul.iaas.idp.tenant.model.IaasIdpTenant;
import io.trishul.iaas.idp.tenant.model.TenantIaasIdpDeleteResult;
import io.trishul.iaas.idp.tenant.model.TenantIaasIdpResources;
import io.trishul.iaas.idp.tenant.model.UpdateIaasIdpTenant;
import io.trishul.iaas.idp.tenant.model.mapper.TenantIaasIdpResourcesMapper;
import java.util.List;
import java.util.Set;

public class TenantIaasIdpService {
    private final IaasIdpTenantService idpService;
    private final TenantIaasIdpResourcesMapper mapper;

    public TenantIaasIdpService(
            IaasIdpTenantService idpService, TenantIaasIdpResourcesMapper mapper) {
        this.idpService = idpService;
        this.mapper = mapper;
    }

    public List<TenantIaasIdpResources> get(Set<String> iaasIdpTenantIds) {
        List<IaasIdpTenant> idpTenants = this.idpService.getAll(iaasIdpTenantIds);
        return this.mapper.fromComponents(idpTenants);
    }

    public List<TenantIaasIdpResources> add(List<BaseIaasIdpTenant> tenants) {
        List<IaasIdpTenant> idpTenants = this.idpService.add(tenants);

        return this.mapper.fromComponents(idpTenants);
    }

    public List<TenantIaasIdpResources> put(List<UpdateIaasIdpTenant> tenants) {
        List<IaasIdpTenant> idpTenants = this.idpService.put(tenants);

        return this.mapper.fromComponents(idpTenants);
    }

    public TenantIaasIdpDeleteResult delete(Set<String> iaasIdpTenantIds) {

        long idpCount = this.idpService.delete(iaasIdpTenantIds);

        return new TenantIaasIdpDeleteResult(idpCount);
    }

    public boolean exist(String iaasIdpTenantId) {
        return this.idpService.exist(iaasIdpTenantId);
    }
}
