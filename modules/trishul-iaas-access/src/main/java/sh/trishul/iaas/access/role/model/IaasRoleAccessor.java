package sh.trishul.iaas.access.role.model;

public interface IaasRoleAccessor<T extends IaasRoleAccessor<T>> {
  String ATTR_IAAS_ROLE = "iaasRole";

  IaasRole getIaasRole();

  T setIaasRole(IaasRole iaasRole);
}
