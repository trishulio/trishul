package sh.trishul.ai.memory.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import sh.trishul.model.base.dto.BaseDto;

public class AddAiChatMemoryConfigDto extends BaseDto {
  @NotBlank
  private String name;

  @NotBlank
  private String strategy;

  private Integer maxMessages;

  private Integer maxTokens;

  private Integer ttlMinutes;

  @NotNull
  private Boolean isDefault;

  public AddAiChatMemoryConfigDto() {}

  public AddAiChatMemoryConfigDto(String name, String strategy, Integer maxMessages,
      Integer maxTokens, Integer ttlMinutes, Boolean isDefault) {
    setName(name);
    setStrategy(strategy);
    setMaxMessages(maxMessages);
    setMaxTokens(maxTokens);
    setTtlMinutes(ttlMinutes);
    setIsDefault(isDefault);
  }

  public String getName() {
    return name;
  }

  public AddAiChatMemoryConfigDto setName(String name) {
    this.name = name;
    return this;
  }

  public String getStrategy() {
    return strategy;
  }

  public AddAiChatMemoryConfigDto setStrategy(String strategy) {
    this.strategy = strategy;
    return this;
  }

  public Integer getMaxMessages() {
    return maxMessages;
  }

  public AddAiChatMemoryConfigDto setMaxMessages(Integer maxMessages) {
    this.maxMessages = maxMessages;
    return this;
  }

  public Integer getMaxTokens() {
    return maxTokens;
  }

  public AddAiChatMemoryConfigDto setMaxTokens(Integer maxTokens) {
    this.maxTokens = maxTokens;
    return this;
  }

  public Integer getTtlMinutes() {
    return ttlMinutes;
  }

  public AddAiChatMemoryConfigDto setTtlMinutes(Integer ttlMinutes) {
    this.ttlMinutes = ttlMinutes;
    return this;
  }

  public Boolean getIsDefault() {
    return isDefault;
  }

  public AddAiChatMemoryConfigDto setIsDefault(Boolean isDefault) {
    this.isDefault = isDefault;
    return this;
  }
}
