package io.trishul.ai.skill.model;

import io.trishul.model.base.dto.BaseDto;
import java.time.LocalDateTime;

public class AiSkillDto extends BaseDto {
  private Long id;

  private String name;

  private String description;

  private String systemPrompt;

  private Boolean isEnabled;

  private LocalDateTime createdAt;

  private LocalDateTime lastUpdated;

  private Integer version;

  public AiSkillDto() {}

  public AiSkillDto(Long id) {
    this();
    setId(id);
  }

  public AiSkillDto(Long id, String name, String description, String systemPrompt,
      Boolean isEnabled, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
    this(id);
    setName(name);
    setDescription(description);
    setSystemPrompt(systemPrompt);
    setIsEnabled(isEnabled);
    setCreatedAt(createdAt);
    setLastUpdated(lastUpdated);
    setVersion(version);
  }

  public Long getId() {
    return id;
  }

  public AiSkillDto setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public AiSkillDto setName(String name) {
    this.name = name;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public AiSkillDto setDescription(String description) {
    this.description = description;
    return this;
  }

  public String getSystemPrompt() {
    return systemPrompt;
  }

  public AiSkillDto setSystemPrompt(String systemPrompt) {
    this.systemPrompt = systemPrompt;
    return this;
  }

  public Boolean getIsEnabled() {
    return isEnabled;
  }

  public AiSkillDto setIsEnabled(Boolean isEnabled) {
    this.isEnabled = isEnabled;
    return this;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public AiSkillDto setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  public AiSkillDto setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }

  public Integer getVersion() {
    return version;
  }

  public AiSkillDto setVersion(Integer version) {
    this.version = version;
    return this;
  }
}
