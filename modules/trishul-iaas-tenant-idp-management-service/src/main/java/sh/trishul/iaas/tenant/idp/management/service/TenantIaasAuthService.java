package sh.trishul.iaas.tenant.idp.management.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import org.springframework.data.domain.Page;
import sh.trishul.iaas.access.role.model.BaseIaasRole;
import sh.trishul.iaas.access.role.model.IaasRole;
import sh.trishul.iaas.access.role.model.UpdateIaasRole;
import sh.trishul.iaas.access.service.role.service.IaasRoleService;
import sh.trishul.iaas.idp.tenant.model.BaseIaasIdpTenant;
import sh.trishul.iaas.idp.tenant.model.TenantIaasAuthDeleteResult;
import sh.trishul.iaas.idp.tenant.model.TenantIaasAuthResourceMapper;
import sh.trishul.iaas.idp.tenant.model.TenantIaasAuthResources;
import sh.trishul.iaas.idp.tenant.model.UpdateIaasIdpTenant;
import sh.trishul.iaas.tenant.resource.TenantIaasResourceBuilder;

public class TenantIaasAuthService {
  private final TenantIaasResourceBuilder resourceBuilder;
  private final TenantIaasAuthResourceMapper mapper;
  private final IaasRoleService roleService;

  public TenantIaasAuthService(TenantIaasAuthResourceMapper mapper, IaasRoleService roleService,
      TenantIaasResourceBuilder resourceBuilder) {
    this.resourceBuilder = resourceBuilder;
    this.roleService = roleService;
    this.mapper = mapper;
  }

  public List<TenantIaasAuthResources> get(Set<String> idpTenantsIds) {
    Set<String> roleIds = new HashSet<>();

    idpTenantsIds.stream().forEach(idpTenantsId -> {
      String roleName = this.resourceBuilder.getRoleId(idpTenantsId);
      roleIds.add(roleName);
    });

    List<IaasRole> roles = this.roleService.getAll(roleIds);

    return this.mapper.fromComponents(roles);
  }

  public List<TenantIaasAuthResources> add(List<? extends BaseIaasIdpTenant<?>> idpTenants) {
    List<BaseIaasRole<?>> roleUpdates = new ArrayList<>(idpTenants.size());

    idpTenants.forEach(idpTenant -> {
      BaseIaasRole<?> role = this.resourceBuilder.buildRole(idpTenant);
      roleUpdates.add(role);
    });

    List<IaasRole> roles = this.roleService.add(roleUpdates);

    return this.mapper.fromComponents(roles);
  }

  public List<TenantIaasAuthResources> put(List<? extends UpdateIaasIdpTenant<?>> idpTenants) {
    List<UpdateIaasRole<?>> roleUpdates = new ArrayList<>(idpTenants.size());

    idpTenants.forEach(idpTenant -> {
      UpdateIaasRole<?> role = this.resourceBuilder.buildRole(idpTenant);

      roleUpdates.add(role);
    });

    List<IaasRole> roles = this.roleService.put(roleUpdates);

    return this.mapper.fromComponents(roles);
  }

  public TenantIaasAuthDeleteResult delete(Set<String> iaasIdpTenantIds) {
    Set<String> roleIds = new HashSet<>();

    iaasIdpTenantIds.stream().forEach(iaasIdpTenantId -> {
      String roleName = this.resourceBuilder.getRoleId(iaasIdpTenantId);
      roleIds.add(roleName);
    });

    long roleCount = this.roleService.delete(roleIds).getCount();

    return new TenantIaasAuthDeleteResult(roleCount);
  }

  public Page<TenantIaasAuthResources> search(String query, SortedSet<String> sort,
      boolean orderAscending, int page, int size) {
    return Page.empty();
  }
}
