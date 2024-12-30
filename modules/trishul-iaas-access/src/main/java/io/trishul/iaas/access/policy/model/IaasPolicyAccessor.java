package io.trishul.iaas.access.policy.model;

public interface IaasPolicyAccessor<T extends IaasPolicyAccessor<T>> {
  IaasPolicy getIaasPolicy();

  T setIaasPolicy(IaasPolicy tenantPolicy);
}
