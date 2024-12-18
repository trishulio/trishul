package io.trishul.iaas.access.policy.model;

import io.trishul.base.types.base.pojo.Audited;
import io.trishul.base.types.base.pojo.CrudEntity;
import io.trishul.model.base.entity.BaseEntity;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IaasPolicy extends BaseEntity
        implements UpdateIaasPolicy, CrudEntity<String>, Audited {
    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(IaasPolicy.class);

    private String name;
    private String document;
    private String description;
    private String iaasResourceName;
    private String iaasId;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;

    public IaasPolicy() {
        super();
    }

    public IaasPolicy(String id) {
        this();
        setId(id);
    }

    public IaasPolicy(
            String id,
            String document,
            String description,
            String iaasResourceName,
            String iaasId,
            LocalDateTime createdAt,
            LocalDateTime lastUpdated) {
        this(id);
        setDocument(document);
        setDescription(description);
        setIaasId(iaasId);
        setIaasResourceName(iaasResourceName);
        setCreatedAt(createdAt);
        setLastUpdated(lastUpdated);
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
    public String getIaasId() {
        return iaasId;
    }

    @Override
    public final void setIaasId(String iaasId) {
        this.iaasId = iaasId;
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
    public String getDocument() {
        return document;
    }

    @Override
    public final void setDocument(String document) {
        this.document = document;
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
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public final void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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
    public Integer getVersion() {
        // Versioning not implemented due to lack of use-case
        return null;
    }
}
