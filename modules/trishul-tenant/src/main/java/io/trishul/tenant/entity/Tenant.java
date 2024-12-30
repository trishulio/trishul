package io.trishul.tenant.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.trishul.base.types.base.pojo.Audited;
import io.trishul.base.types.base.pojo.CrudEntity;
import io.trishul.model.base.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "tenant")
@Table(name = "TENANT")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Tenant extends BaseEntity
    implements UpdateTenant<Tenant>, CrudEntity<UUID, Tenant>, Audited<Tenant> {
  @Id
  private UUID id;

  @Column(name = "name")
  private String name;

  @Column(name = "url")
  private URL url;

  @Column(name = "is_ready")
  private Boolean isReady;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "last_updated")
  private LocalDateTime lastUpdated;

  public Tenant() {
    super();
    this.isReady = false;
  }

  public Tenant(UUID id) {
    this();
    setId(id);
  }

  public Tenant(UUID id, String name, URL url, Boolean isReady, LocalDateTime createdAt,
      LocalDateTime lastUpdated) {
    this(id);
    this.name = name;
    this.url = url;
    this.isReady = isReady;
    this.createdAt = createdAt;
    this.lastUpdated = lastUpdated;
  }

  @PrePersist
  public final void setId() {
    if (this.id == null) {
      this.id = UUID.randomUUID();
    }
  }

  @Override
  public UUID getId() {
    return id;
  }

  @Override
  public final Tenant setId(UUID id) {
    this.id = id;
    return this;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public final Tenant setName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public URL getUrl() {
    return url;
  }

  @Override
  public final Tenant setUrl(URL url) {
    this.url = url;
    return this;
  }

  @Override
  public Boolean getIsReady() {
    return isReady;
  }

  @Override
  public final Tenant setIsReady(Boolean isReady) {
    this.isReady = isReady;
    return this;
  }

  @Override
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  @Override
  public final Tenant setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  @Override
  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  @Override
  public final Tenant setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }

  @Override
  public Integer getVersion() {
    // Versioning not implemented due to lack of use-case
    return null;
  }
}
