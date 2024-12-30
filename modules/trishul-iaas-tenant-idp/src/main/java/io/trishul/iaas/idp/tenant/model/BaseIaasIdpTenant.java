package io.trishul.iaas.idp.tenant.model;

import io.trishul.iaas.access.role.model.IaasRoleAccessor;

public interface BaseIaasIdpTenant<T extends BaseIaasIdpTenant<T>> extends IaasRoleAccessor<T> {
  final String ATTR_NAME = "name";
  final String ATTR_DESCRIPTION = "description";

  String getName();

  T setName(String name);

  String getDescription();

  T setDescription(String description);
}
