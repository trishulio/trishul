package io.trishul.user.status;

import java.time.LocalDateTime;
import io.trishul.model.base.dto.BaseDto;

public class UserStatusDto extends BaseDto {
  private Long id;

  private String name;

  private LocalDateTime createdAt;

  private LocalDateTime lastUpdated;

  private Integer version;

  public UserStatusDto() {}

  public UserStatusDto(Long id) {
    setId(id);
  }

  public UserStatusDto(Long id, String name, LocalDateTime createdAt, LocalDateTime lastUpdated,
      Integer version) {
    this(id);
    setName(name);
    setCreatedAt(createdAt);
    setLastUpdated(lastUpdated);
    setVersion(version);
  }

  public Long getId() {
    return id;
  }

  public UserStatusDto setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public UserStatusDto setName(String name) {
    this.name = name;
    return this;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public UserStatusDto setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  public UserStatusDto setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }

  public Integer getVersion() {
    return version;
  }

  public UserStatusDto setVersion(Integer version) {
    this.version = version;
    return this;
  }
}
