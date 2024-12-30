package io.trishul.iaas.user.model;

public interface BaseIaasUserTenantMembership<T extends BaseIaasUserTenantMembership<T>> {
  final String ATTR_USER = "user";
  final String ATTR_TENANT_ID = "tenantId";

  IaasUser getUser();

  T setUser(IaasUser user);

  String getTenantId();

  T setTenantId(String tenantId);
}
