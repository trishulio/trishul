package io.trishul.user.role.model;

import java.time.LocalDateTime;
import io.trishul.model.base.dto.BaseDto;

public class UserRoleDto extends BaseDto {
  private Long id;

  private String name;

  private LocalDateTime createdAt;

  private LocalDateTime lastUpdated;

  private Integer version;

  public UserRoleDto() {}

  public UserRoleDto(Long id) {
    setId(id);
  }

  public UserRoleDto(Long id, String name, LocalDateTime createdAt, LocalDateTime lastUpdated,
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

  public UserRoleDto setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public UserRoleDto setName(String name) {
    this.name = name;
    return this;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public UserRoleDto setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  public UserRoleDto setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }

  public Integer getVersion() {
    return version;
  }

  public UserRoleDto setVersion(Integer version) {
    this.version = version;
    return this;
  }
}
