package io.trishul.ai.agent.model;

import io.trishul.ai.guardrail.model.AiGuardrail;
import io.trishul.base.types.base.pojo.Audited;
import io.trishul.base.types.base.pojo.CrudEntity;
import io.trishul.model.base.entity.BaseEntity;
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

@Entity(name = "ai_agent_guardrail")
@Table(name = "AI_AGENT_GUARDRAIL")
public class AiAgentGuardrail extends BaseEntity
    implements CrudEntity<Long, AiAgentGuardrail>, Audited<AiAgentGuardrail> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ai_agent_guardrail_generator")
  @SequenceGenerator(name = "ai_agent_guardrail_generator",
      sequenceName = "ai_agent_guardrail_sequence", allocationSize = 1)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "agent_config_id", referencedColumnName = "id")
  private AiAgentConfig agentConfig;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "guardrail_id", referencedColumnName = "id")
  private AiGuardrail guardrail;

  @Version
  private Integer version;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "last_updated")
  private LocalDateTime lastUpdated;

  public AiAgentGuardrail() {}

  public AiAgentGuardrail(Long id) {
    this();
    setId(id);
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public AiAgentGuardrail setId(Long id) {
    this.id = id;
    return this;
  }

  public AiAgentConfig getAgentConfig() {
    return agentConfig;
  }

  public AiAgentGuardrail setAgentConfig(AiAgentConfig agentConfig) {
    this.agentConfig = agentConfig;
    return this;
  }

  public AiGuardrail getGuardrail() {
    return guardrail;
  }

  public AiAgentGuardrail setGuardrail(AiGuardrail guardrail) {
    this.guardrail = guardrail;
    return this;
  }

  public Integer getVersion() {
    return version;
  }

  public AiAgentGuardrail setVersion(Integer version) {
    this.version = version;
    return this;
  }

  @Override
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  @Override
  public AiAgentGuardrail setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  @Override
  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  @Override
  public AiAgentGuardrail setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }
}
