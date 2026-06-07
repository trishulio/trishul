package io.trishul.integration.model;

import io.trishul.model.base.dto.BaseDto;
import java.time.LocalDateTime;

public class IntegrationDto extends BaseDto {
  private Long id;
  private String name;
  private IntegrationType type;
  private String provider;
  private IntegrationStatus status;
  private String configuration;
  private LocalDateTime createdAt;
  private LocalDateTime lastUpdated;
  private Integer version;

  public IntegrationDto() {}

  public IntegrationDto(Long id) {
    this();
    setId(id);
  }

  public IntegrationDto(Long id, String name, IntegrationType type, String provider,
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

  public Long getId() {
    return id;
  }

  public IntegrationDto setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public IntegrationDto setName(String name) {
    this.name = name;
    return this;
  }

  public IntegrationType getType() {
    return type;
  }

  public IntegrationDto setType(IntegrationType type) {
    this.type = type;
    return this;
  }

  public String getProvider() {
    return provider;
  }

  public IntegrationDto setProvider(String provider) {
    this.provider = provider;
    return this;
  }

  public IntegrationStatus getStatus() {
    return status;
  }

  public IntegrationDto setStatus(IntegrationStatus status) {
    this.status = status;
    return this;
  }

  public String getConfiguration() {
    return configuration;
  }

  public IntegrationDto setConfiguration(String configuration) {
    this.configuration = configuration;
    return this;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public IntegrationDto setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  public IntegrationDto setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }

  public Integer getVersion() {
    return version;
  }

  public IntegrationDto setVersion(Integer version) {
    this.version = version;
    return this;
  }
}
