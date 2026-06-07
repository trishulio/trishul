package io.trishul.ai.memory.model;

import io.trishul.model.base.dto.BaseDto;
import java.time.LocalDateTime;

public class AiChatMemoryConfigDto extends BaseDto {
  private Long id;

  private String name;

  private String strategy;

  private Integer maxMessages;

  private Integer maxTokens;

  private Integer ttlMinutes;

  private Boolean isDefault;

  private LocalDateTime createdAt;

  private LocalDateTime lastUpdated;

  private Integer version;

  public AiChatMemoryConfigDto() {}

  public AiChatMemoryConfigDto(Long id) {
    this();
    setId(id);
  }

  public AiChatMemoryConfigDto(Long id, String name, String strategy, Integer maxMessages,
      Integer maxTokens, Integer ttlMinutes, Boolean isDefault, LocalDateTime createdAt,
      LocalDateTime lastUpdated, Integer version) {
    this(id);
    setName(name);
    setStrategy(strategy);
    setMaxMessages(maxMessages);
    setMaxTokens(maxTokens);
    setTtlMinutes(ttlMinutes);
    setIsDefault(isDefault);
    setCreatedAt(createdAt);
    setLastUpdated(lastUpdated);
    setVersion(version);
  }

  public Long getId() {
    return id;
  }

  public AiChatMemoryConfigDto setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public AiChatMemoryConfigDto setName(String name) {
    this.name = name;
    return this;
  }

  public String getStrategy() {
    return strategy;
  }

  public AiChatMemoryConfigDto setStrategy(String strategy) {
    this.strategy = strategy;
    return this;
  }

  public Integer getMaxMessages() {
    return maxMessages;
  }

  public AiChatMemoryConfigDto setMaxMessages(Integer maxMessages) {
    this.maxMessages = maxMessages;
    return this;
  }

  public Integer getMaxTokens() {
    return maxTokens;
  }

  public AiChatMemoryConfigDto setMaxTokens(Integer maxTokens) {
    this.maxTokens = maxTokens;
    return this;
  }

  public Integer getTtlMinutes() {
    return ttlMinutes;
  }

  public AiChatMemoryConfigDto setTtlMinutes(Integer ttlMinutes) {
    this.ttlMinutes = ttlMinutes;
    return this;
  }

  public Boolean getIsDefault() {
    return isDefault;
  }

  public AiChatMemoryConfigDto setIsDefault(Boolean isDefault) {
    this.isDefault = isDefault;
    return this;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public AiChatMemoryConfigDto setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  public AiChatMemoryConfigDto setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }

  public Integer getVersion() {
    return version;
  }

  public AiChatMemoryConfigDto setVersion(Integer version) {
    this.version = version;
    return this;
  }
}
