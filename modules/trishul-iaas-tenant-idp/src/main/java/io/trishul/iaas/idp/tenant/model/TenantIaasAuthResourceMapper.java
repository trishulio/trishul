package io.trishul.iaas.idp.tenant.model;

import io.trishul.iaas.access.role.model.IaasRole;
import java.util.ArrayList;
import java.util.List;

public class TenantIaasAuthResourceMapper {
  public static final TenantIaasAuthResourceMapper INSTANCE = new TenantIaasAuthResourceMapper();

  protected TenantIaasAuthResourceMapper() {}

  public List<TenantIaasAuthResources> fromComponents(List<IaasRole> roles) {
    List<TenantIaasAuthResources> resources = null;

    if (roles != null) {
      resources = new ArrayList<>(roles.size());

      for (int i = 0; i < roles.size(); i++) {
        TenantIaasAuthResources resource = new TenantIaasAuthResources(roles.get(i));
        resources.add(resource);
      }
    }

    return resources;
  }
}
