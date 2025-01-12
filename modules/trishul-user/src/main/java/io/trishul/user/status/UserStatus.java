package io.trishul.user.status;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.trishul.base.types.base.pojo.Audited;
import io.trishul.model.base.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity(name = "user_status")
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class UserStatus extends BaseEntity
    implements UpdateUserStatus<UserStatus>, Audited<UserStatus> {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_status_generator")
  @SequenceGenerator(name = "user_status_generator", sequenceName = "user_status_sequence",
      allocationSize = 1)
  private Long id;

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "last_updated")
  private LocalDateTime lastUpdated;

  @Version
  private Integer version;

  public UserStatus() {}

  public UserStatus(Long id) {
    this();
    setId(id);
  }

  public UserStatus(Long id, String name, LocalDateTime createdAt, LocalDateTime lastUpdated,
      Integer version) {
    this(id);
    setName(name);
    setCreatedAt(createdAt);
    setLastUpdated(lastUpdated);
    setVersion(version);
  }

  @Override
  public Long getId() {
    return this.id;
  }

  @Override
  public final UserStatus setId(Long id) {
    this.id = id;
    return this;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public final UserStatus setName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }

  @Override
  public final UserStatus setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  @Override
  public LocalDateTime getLastUpdated() {
    return this.lastUpdated;
  }

  @Override
  public final UserStatus setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }

  @Override
  public Integer getVersion() {
    return this.version;
  }

  public final UserStatus setVersion(Integer version) {
    this.version = version;
    return this;
  }
}
