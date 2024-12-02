package io.trishul.iaas.access.role.model;

public interface IaasRoleAccessor {
    final String ATTR_IAAS_ROLE = "iaasRole";

    IaasRole getIaasRole();

    void setIaasRole(IaasRole iaasRole);
}
