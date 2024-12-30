package io.trishul.iaas.access.policy.model;

import io.trishul.base.types.base.pojo.Audited;
import io.trishul.base.types.base.pojo.CrudEntity;
import io.trishul.model.base.entity.BaseEntity;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IaasPolicy extends BaseEntity
    implements UpdateIaasPolicy<IaasPolicy>, CrudEntity<String, IaasPolicy>, Audited<IaasPolicy> {
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

  public IaasPolicy(String id, String document, String description, String iaasResourceName,
      String iaasId, LocalDateTime createdAt, LocalDateTime lastUpdated) {
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
  public final IaasPolicy setId(String id) {
    setName(id);
    return this;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public final IaasPolicy setName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public String getIaasId() {
    return iaasId;
  }

  @Override
  public final IaasPolicy setIaasId(String iaasId) {
    this.iaasId = iaasId;
    return this;
  }

  @Override
  public String getIaasResourceName() {
    return iaasResourceName;
  }

  @Override
  public final IaasPolicy setIaasResourceName(String iaasResourceName) {
    this.iaasResourceName = iaasResourceName;
    return this;
  }

  @Override
  public String getDocument() {
    return document;
  }

  @Override
  public final IaasPolicy setDocument(String document) {
    this.document = document;
    return this;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public final IaasPolicy setDescription(String description) {
    this.description = description;
    return this;
  }

  @Override
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  @Override
  public final IaasPolicy setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  @Override
  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  @Override
  public final IaasPolicy setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }

  @Override
  public Integer getVersion() {
    // Versioning not implemented due to lack of use-case
    return null;
  }
}
