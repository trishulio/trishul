package sh.trishul.ai.session.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import sh.trishul.ai.agent.model.AiAgentConfig;
import sh.trishul.ai.memory.model.AiChatMemoryConfig;
import sh.trishul.base.types.base.pojo.Audited;
import sh.trishul.base.types.base.pojo.CrudEntity;
import sh.trishul.model.base.entity.BaseEntity;

@Entity(name = "ai_chat_session")
@Table(name = "AI_CHAT_SESSION")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class AiChatSession extends BaseEntity implements CrudEntity<Long, AiChatSession>,
    UpdateAiChatSession<AiChatSession>, Audited<AiChatSession> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ai_chat_session_generator")
  @SequenceGenerator(name = "ai_chat_session_generator", sequenceName = "ai_chat_session_sequence",
      allocationSize = 1)
  private Long id;

  @Column(name = "session_key", unique = true, updatable = false)
  private String sessionKey;

  @Column(name = "title")
  private String title;

  @Column(name = "is_active")
  private Boolean isActive;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "agent_config_id", referencedColumnName = "id")
  private AiAgentConfig agentConfig;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "chat_memory_config_id", referencedColumnName = "id")
  private AiChatMemoryConfig chatMemoryConfig;

  @Version
  private Integer version;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "last_updated")
  private LocalDateTime lastUpdated;

  public AiChatSession() {}

  public AiChatSession(Long id) {
    this();
    setId(id);
  }

  public AiChatSession(Long id, String sessionKey, String title, Boolean isActive,
      AiAgentConfig agentConfig, AiChatMemoryConfig chatMemoryConfig, LocalDateTime createdAt,
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

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public AiChatSession setId(Long id) {
    this.id = id;
    return this;
  }

  @Override
  public String getSessionKey() {
    return sessionKey;
  }

  @Override
  public AiChatSession setSessionKey(String sessionKey) {
    this.sessionKey = sessionKey;
    return this;
  }

  @Override
  public String getTitle() {
    return title;
  }

  @Override
  public AiChatSession setTitle(String title) {
    this.title = title;
    return this;
  }

  @Override
  public Boolean getIsActive() {
    return isActive;
  }

  @Override
  public AiChatSession setIsActive(Boolean isActive) {
    this.isActive = isActive;
    return this;
  }

  @Override
  public AiAgentConfig getAgentConfig() {
    return agentConfig;
  }

  @Override
  public AiChatSession setAgentConfig(AiAgentConfig agentConfig) {
    this.agentConfig = agentConfig;
    return this;
  }

  @Override
  public AiChatMemoryConfig getChatMemoryConfig() {
    return chatMemoryConfig;
  }

  @Override
  public AiChatSession setChatMemoryConfig(AiChatMemoryConfig chatMemoryConfig) {
    this.chatMemoryConfig = chatMemoryConfig;
    return this;
  }

  @Override
  public Integer getVersion() {
    return version;
  }

  public AiChatSession setVersion(Integer version) {
    this.version = version;
    return this;
  }

  @Override
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  @Override
  public AiChatSession setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  @Override
  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  @Override
  public AiChatSession setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }
}
