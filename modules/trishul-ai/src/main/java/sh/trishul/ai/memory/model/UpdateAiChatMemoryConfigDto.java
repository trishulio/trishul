package sh.trishul.ai.memory.model;

import jakarta.validation.constraints.NotNull;
import sh.trishul.model.base.dto.BaseDto;
import sh.trishul.model.validation.NullOrNotBlank;

public class UpdateAiChatMemoryConfigDto extends BaseDto {
  private Long id;

  @NullOrNotBlank
  private String name;

  @NullOrNotBlank
  private String strategy;

  private Integer maxMessages;

  private Integer maxTokens;

  private Integer ttlMinutes;

  private Boolean isDefault;

  @NotNull
  private Integer version;

  public UpdateAiChatMemoryConfigDto() {}

  public UpdateAiChatMemoryConfigDto(Long id) {
    this();
    setId(id);
  }

  public UpdateAiChatMemoryConfigDto(Long id, String name, String strategy, Integer maxMessages,
      Integer maxTokens, Integer ttlMinutes, Boolean isDefault, @NotNull Integer version) {
    this(id);
    setName(name);
    setStrategy(strategy);
    setMaxMessages(maxMessages);
    setMaxTokens(maxTokens);
    setTtlMinutes(ttlMinutes);
    setIsDefault(isDefault);
    setVersion(version);
  }

  public Long getId() {
    return id;
  }

  public UpdateAiChatMemoryConfigDto setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public UpdateAiChatMemoryConfigDto setName(String name) {
    this.name = name;
    return this;
  }

  public String getStrategy() {
    return strategy;
  }

  public UpdateAiChatMemoryConfigDto setStrategy(String strategy) {
    this.strategy = strategy;
    return this;
  }

  public Integer getMaxMessages() {
    return maxMessages;
  }

  public UpdateAiChatMemoryConfigDto setMaxMessages(Integer maxMessages) {
    this.maxMessages = maxMessages;
    return this;
  }

  public Integer getMaxTokens() {
    return maxTokens;
  }

  public UpdateAiChatMemoryConfigDto setMaxTokens(Integer maxTokens) {
    this.maxTokens = maxTokens;
    return this;
  }

  public Integer getTtlMinutes() {
    return ttlMinutes;
  }

  public UpdateAiChatMemoryConfigDto setTtlMinutes(Integer ttlMinutes) {
    this.ttlMinutes = ttlMinutes;
    return this;
  }

  public Boolean getIsDefault() {
    return isDefault;
  }

  public UpdateAiChatMemoryConfigDto setIsDefault(Boolean isDefault) {
    this.isDefault = isDefault;
    return this;
  }

  public Integer getVersion() {
    return version;
  }

  public UpdateAiChatMemoryConfigDto setVersion(Integer version) {
    this.version = version;
    return this;
  }
}
