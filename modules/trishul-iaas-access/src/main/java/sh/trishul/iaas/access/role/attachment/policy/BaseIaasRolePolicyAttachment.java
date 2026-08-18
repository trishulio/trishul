package sh.trishul.iaas.access.role.attachment.policy;

import sh.trishul.iaas.access.policy.model.IaasPolicyAccessor;
import sh.trishul.iaas.access.role.model.IaasRoleAccessor;

public interface BaseIaasRolePolicyAttachment<T extends BaseIaasRolePolicyAttachment<T>>
    extends IaasRoleAccessor<T>, IaasPolicyAccessor<T> {
}
