package io.trishul.iaas.access.role.model;

import io.trishul.base.types.base.pojo.Audited;
import io.trishul.base.types.base.pojo.CrudEntity;
import io.trishul.model.base.entity.BaseEntity;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IaasRole extends BaseEntity implements UpdateIaasRole, CrudEntity<String>, Audited {
    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(IaasRole.class);

    private String name;
    private String description;
    private String assumePolicyDocument;
    private String iaasResourceName;
    private String iaasId;
    private LocalDateTime lastUsed;
    private LocalDateTime lastUpdated;
    private LocalDateTime createdAt;

    public IaasRole() {
        super();
    }

    public IaasRole(String id) {
        this();
        setId(id);
    }

    public IaasRole(
            String id,
            String description,
            String assumePolicyDocument,
            String iaasResourceName,
            String iaasId,
            LocalDateTime lastUsed,
            LocalDateTime createdAt,
            LocalDateTime lastUpdated) {
        this(id);
        setDescription(description);
        setAssumePolicyDocument(assumePolicyDocument);
        setIaasResourceName(iaasResourceName);
        setIaasId(iaasId);
        setLastUsed(lastUsed);
        setLastUpdated(lastUpdated);
        setCreatedAt(createdAt);
    }

    @Override
    public String getId() {
        return getName();
    }

    @Override
    public final void setId(String id) {
        setName(id);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public final void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public final void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getAssumePolicyDocument() {
        return assumePolicyDocument;
    }

    @Override
    public final void setAssumePolicyDocument(String assumePolicyDocument) {
        this.assumePolicyDocument = assumePolicyDocument;
    }

    @Override
    public String getIaasResourceName() {
        return iaasResourceName;
    }

    @Override
    public final void setIaasResourceName(String iaasResourceName) {
        this.iaasResourceName = iaasResourceName;
    }

    @Override
    public String getIaasId() {
        return iaasId;
    }

    @Override
    public final void setIaasId(String iaasId) {
        this.iaasId = iaasId;
    }

    @Override
    public LocalDateTime getLastUsed() {
        return lastUsed;
    }

    @Override
    public final void setLastUsed(LocalDateTime lastUsed) {
        this.lastUsed = lastUsed;
    }

    @Override
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    @Override
    public final void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public final void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public Integer getVersion() {
        // Versioning not implemented due to lack of use-case
        return null;
    }
}
