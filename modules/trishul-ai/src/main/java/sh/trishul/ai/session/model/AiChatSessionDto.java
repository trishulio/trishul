package sh.trishul.ai.session.model;

import java.time.LocalDateTime;
import sh.trishul.ai.agent.model.AiAgentConfigDto;
import sh.trishul.ai.memory.model.AiChatMemoryConfigDto;
import sh.trishul.model.base.dto.BaseDto;

public class AiChatSessionDto extends BaseDto {
  private Long id;

  private String sessionKey;

  private String title;

  private Boolean isActive;

  private AiAgentConfigDto agentConfig;

  private AiChatMemoryConfigDto chatMemoryConfig;

  private LocalDateTime createdAt;

  private LocalDateTime lastUpdated;

  private Integer version;

  public AiChatSessionDto() {}

  public AiChatSessionDto(Long id) {
    this();
    setId(id);
  }

  public AiChatSessionDto(Long id, String sessionKey, String title, Boolean isActive,
      AiAgentConfigDto agentConfig, AiChatMemoryConfigDto chatMemoryConfig, LocalDateTime createdAt,
      LocalDateTime lastUpdated, Integer version) {
    this(id);
    setSessionKey(sessionKey);
    setTitle(title);
    setIsActive(isActive);
    setAgentConfig(agentConfig);
    setChatMemoryConfig(chatMemoryConfig);
    setCreatedAt(createdAt);
    setLastUpdated(lastUpdated);
    setVersion(version);
  }

  public Long getId() {
    return id;
  }

  public AiChatSessionDto setId(Long id) {
    this.id = id;
    return this;
  }

  public String getSessionKey() {
    return sessionKey;
  }

  public AiChatSessionDto setSessionKey(String sessionKey) {
    this.sessionKey = sessionKey;
    return this;
  }

  public String getTitle() {
    return title;
  }

  public AiChatSessionDto setTitle(String title) {
    this.title = title;
    return this;
  }

  public Boolean getIsActive() {
    return isActive;
  }

  public AiChatSessionDto setIsActive(Boolean isActive) {
    this.isActive = isActive;
    return this;
  }

  public AiAgentConfigDto getAgentConfig() {
    return agentConfig;
  }

  public AiChatSessionDto setAgentConfig(AiAgentConfigDto agentConfig) {
    this.agentConfig = agentConfig;
    return this;
  }

  public AiChatMemoryConfigDto getChatMemoryConfig() {
    return chatMemoryConfig;
  }

  public AiChatSessionDto setChatMemoryConfig(AiChatMemoryConfigDto chatMemoryConfig) {
    this.chatMemoryConfig = chatMemoryConfig;
    return this;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public AiChatSessionDto setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  public AiChatSessionDto setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }

  public Integer getVersion() {
    return version;
  }

  public AiChatSessionDto setVersion(Integer version) {
    this.version = version;
    return this;
  }
}
