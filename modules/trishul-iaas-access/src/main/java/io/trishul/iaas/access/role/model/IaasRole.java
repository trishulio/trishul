package io.trishul.iaas.access.role.model;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.trishul.model.base.entity.BaseEntity;
import io.trishul.model.base.pojo.Audited;
import io.trishul.model.base.pojo.CrudEntity;

public class IaasRole extends BaseEntity implements UpdateIaasRole, CrudEntity<String>, Audited {
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

    public IaasRole(String id, String description, String assumePolicyDocument, String iaasResourceName, String iaasId, LocalDateTime lastUsed, LocalDateTime createdAt, LocalDateTime lastUpdated) {
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
    public void setId(String id) {
        setName(id);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getAssumePolicyDocument() {
        return assumePolicyDocument;
    }

    @Override
    public void setAssumePolicyDocument(String assumePolicyDocument) {
        this.assumePolicyDocument = assumePolicyDocument;
    }

    @Override
    public String getIaasResourceName() {
        return iaasResourceName;
    }

    @Override
    public void setIaasResourceName(String iaasResourceName) {
        this.iaasResourceName = iaasResourceName;
    }

    @Override
    public String getIaasId() {
        return iaasId;
    }

    @Override
    public void setIaasId(String iaasId) {
        this.iaasId = iaasId;
    }

    @Override
    public LocalDateTime getLastUsed() {
        return lastUsed;
    }

    @Override
    public void setLastUsed(LocalDateTime lastUsed) {
        this.lastUsed = lastUsed;
    }

    @Override
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    @Override
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public Integer getVersion() {
        // Versioning not implemented due to lack of use-case
        return null;
    }
}