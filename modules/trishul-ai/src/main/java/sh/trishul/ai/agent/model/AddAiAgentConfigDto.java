package sh.trishul.ai.agent.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import sh.trishul.model.base.dto.BaseDto;

public class AddAiAgentConfigDto extends BaseDto {
  @NotBlank
  private String name;

  private String description;

  @NotNull
  private Boolean isActive;

  @NotNull
  private Long chatModelConfigId;

  private Long chatMemoryConfigId; // Optional fallback

  private Set<Long> guardrailIds;

  private Set<Long> skillIds;

  private Set<Long> toolIds;

  public AddAiAgentConfigDto() {}

  public AddAiAgentConfigDto(String name, String description, Boolean isActive,
      Long chatModelConfigId, Long chatMemoryConfigId, Set<Long> guardrailIds, Set<Long> skillIds,
      Set<Long> toolIds) {
    setName(name);
    setDescription(description);
    setIsActive(isActive);
    setChatModelConfigId(chatModelConfigId);
    setChatMemoryConfigId(chatMemoryConfigId);
    setGuardrailIds(guardrailIds);
    setSkillIds(skillIds);
    setToolIds(toolIds);
  }

  public String getName() {
    return name;
  }

  public AddAiAgentConfigDto setName(String name) {
    this.name = name;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public AddAiAgentConfigDto setDescription(String description) {
    this.description = description;
    return this;
  }

  public Boolean getIsActive() {
    return isActive;
  }

  public AddAiAgentConfigDto setIsActive(Boolean isActive) {
    this.isActive = isActive;
    return this;
  }

  public Long getChatModelConfigId() {
    return chatModelConfigId;
  }

  public AddAiAgentConfigDto setChatModelConfigId(Long chatModelConfigId) {
    this.chatModelConfigId = chatModelConfigId;
    return this;
  }

  public Long getChatMemoryConfigId() {
    return chatMemoryConfigId;
  }

  public AddAiAgentConfigDto setChatMemoryConfigId(Long chatMemoryConfigId) {
    this.chatMemoryConfigId = chatMemoryConfigId;
    return this;
  }

  public Set<Long> getGuardrailIds() {
    return guardrailIds;
  }

  public AddAiAgentConfigDto setGuardrailIds(Set<Long> guardrailIds) {
    this.guardrailIds = guardrailIds;
    return this;
  }

  public Set<Long> getSkillIds() {
    return skillIds;
  }

  public AddAiAgentConfigDto setSkillIds(Set<Long> skillIds) {
    this.skillIds = skillIds;
    return this;
  }

  public Set<Long> getToolIds() {
    return toolIds;
  }

  public AddAiAgentConfigDto setToolIds(Set<Long> toolIds) {
    this.toolIds = toolIds;
    return this;
  }
}
