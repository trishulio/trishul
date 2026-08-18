package sh.trishul.ai.tool.model;

import java.time.LocalDateTime;
import sh.trishul.model.base.dto.BaseDto;

public class AiToolDto extends BaseDto {
  private Long id;

  private String name;

  private String beanName;

  private String description;

  private Boolean isEnabled;

  private LocalDateTime createdAt;

  private LocalDateTime lastUpdated;

  private Integer version;

  public AiToolDto() {}

  public AiToolDto(Long id) {
    this();
    setId(id);
  }

  public AiToolDto(Long id, String name, String beanName, String description, Boolean isEnabled,
      LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
    this(id);
    setName(name);
    setBeanName(beanName);
    setDescription(description);
    setIsEnabled(isEnabled);
    setCreatedAt(createdAt);
    setLastUpdated(lastUpdated);
    setVersion(version);
  }

  public Long getId() {
    return id;
  }

  public AiToolDto setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public AiToolDto setName(String name) {
    this.name = name;
    return this;
  }

  public String getBeanName() {
    return beanName;
  }

  public AiToolDto setBeanName(String beanName) {
    this.beanName = beanName;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public AiToolDto setDescription(String description) {
    this.description = description;
    return this;
  }

  public Boolean getIsEnabled() {
    return isEnabled;
  }

  public AiToolDto setIsEnabled(Boolean isEnabled) {
    this.isEnabled = isEnabled;
    return this;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public AiToolDto setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  public AiToolDto setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }

  public Integer getVersion() {
    return version;
  }

  public AiToolDto setVersion(Integer version) {
    this.version = version;
    return this;
  }
}
