package io.trishul.ai.skill.model;

import io.trishul.model.base.dto.BaseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AddAiSkillDto extends BaseDto {
  @NotBlank
  private String name;

  private String description;

  @NotBlank
  private String systemPrompt;

  @NotNull
  private Boolean isEnabled;

  public AddAiSkillDto() {}

  public AddAiSkillDto(String name, String description, String systemPrompt, Boolean isEnabled) {
    setName(name);
    setDescription(description);
    setSystemPrompt(systemPrompt);
    setIsEnabled(isEnabled);
  }

  public String getName() {
    return name;
  }

  public AddAiSkillDto setName(String name) {
    this.name = name;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public AddAiSkillDto setDescription(String description) {
    this.description = description;
    return this;
  }

  public String getSystemPrompt() {
    return systemPrompt;
  }

  public AddAiSkillDto setSystemPrompt(String systemPrompt) {
    this.systemPrompt = systemPrompt;
    return this;
  }

  public Boolean getIsEnabled() {
    return isEnabled;
  }

  public AddAiSkillDto setIsEnabled(Boolean isEnabled) {
    this.isEnabled = isEnabled;
    return this;
  }
}
