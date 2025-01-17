package io.trishul.iaas.idp.tenant.model;

import java.time.LocalDateTime;
import io.trishul.base.types.base.pojo.Audited;
import io.trishul.base.types.base.pojo.CrudEntity;
import io.trishul.iaas.access.role.model.IaasRole;
import io.trishul.model.base.pojo.BaseModel;

// TODO: Rename all the tenant stuff in the IdpTenant as IdpGroup
public class IaasIdpTenant extends BaseModel implements UpdateIaasIdpTenant<IaasIdpTenant>,
    CrudEntity<String, IaasIdpTenant>, Audited<IaasIdpTenant> {
  private String name;
  private IaasRole role;
  private String description;
  private LocalDateTime createdAt;
  private LocalDateTime lastUpdated;

  public IaasIdpTenant() {
    super();
  }

  public IaasIdpTenant(String name) {
    this();
    setName(name);
  }

  public IaasIdpTenant(String name, IaasRole role, String description, LocalDateTime createdAt,
      LocalDateTime lastUpdated) {
    this(name);
    setIaasRole(role);
    setDescription(description);
    setCreatedAt(createdAt);
    setLastUpdated(lastUpdated);
  }

  @Override
  public String getId() {
    return getName();
  }

  @Override
  public IaasIdpTenant setId(String id) {
    setName(id);
    return this;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public IaasIdpTenant setName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public String getDescription() {
    return this.description;
  }

  @Override
  public IaasIdpTenant setDescription(String description) {
    this.description = description;
    return this;
  }

  @Override
  public IaasRole getIaasRole() {
    return this.role == null ? null : this.role.deepClone();
  }

  @Override
  public IaasIdpTenant setIaasRole(IaasRole role) {
    this.role = role == null ? null : role.deepClone();
    return this;
  }

  @Override
  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }

  @Override
  public IaasIdpTenant setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  @Override
  public LocalDateTime getLastUpdated() {
    return this.lastUpdated;
  }

  @Override
  public IaasIdpTenant setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }

  @Override
  public Integer getVersion() {
    // Versioning not implemented due to lack of use-case
    return null;
  }

  public IaasIdpTenant setVersion(Integer version) {
    // Versioning not implemented due to lack of use-case
    return this;
  }
}
