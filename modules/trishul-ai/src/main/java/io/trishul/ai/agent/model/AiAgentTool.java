package io.trishul.ai.agent.model;

import io.trishul.ai.tool.model.AiTool;
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

@Entity(name = "ai_agent_tool")
@Table(name = "AI_AGENT_TOOL")
public class AiAgentTool extends BaseEntity
    implements CrudEntity<Long, AiAgentTool>, Audited<AiAgentTool> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ai_agent_tool_generator")
  @SequenceGenerator(name = "ai_agent_tool_generator", sequenceName = "ai_agent_tool_sequence",
      allocationSize = 1)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "agent_config_id", referencedColumnName = "id")
  private AiAgentConfig agentConfig;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tool_id", referencedColumnName = "id")
  private AiTool tool;

  @Version
  private Integer version;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "last_updated")
  private LocalDateTime lastUpdated;

  public AiAgentTool() {}

  public AiAgentTool(Long id) {
    this();
    setId(id);
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public AiAgentTool setId(Long id) {
    this.id = id;
    return this;
  }

  public AiAgentConfig getAgentConfig() {
    return agentConfig;
  }

  public AiAgentTool setAgentConfig(AiAgentConfig agentConfig) {
    this.agentConfig = agentConfig;
    return this;
  }

  public AiTool getTool() {
    return tool;
  }

  public AiAgentTool setTool(AiTool tool) {
    this.tool = tool;
    return this;
  }

  public Integer getVersion() {
    return version;
  }

  public AiAgentTool setVersion(Integer version) {
    this.version = version;
    return this;
  }

  @Override
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  @Override
  public AiAgentTool setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  @Override
  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  @Override
  public AiAgentTool setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }
}
