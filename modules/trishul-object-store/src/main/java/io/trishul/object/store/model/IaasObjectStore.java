package io.trishul.object.store.model;

import io.trishul.base.types.base.pojo.Audited;
import io.trishul.base.types.base.pojo.CrudEntity;
import io.trishul.model.base.entity.BaseEntity;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IaasObjectStore extends BaseEntity
        implements UpdateIaasObjectStore, CrudEntity<String>, Audited {
    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(IaasObjectStore.class);

    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;

    public IaasObjectStore() {
        super();
    }

    public IaasObjectStore(String id) {
        this();
        setId(id);
    }

    public IaasObjectStore(String name, LocalDateTime createdAt, LocalDateTime lastUpdated) {
        this(name);
        setCreatedAt(createdAt);
        setLastUpdated(lastUpdated);
    }

    @Override
    public final void setId(String id) {
        setName(id);
    }

    @Override
    public String getId() {
        return getName();
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
