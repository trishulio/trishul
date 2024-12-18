package io.trishul.iaas.access.role.attachment.policy;

import io.trishul.base.types.base.pojo.Audited;
import io.trishul.base.types.base.pojo.CrudEntity;
import io.trishul.iaas.access.policy.model.IaasPolicy;
import io.trishul.iaas.access.role.model.IaasRole;
import io.trishul.model.base.entity.BaseEntity;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IaasRolePolicyAttachment extends BaseEntity
        implements UpdateIaasRolePolicyAttachment, CrudEntity<IaasRolePolicyAttachmentId>, Audited {
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

    public IaasRolePolicyAttachment(
            IaasRole role, IaasPolicy policy, LocalDateTime createdAt, LocalDateTime lastUpdated) {
        this(role, policy);
        setCreatedAt(createdAt);
        setLastUpdated(lastUpdated);
    }

    @Override
    public IaasRolePolicyAttachmentId getId() {
        return IaasRolePolicyAttachmentId.build(this.role, this.policy);
    }

    @Override
    public final void setId(IaasRolePolicyAttachmentId id) {
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
    }

    @Override
    public IaasRole getIaasRole() {
        return this.role;
    }

    @Override
    public final void setIaasRole(IaasRole role) {
        this.role = role;
    }

    @Override
    public IaasPolicy getIaasPolicy() {
        return this.policy;
    }

    @Override
    public final void setIaasPolicy(IaasPolicy policy) {
        this.policy = policy;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public final void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public LocalDateTime getLastUpdated() {
        return this.lastUpdated;
    }

    @Override
    public final void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public Integer getVersion() {
        // Versioning not implemented due to lack of use-case
        return null;
    }
}
