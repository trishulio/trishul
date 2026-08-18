package sh.trishul.ai.guardrail.model;

import jakarta.validation.constraints.NotNull;
import sh.trishul.model.base.dto.BaseDto;
import sh.trishul.model.validation.NullOrNotBlank;

public class UpdateAiGuardrailDto extends BaseDto {
  private Long id;

  @NullOrNotBlank
  private String name;

  private AiGuardrailType type;

  @NullOrNotBlank
  private String strategy;

  private String configuration;

  private Integer priority;

  private Boolean isEnabled;

  @NotNull
  private Integer version;

  public UpdateAiGuardrailDto() {}

  public UpdateAiGuardrailDto(Long id) {
    this();
    setId(id);
  }

  public UpdateAiGuardrailDto(Long id, String name, AiGuardrailType type, String strategy,
      String configuration, Integer priority, Boolean isEnabled, @NotNull Integer version) {
    this(id);
    setName(name);
    setType(type);
    setStrategy(strategy);
    setConfiguration(configuration);
    setPriority(priority);
    setIsEnabled(isEnabled);
    setVersion(version);
  }

  public Long getId() {
    return id;
  }

  public UpdateAiGuardrailDto setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public UpdateAiGuardrailDto setName(String name) {
    this.name = name;
    return this;
  }

  public AiGuardrailType getType() {
    return type;
  }

  public UpdateAiGuardrailDto setType(AiGuardrailType type) {
    this.type = type;
    return this;
  }

  public String getStrategy() {
    return strategy;
  }

  public UpdateAiGuardrailDto setStrategy(String strategy) {
    this.strategy = strategy;
    return this;
  }

  public String getConfiguration() {
    return configuration;
  }

  public UpdateAiGuardrailDto setConfiguration(String configuration) {
    this.configuration = configuration;
    return this;
  }

  public Integer getPriority() {
    return priority;
  }

  public UpdateAiGuardrailDto setPriority(Integer priority) {
    this.priority = priority;
    return this;
  }

  public Boolean getIsEnabled() {
    return isEnabled;
  }

  public UpdateAiGuardrailDto setIsEnabled(Boolean isEnabled) {
    this.isEnabled = isEnabled;
    return this;
  }

  public Integer getVersion() {
    return version;
  }

  public UpdateAiGuardrailDto setVersion(Integer version) {
    this.version = version;
    return this;
  }
}
