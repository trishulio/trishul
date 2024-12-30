package io.trishul.iaas.access.role.attachment.policy;

import io.trishul.iaas.access.policy.model.IaasPolicyAccessor;
import io.trishul.iaas.access.role.model.IaasRoleAccessor;

public interface BaseIaasRolePolicyAttachment<T extends BaseIaasRolePolicyAttachment<T>>
    extends IaasRoleAccessor<T>, IaasPolicyAccessor<T> {
}
