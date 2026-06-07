package io.trishul.ai.guardrail.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.trishul.base.types.base.pojo.Audited;
import io.trishul.base.types.base.pojo.CrudEntity;
import io.trishul.model.base.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "ai_guardrail")
@Table(name = "AI_GUARDRAIL")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class AiGuardrail extends BaseEntity
    implements CrudEntity<Long, AiGuardrail>, UpdateAiGuardrail<AiGuardrail>, Audited<AiGuardrail> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ai_guardrail_generator")
  @SequenceGenerator(name = "ai_guardrail_generator", sequenceName = "ai_guardrail_sequence",
      allocationSize = 1)
  private Long id;

  @Column(name = "name")
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private AiGuardrailType type;

  @Column(name = "strategy")
  private String strategy;

  @Column(name = "configuration", columnDefinition = "TEXT")
  private String configuration;

  @Column(name = "priority")
  private Integer priority;

  @Column(name = "is_enabled")
  private Boolean isEnabled;

  @Version
  private Integer version;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "last_updated")
  private LocalDateTime lastUpdated;

  public AiGuardrail() {}

  public AiGuardrail(Long id) {
    this();
    setId(id);
  }

  public AiGuardrail(Long id, String name, AiGuardrailType type, String strategy,
      String configuration, Integer priority, Boolean isEnabled, LocalDateTime createdAt,
      LocalDateTime lastUpdated, Integer version) {
    this(id);
    setName(name);
    setType(type);
    setStrategy(strategy);
    setConfiguration(configuration);
    setPriority(priority);
    setIsEnabled(isEnabled);
    setCreatedAt(createdAt);
    setLastUpdated(lastUpdated);
    setVersion(version);
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public AiGuardrail setId(Long id) {
    this.id = id;
    return this;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public AiGuardrail setName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public AiGuardrailType getType() {
    return type;
  }

  @Override
  public AiGuardrail setType(AiGuardrailType type) {
    this.type = type;
    return this;
  }

  @Override
  public String getStrategy() {
    return strategy;
  }

  @Override
  public AiGuardrail setStrategy(String strategy) {
    this.strategy = strategy;
    return this;
  }

  @Override
  public String getConfiguration() {
    return configuration;
  }

  @Override
  public AiGuardrail setConfiguration(String configuration) {
    this.configuration = configuration;
    return this;
  }

  @Override
  public Integer getPriority() {
    return priority;
  }

  @Override
  public AiGuardrail setPriority(Integer priority) {
    this.priority = priority;
    return this;
  }

  @Override
  public Boolean getIsEnabled() {
    return isEnabled;
  }

  @Override
  public AiGuardrail setIsEnabled(Boolean isEnabled) {
    this.isEnabled = isEnabled;
    return this;
  }

  @Override
  public Integer getVersion() {
    return version;
  }

  public AiGuardrail setVersion(Integer version) {
    this.version = version;
    return this;
  }

  @Override
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  @Override
  public AiGuardrail setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  @Override
  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  @Override
  public AiGuardrail setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }
}
