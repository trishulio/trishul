package io.trishul.iaas.access.role.attachment.policy;

public interface IaasRolePolicyAttachmentAccessor<T extends IaasRolePolicyAttachmentAccessor<T>> {
  IaasRolePolicyAttachment getIaasRolePolicyAttachment();

  T setIaasRolePolicyAttachment(IaasRolePolicyAttachment attachment);
}
