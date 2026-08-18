package sh.trishul.ai.skill.model;

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

@Entity(name = "ai_skill")
@Table(name = "AI_SKILL")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class AiSkill extends BaseEntity
    implements CrudEntity<Long, AiSkill>, UpdateAiSkill<AiSkill>, Audited<AiSkill> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ai_skill_generator")
  @SequenceGenerator(name = "ai_skill_generator", sequenceName = "ai_skill_sequence",
      allocationSize = 1)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @Column(name = "system_prompt", columnDefinition = "TEXT")
  private String systemPrompt;

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

  public AiSkill() {}

  public AiSkill(Long id) {
    this();
    setId(id);
  }

  public AiSkill(Long id, String name, String description, String systemPrompt, Boolean isEnabled,
      LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
    this(id);
    setName(name);
    setDescription(description);
    setSystemPrompt(systemPrompt);
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
  public AiSkill setId(Long id) {
    this.id = id;
    return this;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public AiSkill setName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public AiSkill setDescription(String description) {
    this.description = description;
    return this;
  }

  @Override
  public String getSystemPrompt() {
    return systemPrompt;
  }

  @Override
  public AiSkill setSystemPrompt(String systemPrompt) {
    this.systemPrompt = systemPrompt;
    return this;
  }

  @Override
  public Boolean getIsEnabled() {
    return isEnabled;
  }

  @Override
  public AiSkill setIsEnabled(Boolean isEnabled) {
    this.isEnabled = isEnabled;
    return this;
  }

  @Override
  public Integer getVersion() {
    return version;
  }

  public AiSkill setVersion(Integer version) {
    this.version = version;
    return this;
  }

  @Override
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  @Override
  public AiSkill setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  @Override
  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  @Override
  public AiSkill setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }
}
