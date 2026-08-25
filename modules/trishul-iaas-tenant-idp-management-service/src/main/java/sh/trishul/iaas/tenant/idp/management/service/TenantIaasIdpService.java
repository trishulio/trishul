package sh.trishul.iaas.tenant.idp.management.service;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import org.springframework.data.domain.Page;
import sh.trishul.iaas.idp.tenant.model.BaseIaasIdpTenant;
import sh.trishul.iaas.idp.tenant.model.IaasIdpTenant;
import sh.trishul.iaas.idp.tenant.model.TenantIaasIdpDeleteResult;
import sh.trishul.iaas.idp.tenant.model.TenantIaasIdpResources;
import sh.trishul.iaas.idp.tenant.model.UpdateIaasIdpTenant;
import sh.trishul.iaas.idp.tenant.model.mapper.TenantIaasIdpResourcesMapper;

public class TenantIaasIdpService {
  private final IaasIdpTenantService idpService;
  private final TenantIaasIdpResourcesMapper mapper;

  public TenantIaasIdpService(IaasIdpTenantService idpService,
      TenantIaasIdpResourcesMapper mapper) {
    this.idpService = idpService;
    this.mapper = mapper;
  }

  public List<TenantIaasIdpResources> get(Set<String> iaasIdpTenantIds) {
    List<IaasIdpTenant> idpTenants = this.idpService.getAll(iaasIdpTenantIds);
    return this.mapper.fromComponents(idpTenants);
  }

  public List<TenantIaasIdpResources> add(List<? extends BaseIaasIdpTenant<?>> tenants) {
    List<IaasIdpTenant> idpTenants = this.idpService.add(tenants);

    return this.mapper.fromComponents(idpTenants);
  }

  public List<TenantIaasIdpResources> put(List<? extends UpdateIaasIdpTenant<?>> tenants) {
    List<IaasIdpTenant> idpTenants = this.idpService.put(tenants);

    return this.mapper.fromComponents(idpTenants);
  }

  public TenantIaasIdpDeleteResult delete(Set<String> iaasIdpTenantIds) {

    long idpCount = this.idpService.delete(iaasIdpTenantIds).getCount();

    return new TenantIaasIdpDeleteResult(idpCount);
  }

  public boolean exist(String iaasIdpTenantId) {
    return this.idpService.exist(iaasIdpTenantId);
  }

  public Page<TenantIaasIdpResources> search(String query, SortedSet<String> sort,
      boolean orderAscending, int page, int size) {
    return Page.empty();
  }
}
