package io.trishul.ai.tool.model;

import io.trishul.model.base.dto.BaseDto;
import io.trishul.model.validation.NullOrNotBlank;
import jakarta.validation.constraints.NotNull;

public class UpdateAiToolDto extends BaseDto {
  private Long id;

  @NullOrNotBlank
  private String name;

  @NullOrNotBlank
  private String beanName;

  private String description;

  private Boolean isEnabled;

  @NotNull
  private Integer version;

  public UpdateAiToolDto() {}

  public UpdateAiToolDto(Long id) {
    this();
    setId(id);
  }

  public UpdateAiToolDto(Long id, String name, String beanName, String description,
      Boolean isEnabled, @NotNull Integer version) {
    this(id);
    setName(name);
    setBeanName(beanName);
    setDescription(description);
    setIsEnabled(isEnabled);
    setVersion(version);
  }

  public Long getId() {
    return id;
  }

  public UpdateAiToolDto setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public UpdateAiToolDto setName(String name) {
    this.name = name;
    return this;
  }

  public String getBeanName() {
    return beanName;
  }

  public UpdateAiToolDto setBeanName(String beanName) {
    this.beanName = beanName;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public UpdateAiToolDto setDescription(String description) {
    this.description = description;
    return this;
  }

  public Boolean getIsEnabled() {
    return isEnabled;
  }

  public UpdateAiToolDto setIsEnabled(Boolean isEnabled) {
    this.isEnabled = isEnabled;
    return this;
  }

  public Integer getVersion() {
    return version;
  }

  public UpdateAiToolDto setVersion(Integer version) {
    this.version = version;
    return this;
  }
}
