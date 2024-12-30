package io.trishul.user.salutation.model;

import java.time.LocalDateTime;
import io.trishul.model.base.dto.BaseDto;

public class UserSalutationDto extends BaseDto {
  private Long id;

  private String title;

  private LocalDateTime createdAt;

  private LocalDateTime lastUpdated;

  private Integer version;

  public UserSalutationDto() {}

  public UserSalutationDto(Long id) {
    setId(id);
  }

  public UserSalutationDto(Long id, String title, LocalDateTime createdAt,
      LocalDateTime lastUpdated, Integer version) {
    this(id);
    setTitle(title);
    setCreatedAt(createdAt);
    setLastUpdated(lastUpdated);
    setVersion(version);
  }

  public Long getId() {
    return id;
  }

  public final UserSalutationDto setId(Long id) {
    this.id = id;
    return this;
  }

  public String getTitle() {
    return title;
  }

  public final UserSalutationDto setTitle(String title) {
    this.title = title;
    return this;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public final UserSalutationDto setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  public final UserSalutationDto setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }

  public Integer getVersion() {
    return version;
  }

  public final UserSalutationDto setVersion(Integer version) {
    this.version = version;
    return this;
  }
}
