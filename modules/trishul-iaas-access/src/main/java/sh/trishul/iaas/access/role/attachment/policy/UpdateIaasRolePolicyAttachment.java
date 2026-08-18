package sh.trishul.iaas.access.role.attachment.policy;

import sh.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateIaasRolePolicyAttachment<T extends UpdateIaasRolePolicyAttachment<T>>
    extends BaseIaasRolePolicyAttachment<T>, UpdatableEntity<IaasRolePolicyAttachmentId, T> {
}
