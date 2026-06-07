package io.trishul.ai.tool.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.trishul.base.types.base.pojo.Audited;
import io.trishul.base.types.base.pojo.CrudEntity;
import io.trishul.model.base.entity.BaseEntity;
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

@Entity(name = "ai_tool")
@Table(name = "AI_TOOL")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class AiTool extends BaseEntity
    implements CrudEntity<Long, AiTool>, UpdateAiTool<AiTool>, Audited<AiTool> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ai_tool_generator")
  @SequenceGenerator(name = "ai_tool_generator", sequenceName = "ai_tool_sequence",
      allocationSize = 1)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "bean_name")
  private String beanName;

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

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

  public AiTool() {}

  public AiTool(Long id) {
    this();
    setId(id);
  }

  public AiTool(Long id, String name, String beanName, String description, Boolean isEnabled,
      LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
    this(id);
    setName(name);
    setBeanName(beanName);
    setDescription(description);
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
  public AiTool setId(Long id) {
    this.id = id;
    return this;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public AiTool setName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public String getBeanName() {
    return beanName;
  }

  @Override
  public AiTool setBeanName(String beanName) {
    this.beanName = beanName;
    return this;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public AiTool setDescription(String description) {
    this.description = description;
    return this;
  }

  @Override
  public Boolean getIsEnabled() {
    return isEnabled;
  }

  @Override
  public AiTool setIsEnabled(Boolean isEnabled) {
    this.isEnabled = isEnabled;
    return this;
  }

  @Override
  public Integer getVersion() {
    return version;
  }

  public AiTool setVersion(Integer version) {
    this.version = version;
    return this;
  }

  @Override
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  @Override
  public AiTool setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  @Override
  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  @Override
  public AiTool setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }
}
