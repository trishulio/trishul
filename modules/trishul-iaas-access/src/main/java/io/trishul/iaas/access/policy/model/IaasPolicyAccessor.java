package io.trishul.iaas.access.policy.model;

public interface IaasPolicyAccessor {
    IaasPolicy getIaasPolicy();

    void setIaasPolicy(IaasPolicy tenantPolicy);
}
