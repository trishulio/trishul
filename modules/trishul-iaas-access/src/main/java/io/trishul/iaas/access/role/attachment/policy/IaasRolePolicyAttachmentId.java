package io.trishul.iaas.access.role.attachment.policy;

import io.trishul.iaas.access.policy.model.IaasPolicy;
import io.trishul.iaas.access.role.model.IaasRole;
import io.trishul.model.base.pojo.BaseModel;

public class IaasRolePolicyAttachmentId extends BaseModel {
    private String policyId;
    private String roleId;

    public IaasRolePolicyAttachmentId() {
    }

    public IaasRolePolicyAttachmentId(String roleId, String policyId) {
        this();
        setPolicyId(policyId);
        setRoleId(roleId);
    }

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public static IaasRolePolicyAttachmentId build(IaasRole role, IaasPolicy policy) {
        IaasRolePolicyAttachmentId id = null;

        if (role != null || policy != null) {
            id = new IaasRolePolicyAttachmentId();
        }

        if (role != null ) {
            id.setRoleId(role.getId());
        }

        if (policy != null) {
            id.setPolicyId(policy.getId());
        }

        return id;
    }
}
