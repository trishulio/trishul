package sh.trishul.ai.chat.model;

import jakarta.validation.constraints.NotNull;
import sh.trishul.model.base.dto.BaseDto;
import sh.trishul.model.validation.NullOrNotBlank;

public class UpdateAiChatModelConfigDto extends BaseDto {
  private Long id;

  @NullOrNotBlank
  private String name;

  @NullOrNotBlank
  private String provider;

  @NullOrNotBlank
  private String modelName;

  @NullOrNotBlank
  private String streamingModelName;

  private String apiKey;

  private Double temperature;

  private Integer maxTokens;

  private Double topP;

  private Boolean isDefault;

  @NotNull
  private Integer version;

  public UpdateAiChatModelConfigDto() {}

  public UpdateAiChatModelConfigDto(Long id) {
    this();
    setId(id);
  }

  public UpdateAiChatModelConfigDto(Long id, String name, String provider, String modelName,
      String streamingModelName, String apiKey, Double temperature, Integer maxTokens, Double topP,
      Boolean isDefault, @NotNull Integer version) {
    this(id);
    setName(name);
    setProvider(provider);
    setModelName(modelName);
    setStreamingModelName(streamingModelName);
    setApiKey(apiKey);
    setTemperature(temperature);
    setMaxTokens(maxTokens);
    setTopP(topP);
    setIsDefault(isDefault);
    setVersion(version);
  }

  public Long getId() {
    return id;
  }

  public UpdateAiChatModelConfigDto setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public UpdateAiChatModelConfigDto setName(String name) {
    this.name = name;
    return this;
  }

  public String getProvider() {
    return provider;
  }

  public UpdateAiChatModelConfigDto setProvider(String provider) {
    this.provider = provider;
    return this;
  }

  public String getModelName() {
    return modelName;
  }

  public UpdateAiChatModelConfigDto setModelName(String modelName) {
    this.modelName = modelName;
    return this;
  }

  public String getStreamingModelName() {
    return streamingModelName;
  }

  public UpdateAiChatModelConfigDto setStreamingModelName(String streamingModelName) {
    this.streamingModelName = streamingModelName;
    return this;
  }

  public String getApiKey() {
    return apiKey;
  }

  public UpdateAiChatModelConfigDto setApiKey(String apiKey) {
    this.apiKey = apiKey;
    return this;
  }

  public Double getTemperature() {
    return temperature;
  }

  public UpdateAiChatModelConfigDto setTemperature(Double temperature) {
    this.temperature = temperature;
    return this;
  }

  public Integer getMaxTokens() {
    return maxTokens;
  }

  public UpdateAiChatModelConfigDto setMaxTokens(Integer maxTokens) {
    this.maxTokens = maxTokens;
    return this;
  }

  public Double getTopP() {
    return topP;
  }

  public UpdateAiChatModelConfigDto setTopP(Double topP) {
    this.topP = topP;
    return this;
  }

  public Boolean getIsDefault() {
    return isDefault;
  }

  public UpdateAiChatModelConfigDto setIsDefault(Boolean isDefault) {
    this.isDefault = isDefault;
    return this;
  }

  public Integer getVersion() {
    return version;
  }

  public UpdateAiChatModelConfigDto setVersion(Integer version) {
    this.version = version;
    return this;
  }
}
