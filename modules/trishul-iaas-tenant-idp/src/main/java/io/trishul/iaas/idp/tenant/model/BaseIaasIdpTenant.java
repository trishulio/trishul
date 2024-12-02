package io.trishul.iaas.idp.tenant.model;

import io.trishul.iaas.access.role.model.IaasRoleAccessor;

public interface BaseIaasIdpTenant extends IaasRoleAccessor {
    final String ATTR_NAME = "name";
    final String ATTR_DESCRIPTION = "description";

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);
}
