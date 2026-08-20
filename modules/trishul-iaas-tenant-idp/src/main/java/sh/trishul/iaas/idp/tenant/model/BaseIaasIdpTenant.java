package sh.trishul.iaas.idp.tenant.model;

import sh.trishul.iaas.access.role.model.IaasRoleAccessor;

public interface BaseIaasIdpTenant<T extends BaseIaasIdpTenant<T>> extends IaasRoleAccessor<T> {
  String ATTR_NAME = "name";
  String ATTR_DESCRIPTION = "description";

  String getName();

  T setName(String name);

  String getDescription();

  T setDescription(String description);
}
