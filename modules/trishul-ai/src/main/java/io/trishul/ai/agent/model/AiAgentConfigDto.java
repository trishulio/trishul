package io.trishul.ai.agent.model;

import io.trishul.ai.chat.model.AiChatModelConfigDto;
import io.trishul.ai.guardrail.model.AiGuardrailDto;
import io.trishul.ai.memory.model.AiChatMemoryConfigDto;
import io.trishul.ai.skill.model.AiSkillDto;
import io.trishul.ai.tool.model.AiToolDto;
import io.trishul.model.base.dto.BaseDto;
import java.time.LocalDateTime;
import java.util.Set;

public class AiAgentConfigDto extends BaseDto {
  public static final String ATTR_GUARDRAILS = "guardrails";
  public static final String ATTR_SKILLS = "skills";
  public static final String ATTR_TOOLS = "tools";

  private Long id;

  private String name;

  private String description;

  private Boolean isActive;

  private AiChatModelConfigDto chatModelConfig;

  private AiChatMemoryConfigDto chatMemoryConfig;

  private Set<AiGuardrailDto> guardrails;

  private Set<AiSkillDto> skills;

  private Set<AiToolDto> tools;

  private LocalDateTime createdAt;

  private LocalDateTime lastUpdated;

  private Integer version;

  public AiAgentConfigDto() {}

  public AiAgentConfigDto(Long id) {
    this();
    setId(id);
  }

  public AiAgentConfigDto(Long id, String name, String description, Boolean isActive,
      AiChatModelConfigDto chatModelConfig, AiChatMemoryConfigDto chatMemoryConfig,
      Set<AiGuardrailDto> guardrails, Set<AiSkillDto> skills, Set<AiToolDto> tools,
      LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
    this(id);
    setName(name);
    setDescription(description);
    setIsActive(isActive);
    setChatModelConfig(chatModelConfig);
    setChatMemoryConfig(chatMemoryConfig);
    setGuardrails(guardrails);
    setSkills(skills);
    setTools(tools);
    setCreatedAt(createdAt);
    setLastUpdated(lastUpdated);
    setVersion(version);
  }

  public Long getId() {
    return id;
  }

  public AiAgentConfigDto setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public AiAgentConfigDto setName(String name) {
    this.name = name;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public AiAgentConfigDto setDescription(String description) {
    this.description = description;
    return this;
  }

  public Boolean getIsActive() {
    return isActive;
  }

  public AiAgentConfigDto setIsActive(Boolean isActive) {
    this.isActive = isActive;
    return this;
  }

  public AiChatModelConfigDto getChatModelConfig() {
    return chatModelConfig;
  }

  public AiAgentConfigDto setChatModelConfig(AiChatModelConfigDto chatModelConfig) {
    this.chatModelConfig = chatModelConfig;
    return this;
  }

  public AiChatMemoryConfigDto getChatMemoryConfig() {
    return chatMemoryConfig;
  }

  public AiAgentConfigDto setChatMemoryConfig(AiChatMemoryConfigDto chatMemoryConfig) {
    this.chatMemoryConfig = chatMemoryConfig;
    return this;
  }

  public Set<AiGuardrailDto> getGuardrails() {
    return guardrails;
  }

  public AiAgentConfigDto setGuardrails(Set<AiGuardrailDto> guardrails) {
    this.guardrails = guardrails;
    return this;
  }

  public Set<AiSkillDto> getSkills() {
    return skills;
  }

  public AiAgentConfigDto setSkills(Set<AiSkillDto> skills) {
    this.skills = skills;
    return this;
  }

  public Set<AiToolDto> getTools() {
    return tools;
  }

  public AiAgentConfigDto setTools(Set<AiToolDto> tools) {
    this.tools = tools;
    return this;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public AiAgentConfigDto setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  public AiAgentConfigDto setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }

  public Integer getVersion() {
    return version;
  }

  public AiAgentConfigDto setVersion(Integer version) {
    this.version = version;
    return this;
  }
}
