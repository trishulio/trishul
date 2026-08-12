package sh.trishul.ai.session.model;

import jakarta.validation.constraints.NotNull;
import sh.trishul.model.base.dto.BaseDto;
import sh.trishul.model.validation.NullOrNotBlank;

public class UpdateAiChatSessionDto extends BaseDto {
  private Long id;

  @NullOrNotBlank
  private String title;

  private Boolean isActive;

  private Long agentConfigId;

  private Long chatMemoryConfigId;

  @NotNull
  private Integer version;

  public UpdateAiChatSessionDto() {}

  public UpdateAiChatSessionDto(Long id) {
    this();
    setId(id);
  }

  public UpdateAiChatSessionDto(Long id, String title, Boolean isActive, Long agentConfigId,
      Long chatMemoryConfigId, @NotNull Integer version) {
    this(id);
    setTitle(title);
    setIsActive(isActive);
    setAgentConfigId(agentConfigId);
    setChatMemoryConfigId(chatMemoryConfigId);
    setVersion(version);
  }

  public Long getId() {
    return id;
  }

  public UpdateAiChatSessionDto setId(Long id) {
    this.id = id;
    return this;
  }

  public String getTitle() {
    return title;
  }

  public UpdateAiChatSessionDto setTitle(String title) {
    this.title = title;
    return this;
  }

  public Boolean getIsActive() {
    return isActive;
  }

  public UpdateAiChatSessionDto setIsActive(Boolean isActive) {
    this.isActive = isActive;
    return this;
  }

  public Long getAgentConfigId() {
    return agentConfigId;
  }

  public UpdateAiChatSessionDto setAgentConfigId(Long agentConfigId) {
    this.agentConfigId = agentConfigId;
    return this;
  }

  public Long getChatMemoryConfigId() {
    return chatMemoryConfigId;
  }

  public UpdateAiChatSessionDto setChatMemoryConfigId(Long chatMemoryConfigId) {
    this.chatMemoryConfigId = chatMemoryConfigId;
    return this;
  }

  public Integer getVersion() {
    return version;
  }

  public UpdateAiChatSessionDto setVersion(Integer version) {
    this.version = version;
    return this;
  }
}
