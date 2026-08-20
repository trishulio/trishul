package sh.trishul.iaas.user.model;

public interface BaseIaasUserTenantMembership<T extends BaseIaasUserTenantMembership<T>> {
  String ATTR_USER = "user";
  String ATTR_TENANT_ID = "tenantId";

  IaasUser getUser();

  T setUser(IaasUser user);

  String getTenantId();

  T setTenantId(String tenantId);
}
