package io.trishul.iaas.access.role.attachment.policy;

import io.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateIaasRolePolicyAttachment<T extends UpdateIaasRolePolicyAttachment<T>>
    extends BaseIaasRolePolicyAttachment<T>, UpdatableEntity<IaasRolePolicyAttachmentId, T> {
}
