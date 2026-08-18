package sh.trishul.ai.chat.model;

import java.time.LocalDateTime;
import sh.trishul.model.base.dto.BaseDto;

public class AiChatModelConfigDto extends BaseDto {
  private Long id;

  private String name;

  private String provider;

  private String modelName;

  private String streamingModelName;

  private Double temperature;

  private Integer maxTokens;

  private Double topP;

  private Boolean isDefault;

  private LocalDateTime createdAt;

  private LocalDateTime lastUpdated;

  private Integer version;

  public AiChatModelConfigDto() {}

  public AiChatModelConfigDto(Long id) {
    this();
    setId(id);
  }

  public AiChatModelConfigDto(Long id, String name, String provider, String modelName,
      String streamingModelName, Double temperature, Integer maxTokens, Double topP,
      Boolean isDefault, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
    this(id);
    setName(name);
    setProvider(provider);
    setModelName(modelName);
    setStreamingModelName(streamingModelName);
    setTemperature(temperature);
    setMaxTokens(maxTokens);
    setTopP(topP);
    setIsDefault(isDefault);
    setCreatedAt(createdAt);
    setLastUpdated(lastUpdated);
    setVersion(version);
  }

  public Long getId() {
    return id;
  }

  public AiChatModelConfigDto setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public AiChatModelConfigDto setName(String name) {
    this.name = name;
    return this;
  }

  public String getProvider() {
    return provider;
  }

  public AiChatModelConfigDto setProvider(String provider) {
    this.provider = provider;
    return this;
  }

  public String getModelName() {
    return modelName;
  }

  public AiChatModelConfigDto setModelName(String modelName) {
    this.modelName = modelName;
    return this;
  }

  public String getStreamingModelName() {
    return streamingModelName;
  }

  public AiChatModelConfigDto setStreamingModelName(String streamingModelName) {
    this.streamingModelName = streamingModelName;
    return this;
  }

  public Double getTemperature() {
    return temperature;
  }

  public AiChatModelConfigDto setTemperature(Double temperature) {
    this.temperature = temperature;
    return this;
  }

  public Integer getMaxTokens() {
    return maxTokens;
  }

  public AiChatModelConfigDto setMaxTokens(Integer maxTokens) {
    this.maxTokens = maxTokens;
    return this;
  }

  public Double getTopP() {
    return topP;
  }

  public AiChatModelConfigDto setTopP(Double topP) {
    this.topP = topP;
    return this;
  }

  public Boolean getIsDefault() {
    return isDefault;
  }

  public AiChatModelConfigDto setIsDefault(Boolean isDefault) {
    this.isDefault = isDefault;
    return this;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public AiChatModelConfigDto setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  public AiChatModelConfigDto setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }

  public Integer getVersion() {
    return version;
  }

  public AiChatModelConfigDto setVersion(Integer version) {
    this.version = version;
    return this;
  }
}
