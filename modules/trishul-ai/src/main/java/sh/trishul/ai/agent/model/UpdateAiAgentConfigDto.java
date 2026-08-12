package sh.trishul.ai.agent.model;

import jakarta.validation.constraints.NotNull;
import java.util.Set;
import sh.trishul.model.base.dto.BaseDto;
import sh.trishul.model.validation.NullOrNotBlank;

public class UpdateAiAgentConfigDto extends BaseDto {
  private Long id;

  @NullOrNotBlank
  private String name;

  private String description;

  private Boolean isActive;

  private Long chatModelConfigId;

  private Long chatMemoryConfigId;

  private Set<Long> guardrailIds;

  private Set<Long> skillIds;

  private Set<Long> toolIds;

  @NotNull
  private Integer version;

  public UpdateAiAgentConfigDto() {}

  public UpdateAiAgentConfigDto(Long id) {
    this();
    setId(id);
  }

  public UpdateAiAgentConfigDto(Long id, String name, String description, Boolean isActive,
      Long chatModelConfigId, Long chatMemoryConfigId, Set<Long> guardrailIds, Set<Long> skillIds,
      Set<Long> toolIds, @NotNull Integer version) {
    this(id);
    setName(name);
    setDescription(description);
    setIsActive(isActive);
    setChatModelConfigId(chatModelConfigId);
    setChatMemoryConfigId(chatMemoryConfigId);
    setGuardrailIds(guardrailIds);
    setSkillIds(skillIds);
    setToolIds(toolIds);
    setVersion(version);
  }

  public Long getId() {
    return id;
  }

  public UpdateAiAgentConfigDto setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public UpdateAiAgentConfigDto setName(String name) {
    this.name = name;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public UpdateAiAgentConfigDto setDescription(String description) {
    this.description = description;
    return this;
  }

  public Boolean getIsActive() {
    return isActive;
  }

  public UpdateAiAgentConfigDto setIsActive(Boolean isActive) {
    this.isActive = isActive;
    return this;
  }

  public Long getChatModelConfigId() {
    return chatModelConfigId;
  }

  public UpdateAiAgentConfigDto setChatModelConfigId(Long chatModelConfigId) {
    this.chatModelConfigId = chatModelConfigId;
    return this;
  }

  public Long getChatMemoryConfigId() {
    return chatMemoryConfigId;
  }

  public UpdateAiAgentConfigDto setChatMemoryConfigId(Long chatMemoryConfigId) {
    this.chatMemoryConfigId = chatMemoryConfigId;
    return this;
  }

  public Set<Long> getGuardrailIds() {
    return guardrailIds;
  }

  public UpdateAiAgentConfigDto setGuardrailIds(Set<Long> guardrailIds) {
    this.guardrailIds = guardrailIds;
    return this;
  }

  public Set<Long> getSkillIds() {
    return skillIds;
  }

  public UpdateAiAgentConfigDto setSkillIds(Set<Long> skillIds) {
    this.skillIds = skillIds;
    return this;
  }

  public Set<Long> getToolIds() {
    return toolIds;
  }

  public UpdateAiAgentConfigDto setToolIds(Set<Long> toolIds) {
    this.toolIds = toolIds;
    return this;
  }

  public Integer getVersion() {
    return version;
  }

  public UpdateAiAgentConfigDto setVersion(Integer version) {
    this.version = version;
    return this;
  }
}
