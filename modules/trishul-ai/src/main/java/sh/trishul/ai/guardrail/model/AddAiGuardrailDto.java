package sh.trishul.ai.guardrail.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import sh.trishul.model.base.dto.BaseDto;

public class AddAiGuardrailDto extends BaseDto {
  @NotBlank
  private String name;

  @NotNull
  private AiGuardrailType type;

  @NotBlank
  private String strategy;

  private String configuration;

  @NotNull
  private Integer priority;

  @NotNull
  private Boolean isEnabled;

  public AddAiGuardrailDto() {}

  public AddAiGuardrailDto(String name, AiGuardrailType type, String strategy, String configuration,
      Integer priority, Boolean isEnabled) {
    setName(name);
    setType(type);
    setStrategy(strategy);
    setConfiguration(configuration);
    setPriority(priority);
    setIsEnabled(isEnabled);
  }

  public String getName() {
    return name;
  }

  public AddAiGuardrailDto setName(String name) {
    this.name = name;
    return this;
  }

  public AiGuardrailType getType() {
    return type;
  }

  public AddAiGuardrailDto setType(AiGuardrailType type) {
    this.type = type;
    return this;
  }

  public String getStrategy() {
    return strategy;
  }

  public AddAiGuardrailDto setStrategy(String strategy) {
    this.strategy = strategy;
    return this;
  }

  public String getConfiguration() {
    return configuration;
  }

  public AddAiGuardrailDto setConfiguration(String configuration) {
    this.configuration = configuration;
    return this;
  }

  public Integer getPriority() {
    return priority;
  }

  public AddAiGuardrailDto setPriority(Integer priority) {
    this.priority = priority;
    return this;
  }

  public Boolean getIsEnabled() {
    return isEnabled;
  }

  public AddAiGuardrailDto setIsEnabled(Boolean isEnabled) {
    this.isEnabled = isEnabled;
    return this;
  }
}
