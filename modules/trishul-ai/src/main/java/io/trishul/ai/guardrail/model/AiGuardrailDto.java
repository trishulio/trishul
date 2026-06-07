package io.trishul.ai.guardrail.model;

import io.trishul.model.base.dto.BaseDto;
import java.time.LocalDateTime;

public class AiGuardrailDto extends BaseDto {
  private Long id;

  private String name;

  private AiGuardrailType type;

  private String strategy;

  private String configuration;

  private Integer priority;

  private Boolean isEnabled;

  private LocalDateTime createdAt;

  private LocalDateTime lastUpdated;

  private Integer version;

  public AiGuardrailDto() {}

  public AiGuardrailDto(Long id) {
    this();
    setId(id);
  }

  public AiGuardrailDto(Long id, String name, AiGuardrailType type, String strategy,
      String configuration, Integer priority, Boolean isEnabled, LocalDateTime createdAt,
      LocalDateTime lastUpdated, Integer version) {
    this(id);
    setName(name);
    setType(type);
    setStrategy(strategy);
    setConfiguration(configuration);
    setPriority(priority);
    setIsEnabled(isEnabled);
    setCreatedAt(createdAt);
    setLastUpdated(lastUpdated);
    setVersion(version);
  }

  public Long getId() {
    return id;
  }

  public AiGuardrailDto setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public AiGuardrailDto setName(String name) {
    this.name = name;
    return this;
  }

  public AiGuardrailType getType() {
    return type;
  }

  public AiGuardrailDto setType(AiGuardrailType type) {
    this.type = type;
    return this;
  }

  public String getStrategy() {
    return strategy;
  }

  public AiGuardrailDto setStrategy(String strategy) {
    this.strategy = strategy;
    return this;
  }

  public String getConfiguration() {
    return configuration;
  }

  public AiGuardrailDto setConfiguration(String configuration) {
    this.configuration = configuration;
    return this;
  }

  public Integer getPriority() {
    return priority;
  }

  public AiGuardrailDto setPriority(Integer priority) {
    this.priority = priority;
    return this;
  }

  public Boolean getIsEnabled() {
    return isEnabled;
  }

  public AiGuardrailDto setIsEnabled(Boolean isEnabled) {
    this.isEnabled = isEnabled;
    return this;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public AiGuardrailDto setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  public AiGuardrailDto setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }

  public Integer getVersion() {
    return version;
  }

  public AiGuardrailDto setVersion(Integer version) {
    this.version = version;
    return this;
  }
}
