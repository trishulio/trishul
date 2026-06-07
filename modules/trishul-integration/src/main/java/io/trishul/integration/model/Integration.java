package io.trishul.integration.model;

import io.trishul.base.types.base.pojo.Audited;
import io.trishul.base.types.base.pojo.CrudEntity;
import io.trishul.model.base.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "integration")
@Table(name = "integration")
public class Integration extends BaseEntity
    implements CrudEntity<Long, Integration>, UpdateIntegration<Integration>, Audited<Integration> {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "integration_generator")
  @SequenceGenerator(name = "integration_generator", sequenceName = "integration_sequence",
      allocationSize = 1)
  private Long id;

  @Column(name = "name")
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private IntegrationType type;

  @Column(name = "provider")
  private String provider;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private IntegrationStatus status;

  @Column(name = "configuration", columnDefinition = "TEXT")
  private String configuration;

  @Version
  private Integer version;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "last_updated")
  private LocalDateTime lastUpdated;

  public Integration() {}

  public Integration(Long id) {
    this();
    setId(id);
  }

  public Integration(Long id, String name, IntegrationType type, String provider,
      IntegrationStatus status, String configuration, LocalDateTime createdAt,
      LocalDateTime lastUpdated, Integer version) {
    this(id);
    setName(name);
    setType(type);
    setProvider(provider);
    setStatus(status);
    setConfiguration(configuration);
    setCreatedAt(createdAt);
    setLastUpdated(lastUpdated);
    setVersion(version);
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public Integration setId(Long id) {
    this.id = id;
    return this;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Integration setName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public IntegrationType getType() {
    return type;
  }

  @Override
  public Integration setType(IntegrationType type) {
    this.type = type;
    return this;
  }

  @Override
  public String getProvider() {
    return provider;
  }

  @Override
  public Integration setProvider(String provider) {
    this.provider = provider;
    return this;
  }

  @Override
  public IntegrationStatus getStatus() {
    return status;
  }

  @Override
  public Integration setStatus(IntegrationStatus status) {
    this.status = status;
    return this;
  }

  @Override
  public String getConfiguration() {
    return configuration;
  }

  @Override
  public Integration setConfiguration(String configuration) {
    this.configuration = configuration;
    return this;
  }

  @Override
  public Integer getVersion() {
    return version;
  }

  public Integration setVersion(Integer version) {
    this.version = version;
    return this;
  }

  @Override
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  @Override
  public Integration setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  @Override
  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  @Override
  public Integration setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }
}
