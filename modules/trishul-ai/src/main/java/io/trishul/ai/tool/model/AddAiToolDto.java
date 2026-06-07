package io.trishul.ai.tool.model;

import io.trishul.model.base.dto.BaseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AddAiToolDto extends BaseDto {
  @NotBlank
  private String name;

  @NotBlank
  private String beanName;

  private String description;

  @NotNull
  private Boolean isEnabled;

  public AddAiToolDto() {}

  public AddAiToolDto(String name, String beanName, String description, Boolean isEnabled) {
    setName(name);
    setBeanName(beanName);
    setDescription(description);
    setIsEnabled(isEnabled);
  }

  public String getName() {
    return name;
  }

  public AddAiToolDto setName(String name) {
    this.name = name;
    return this;
  }

  public String getBeanName() {
    return beanName;
  }

  public AddAiToolDto setBeanName(String beanName) {
    this.beanName = beanName;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public AddAiToolDto setDescription(String description) {
    this.description = description;
    return this;
  }

  public Boolean getIsEnabled() {
    return isEnabled;
  }

  public AddAiToolDto setIsEnabled(Boolean isEnabled) {
    this.isEnabled = isEnabled;
    return this;
  }
}
