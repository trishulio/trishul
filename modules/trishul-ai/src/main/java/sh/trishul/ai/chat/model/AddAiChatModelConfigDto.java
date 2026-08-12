package sh.trishul.ai.chat.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import sh.trishul.model.base.dto.BaseDto;

public class AddAiChatModelConfigDto extends BaseDto {
  @NotBlank
  private String name;

  @NotBlank
  private String provider;

  @NotBlank
  private String modelName;

  private String streamingModelName;

  private String apiKey;

  private Double temperature;

  private Integer maxTokens;

  private Double topP;

  @NotNull
  private Boolean isDefault;

  public AddAiChatModelConfigDto() {}

  public AddAiChatModelConfigDto(String name, String provider, String modelName,
      String streamingModelName, String apiKey, Double temperature, Integer maxTokens, Double topP,
      Boolean isDefault) {
    setName(name);
    setProvider(provider);
    setModelName(modelName);
    setStreamingModelName(streamingModelName);
    setApiKey(apiKey);
    setTemperature(temperature);
    setMaxTokens(maxTokens);
    setTopP(topP);
    setIsDefault(isDefault);
  }

  public String getName() {
    return name;
  }

  public AddAiChatModelConfigDto setName(String name) {
    this.name = name;
    return this;
  }

  public String getProvider() {
    return provider;
  }

  public AddAiChatModelConfigDto setProvider(String provider) {
    this.provider = provider;
    return this;
  }

  public String getModelName() {
    return modelName;
  }

  public AddAiChatModelConfigDto setModelName(String modelName) {
    this.modelName = modelName;
    return this;
  }

  public String getStreamingModelName() {
    return streamingModelName;
  }

  public AddAiChatModelConfigDto setStreamingModelName(String streamingModelName) {
    this.streamingModelName = streamingModelName;
    return this;
  }

  public String getApiKey() {
    return apiKey;
  }

  public AddAiChatModelConfigDto setApiKey(String apiKey) {
    this.apiKey = apiKey;
    return this;
  }

  public Double getTemperature() {
    return temperature;
  }

  public AddAiChatModelConfigDto setTemperature(Double temperature) {
    this.temperature = temperature;
    return this;
  }

  public Integer getMaxTokens() {
    return maxTokens;
  }

  public AddAiChatModelConfigDto setMaxTokens(Integer maxTokens) {
    this.maxTokens = maxTokens;
    return this;
  }

  public Double getTopP() {
    return topP;
  }

  public AddAiChatModelConfigDto setTopP(Double topP) {
    this.topP = topP;
    return this;
  }

  public Boolean getIsDefault() {
    return isDefault;
  }

  public AddAiChatModelConfigDto setIsDefault(Boolean isDefault) {
    this.isDefault = isDefault;
    return this;
  }
}
