package io.trishul.iaas.access.role.model;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.trishul.base.types.base.pojo.Audited;
import io.trishul.base.types.base.pojo.CrudEntity;
import io.trishul.model.base.entity.BaseEntity;

public class IaasRole extends BaseEntity
    implements UpdateIaasRole<IaasRole>, CrudEntity<String, IaasRole>, Audited<IaasRole> {
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

  public IaasRole(String id, String description, String assumePolicyDocument,
      String iaasResourceName, String iaasId, LocalDateTime lastUsed, LocalDateTime createdAt,
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
  public IaasRole setId(String id) {
    setName(id);
    return this;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public IaasRole setName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public IaasRole setDescription(String description) {
    this.description = description;
    return this;
  }

  @Override
  public String getAssumePolicyDocument() {
    return assumePolicyDocument;
  }

  @Override
  public IaasRole setAssumePolicyDocument(String assumePolicyDocument) {
    this.assumePolicyDocument = assumePolicyDocument;
    return this;
  }

  @Override
  public String getIaasResourceName() {
    return iaasResourceName;
  }

  @Override
  public IaasRole setIaasResourceName(String iaasResourceName) {
    this.iaasResourceName = iaasResourceName;
    return this;
  }

  @Override
  public String getIaasId() {
    return iaasId;
  }

  @Override
  public IaasRole setIaasId(String iaasId) {
    this.iaasId = iaasId;
    return this;
  }

  @Override
  public LocalDateTime getLastUsed() {
    return lastUsed;
  }

  @Override
  public IaasRole setLastUsed(LocalDateTime lastUsed) {
    this.lastUsed = lastUsed;
    return this;
  }

  @Override
  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  @Override
  public IaasRole setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }

  @Override
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  @Override
  public IaasRole setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  @Override
  public Integer getVersion() {
    // Versioning not implemented due to lack of use-case
    return null;
  }

  public IaasRole setVersion(Integer version) {
    // Versioning not implemented due to lack of use-case
    return this;
  }
}
