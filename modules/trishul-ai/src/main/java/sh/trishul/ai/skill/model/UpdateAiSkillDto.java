package sh.trishul.ai.skill.model;

import jakarta.validation.constraints.NotNull;
import sh.trishul.model.base.dto.BaseDto;
import sh.trishul.model.validation.NullOrNotBlank;

public class UpdateAiSkillDto extends BaseDto {
  private Long id;

  @NullOrNotBlank
  private String name;

  private String description;

  @NullOrNotBlank
  private String systemPrompt;

  private Boolean isEnabled;

  @NotNull
  private Integer version;

  public UpdateAiSkillDto() {}

  public UpdateAiSkillDto(Long id) {
    this();
    setId(id);
  }

  public UpdateAiSkillDto(Long id, String name, String description, String systemPrompt,
      Boolean isEnabled, @NotNull Integer version) {
    this(id);
    setName(name);
    setDescription(description);
    setSystemPrompt(systemPrompt);
    setIsEnabled(isEnabled);
    setVersion(version);
  }

  public Long getId() {
    return id;
  }

  public UpdateAiSkillDto setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public UpdateAiSkillDto setName(String name) {
    this.name = name;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public UpdateAiSkillDto setDescription(String description) {
    this.description = description;
    return this;
  }

  public String getSystemPrompt() {
    return systemPrompt;
  }

  public UpdateAiSkillDto setSystemPrompt(String systemPrompt) {
    this.systemPrompt = systemPrompt;
    return this;
  }

  public Boolean getIsEnabled() {
    return isEnabled;
  }

  public UpdateAiSkillDto setIsEnabled(Boolean isEnabled) {
    this.isEnabled = isEnabled;
    return this;
  }

  public Integer getVersion() {
    return version;
  }

  public UpdateAiSkillDto setVersion(Integer version) {
    this.version = version;
    return this;
  }
}
