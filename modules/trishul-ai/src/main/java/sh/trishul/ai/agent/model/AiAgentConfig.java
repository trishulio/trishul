package sh.trishul.ai.agent.model;

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
import sh.trishul.ai.chat.model.AiChatModelConfig;
import sh.trishul.ai.memory.model.AiChatMemoryConfig;
import sh.trishul.base.types.base.pojo.Audited;
import sh.trishul.base.types.base.pojo.CrudEntity;
import sh.trishul.model.base.entity.BaseEntity;

@Entity(name = "ai_agent_config")
@Table(name = "AI_AGENT_CONFIG")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class AiAgentConfig extends BaseEntity implements CrudEntity<Long, AiAgentConfig>,
    UpdateAiAgentConfig<AiAgentConfig>, Audited<AiAgentConfig> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ai_agent_config_generator")
  @SequenceGenerator(name = "ai_agent_config_generator", sequenceName = "ai_agent_config_sequence",
      allocationSize = 1)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @Column(name = "is_active")
  private Boolean isActive;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "chat_model_config_id", referencedColumnName = "id")
  private AiChatModelConfig chatModelConfig;

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

  public AiAgentConfig() {}

  public AiAgentConfig(Long id) {
    this();
    setId(id);
  }

  public AiAgentConfig(Long id, String name, String description, Boolean isActive,
      AiChatModelConfig chatModelConfig, AiChatMemoryConfig chatMemoryConfig,
      LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
    this(id);
    setName(name);
    setDescription(description);
    setIsActive(isActive);
    setChatModelConfig(chatModelConfig);
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
  public AiAgentConfig setId(Long id) {
    this.id = id;
    return this;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public AiAgentConfig setName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public AiAgentConfig setDescription(String description) {
    this.description = description;
    return this;
  }

  @Override
  public Boolean getIsActive() {
    return isActive;
  }

  @Override
  public AiAgentConfig setIsActive(Boolean isActive) {
    this.isActive = isActive;
    return this;
  }

  @Override
  public AiChatModelConfig getChatModelConfig() {
    return chatModelConfig;
  }

  @Override
  public AiAgentConfig setChatModelConfig(AiChatModelConfig chatModelConfig) {
    this.chatModelConfig = chatModelConfig;
    return this;
  }

  @Override
  public AiChatMemoryConfig getChatMemoryConfig() {
    return chatMemoryConfig;
  }

  @Override
  public AiAgentConfig setChatMemoryConfig(AiChatMemoryConfig chatMemoryConfig) {
    this.chatMemoryConfig = chatMemoryConfig;
    return this;
  }

  @Override
  public Integer getVersion() {
    return version;
  }

  public AiAgentConfig setVersion(Integer version) {
    this.version = version;
    return this;
  }

  @Override
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  @Override
  public AiAgentConfig setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  @Override
  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  @Override
  public AiAgentConfig setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }
}
