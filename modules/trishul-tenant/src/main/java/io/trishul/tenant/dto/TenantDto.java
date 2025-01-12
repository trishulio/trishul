package io.trishul.tenant.dto;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;
import io.trishul.model.base.dto.BaseDto;

public class TenantDto extends BaseDto {
  private UUID id;
  private String name;
  private URL url;
  private Boolean isReady;
  private LocalDateTime createdAt;
  private LocalDateTime lastUpdated;

  public TenantDto() {
    super();
  }

  public TenantDto(UUID id) {
    this();
    setId(id);
  }

  public TenantDto(UUID id, String name, URL url, Boolean isReady, LocalDateTime createdAt,
      LocalDateTime lastUpdated) {
    this(id);
    setName(name);
    setUrl(url);
    setIsReady(isReady);
    setCreatedAt(createdAt);
    setLastUpdated(lastUpdated);
  }

  public UUID getId() {
    return id;
  }

  public TenantDto setId(UUID id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public TenantDto setName(String name) {
    this.name = name;
    return this;
  }

  public URL getUrl() {
    return url;
  }

  public TenantDto setUrl(URL url) {
    this.url = url;
    return this;
  }

  public Boolean getIsReady() {
    return isReady;
  }

  public TenantDto setIsReady(Boolean isReady) {
    this.isReady = isReady;
    return this;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public TenantDto setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  public TenantDto setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }
}
