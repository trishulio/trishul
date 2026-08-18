package sh.trishul.ai.agent.model;

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
import sh.trishul.ai.skill.model.AiSkill;
import sh.trishul.base.types.base.pojo.Audited;
import sh.trishul.base.types.base.pojo.CrudEntity;
import sh.trishul.model.base.entity.BaseEntity;

@Entity(name = "ai_agent_skill")
@Table(name = "AI_AGENT_SKILL")
public class AiAgentSkill extends BaseEntity
    implements CrudEntity<Long, AiAgentSkill>, Audited<AiAgentSkill> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ai_agent_skill_generator")
  @SequenceGenerator(name = "ai_agent_skill_generator", sequenceName = "ai_agent_skill_sequence",
      allocationSize = 1)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "agent_config_id", referencedColumnName = "id")
  private AiAgentConfig agentConfig;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "skill_id", referencedColumnName = "id")
  private AiSkill skill;

  @Version
  private Integer version;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "last_updated")
  private LocalDateTime lastUpdated;

  public AiAgentSkill() {}

  public AiAgentSkill(Long id) {
    this();
    setId(id);
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public AiAgentSkill setId(Long id) {
    this.id = id;
    return this;
  }

  public AiAgentConfig getAgentConfig() {
    return agentConfig;
  }

  public AiAgentSkill setAgentConfig(AiAgentConfig agentConfig) {
    this.agentConfig = agentConfig;
    return this;
  }

  public AiSkill getSkill() {
    return skill;
  }

  public AiAgentSkill setSkill(AiSkill skill) {
    this.skill = skill;
    return this;
  }

  public Integer getVersion() {
    return version;
  }

  public AiAgentSkill setVersion(Integer version) {
    this.version = version;
    return this;
  }

  @Override
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  @Override
  public AiAgentSkill setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  @Override
  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  @Override
  public AiAgentSkill setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }
}
