package io.trishul.iaas.user.model;

public interface BaseIaasUserTenantMembership {
    final String ATTR_USER = "user";
    final String ATTR_TENANT_ID = "tenantId";

    IaasUser getUser();

    void setUser(IaasUser user);

    String getTenantId();

    void setTenantId(String tenantId);
}
