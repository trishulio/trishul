package io.trishul.object.store.model;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.trishul.base.types.base.pojo.Audited;
import io.trishul.base.types.base.pojo.CrudEntity;
import io.trishul.model.base.entity.BaseEntity;

public class IaasObjectStore extends BaseEntity implements UpdateIaasObjectStore<IaasObjectStore>,
    CrudEntity<String, IaasObjectStore>, Audited<IaasObjectStore> {
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
  public IaasObjectStore setId(String id) {
    setName(id);
    return this;
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
  public IaasObjectStore setName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  @Override
  public IaasObjectStore setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  @Override
  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  @Override
  public IaasObjectStore setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }

  @Override
  public Integer getVersion() {
    // Versioning not implemented due to lack of use-case
    return null;
  }

  public IaasObjectStore setVersion(Integer version) {
    // Versioning not implemented due to lack of use-case
    return this;
  }
}
