package io.trishul.iaas.tenant.idp.management.service;

import io.trishul.iaas.access.role.model.BaseIaasRole;
import io.trishul.iaas.access.role.model.IaasRole;
import io.trishul.iaas.access.role.model.UpdateIaasRole;
import io.trishul.iaas.access.service.role.service.IaasRoleService;
import io.trishul.iaas.idp.tenant.model.BaseIaasIdpTenant;
import io.trishul.iaas.idp.tenant.model.TenantIaasAuthDeleteResult;
import io.trishul.iaas.idp.tenant.model.TenantIaasAuthResourceMapper;
import io.trishul.iaas.idp.tenant.model.TenantIaasAuthResources;
import io.trishul.iaas.idp.tenant.model.UpdateIaasIdpTenant;
import io.trishul.iaas.tenant.resource.TenantIaasResourceBuilder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
      // Hack: The returned type is IaasRole and hence can be casted. If that changes, split up the
      // buildRole
      // into two methods on the resourceBuilder. The buildRole is used in this class for the add
      // method.
      UpdateIaasRole<?> role = (UpdateIaasRole<?>) this.resourceBuilder.buildRole(idpTenant);

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

    long roleCount = this.roleService.delete(roleIds);

    return new TenantIaasAuthDeleteResult(roleCount);
  }
}
