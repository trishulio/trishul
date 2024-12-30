package io.trishul.iaas.access.role.model;

public interface IaasRoleAccessor<T extends IaasRoleAccessor<T>> {
  final String ATTR_IAAS_ROLE = "iaasRole";

  IaasRole getIaasRole();

  T setIaasRole(IaasRole iaasRole);
}
