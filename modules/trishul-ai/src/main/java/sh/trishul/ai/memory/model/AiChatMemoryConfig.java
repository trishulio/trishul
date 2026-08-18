package sh.trishul.ai.memory.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import sh.trishul.base.types.base.pojo.Audited;
import sh.trishul.base.types.base.pojo.CrudEntity;
import sh.trishul.model.base.entity.BaseEntity;

@Entity(name = "ai_chat_memory_config")
@Table(name = "AI_CHAT_MEMORY_CONFIG")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class AiChatMemoryConfig extends BaseEntity implements CrudEntity<Long, AiChatMemoryConfig>,
    UpdateAiChatMemoryConfig<AiChatMemoryConfig>, Audited<AiChatMemoryConfig> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ai_chat_memory_config_generator")
  @SequenceGenerator(name = "ai_chat_memory_config_generator",
      sequenceName = "ai_chat_memory_config_sequence", allocationSize = 1)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "strategy")
  private String strategy;

  @Column(name = "max_messages")
  private Integer maxMessages;

  @Column(name = "max_tokens")
  private Integer maxTokens;

  @Column(name = "ttl_minutes")
  private Integer ttlMinutes;

  @Column(name = "is_default")
  private Boolean isDefault;

  @Version
  private Integer version;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "last_updated")
  private LocalDateTime lastUpdated;

  public AiChatMemoryConfig() {}

  public AiChatMemoryConfig(Long id) {
    this();
    setId(id);
  }

  public AiChatMemoryConfig(Long id, String name, String strategy, Integer maxMessages,
      Integer maxTokens, Integer ttlMinutes, Boolean isDefault, LocalDateTime createdAt,
      LocalDateTime lastUpdated, Integer version) {
    this(id);
    setName(name);
    setStrategy(strategy);
    setMaxMessages(maxMessages);
    setMaxTokens(maxTokens);
    setTtlMinutes(ttlMinutes);
    setIsDefault(isDefault);
    setCreatedAt(createdAt);
    setLastUpdated(lastUpdated);
    setVersion(version);
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public AiChatMemoryConfig setId(Long id) {
    this.id = id;
    return this;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public AiChatMemoryConfig setName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public String getStrategy() {
    return strategy;
  }

  @Override
  public AiChatMemoryConfig setStrategy(String strategy) {
    this.strategy = strategy;
    return this;
  }

  @Override
  public Integer getMaxMessages() {
    return maxMessages;
  }

  @Override
  public AiChatMemoryConfig setMaxMessages(Integer maxMessages) {
    this.maxMessages = maxMessages;
    return this;
  }

  @Override
  public Integer getMaxTokens() {
    return maxTokens;
  }

  @Override
  public AiChatMemoryConfig setMaxTokens(Integer maxTokens) {
    this.maxTokens = maxTokens;
    return this;
  }

  @Override
  public Integer getTtlMinutes() {
    return ttlMinutes;
  }

  @Override
  public AiChatMemoryConfig setTtlMinutes(Integer ttlMinutes) {
    this.ttlMinutes = ttlMinutes;
    return this;
  }

  @Override
  public Boolean getIsDefault() {
    return isDefault;
  }

  @Override
  public AiChatMemoryConfig setIsDefault(Boolean isDefault) {
    this.isDefault = isDefault;
    return this;
  }

  @Override
  public Integer getVersion() {
    return version;
  }

  public AiChatMemoryConfig setVersion(Integer version) {
    this.version = version;
    return this;
  }

  @Override
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  @Override
  public AiChatMemoryConfig setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  @Override
  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  @Override
  public AiChatMemoryConfig setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }
}
