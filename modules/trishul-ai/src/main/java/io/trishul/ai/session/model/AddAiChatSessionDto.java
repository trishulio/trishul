package io.trishul.ai.session.model;

import io.trishul.model.base.dto.BaseDto;
import jakarta.validation.constraints.NotNull;

public class AddAiChatSessionDto extends BaseDto {
  private String sessionKey; // Can be auto-generated or provided

  private String title;

  @NotNull
  private Boolean isActive;

  @NotNull
  private Long agentConfigId;

  private Long chatMemoryConfigId; // Optional, can inherit from agent

  public AddAiChatSessionDto() {}

  public AddAiChatSessionDto(String sessionKey, String title, Boolean isActive, Long agentConfigId,
      Long chatMemoryConfigId) {
    setSessionKey(sessionKey);
    setTitle(title);
    setIsActive(isActive);
    setAgentConfigId(agentConfigId);
    setChatMemoryConfigId(chatMemoryConfigId);
  }

  public String getSessionKey() {
    return sessionKey;
  }

  public AddAiChatSessionDto setSessionKey(String sessionKey) {
    this.sessionKey = sessionKey;
    return this;
  }

  public String getTitle() {
    return title;
  }

  public AddAiChatSessionDto setTitle(String title) {
    this.title = title;
    return this;
  }

  public Boolean getIsActive() {
    return isActive;
  }

  public AddAiChatSessionDto setIsActive(Boolean isActive) {
    this.isActive = isActive;
    return this;
  }

  public Long getAgentConfigId() {
    return agentConfigId;
  }

  public AddAiChatSessionDto setAgentConfigId(Long agentConfigId) {
    this.agentConfigId = agentConfigId;
    return this;
  }

  public Long getChatMemoryConfigId() {
    return chatMemoryConfigId;
  }

  public AddAiChatSessionDto setChatMemoryConfigId(Long chatMemoryConfigId) {
    this.chatMemoryConfigId = chatMemoryConfigId;
    return this;
  }
}
