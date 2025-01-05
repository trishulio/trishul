package io.trishul.iaas.access.role.attachment.policy;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.trishul.base.types.base.pojo.Audited;
import io.trishul.base.types.base.pojo.CrudEntity;
import io.trishul.iaas.access.policy.model.IaasPolicy;
import io.trishul.iaas.access.role.model.IaasRole;
import io.trishul.model.base.entity.BaseEntity;

public class IaasRolePolicyAttachment extends BaseEntity
    implements UpdateIaasRolePolicyAttachment<IaasRolePolicyAttachment>,
    CrudEntity<IaasRolePolicyAttachmentId, IaasRolePolicyAttachment>,
    Audited<IaasRolePolicyAttachment> {
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(IaasRolePolicyAttachment.class);

  private IaasRole role;
  private IaasPolicy policy;
  private LocalDateTime createdAt;
  private LocalDateTime lastUpdated;

  public IaasRolePolicyAttachment() {
    super();
  }

  public IaasRolePolicyAttachment(IaasRolePolicyAttachmentId id) {
    this();
    setId(id);
  }

  public IaasRolePolicyAttachment(IaasRole role, IaasPolicy policy) {
    this();
    setIaasRole(role);
    setIaasPolicy(policy);
  }

  public IaasRolePolicyAttachment(IaasRole role, IaasPolicy policy, LocalDateTime createdAt,
      LocalDateTime lastUpdated) {
    this(role, policy);
    setCreatedAt(createdAt);
    setLastUpdated(lastUpdated);
  }

  @Override
  public IaasRolePolicyAttachmentId getId() {
    return IaasRolePolicyAttachmentId.build(this.role, this.policy);
  }

  @Override
  public final IaasRolePolicyAttachment setId(IaasRolePolicyAttachmentId id) {
    if (id == null) {
      if (this.role != null) {
        this.role.setId(null);
      }

      if (this.policy != null) {
        this.policy.setId(null);
      }
    } else {
      if (this.role == null) {
        this.role = new IaasRole();
      }

      if (this.policy == null) {
        this.policy = new IaasPolicy();
      }

      this.role.setId(id.getRoleId());
      this.policy.setId(id.getPolicyId());
    }
    return this;
  }

  @Override
  public IaasRole getIaasRole() {
    return this.role == null ? null : this.role.deepClone();
  }

  @Override
  public final IaasRolePolicyAttachment setIaasRole(IaasRole role) {
    this.role = role == null ? null : role.deepClone();
    return this;
  }

  @Override
  public IaasPolicy getIaasPolicy() {
    return this.policy == null ? null : this.policy.deepClone();
  }

  @Override
  public final IaasRolePolicyAttachment setIaasPolicy(IaasPolicy policy) {
    this.policy = policy == null ? null : policy.deepClone();
    return this;
  }

  @Override
  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }

  @Override
  public final IaasRolePolicyAttachment setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  @Override
  public LocalDateTime getLastUpdated() {
    return this.lastUpdated;
  }

  @Override
  public final IaasRolePolicyAttachment setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }

  @Override
  public Integer getVersion() {
    // Versioning not implemented due to lack of use-case
    return null;
  }

  public IaasRolePolicyAttachment setVersion(Integer version) {
    // Versioning not implemented due to lack of use-case
    return this;
  }
}
