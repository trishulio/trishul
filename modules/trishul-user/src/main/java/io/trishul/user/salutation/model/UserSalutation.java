package io.trishul.user.salutation.model;

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
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "user_salutation")
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class UserSalutation extends BaseEntity
    implements UpdateUserSalutation<UserSalutation>, Audited<UserSalutation> {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_salutation_generator")
  @SequenceGenerator(name = "user_salutation_generator", sequenceName = "user_salutation_sequence",
      allocationSize = 1)
  private Long id;

  @Column(name = "name", nullable = false, unique = true)
  private String title;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "last_updated")
  private LocalDateTime lastUpdated;

  @Version
  private Integer version;

  public UserSalutation() {}

  public UserSalutation(Long id) {
    this();
    setId(id);
  }

  public UserSalutation(Long id, String title, LocalDateTime createdAt, LocalDateTime lastUpdated,
      Integer version) {
    this(id);
    setTitle(title);
    setCreatedAt(createdAt);
    setLastUpdated(lastUpdated);
    setVersion(version);
  }

  @Override
  public Long getId() {
    return this.id;
  }

  @Override
  public UserSalutation setId(Long id) {
    this.id = id;
    return this;
  }

  @Override
  public String getTitle() {
    return this.title;
  }

  @Override
  public UserSalutation setTitle(String title) {
    this.title = title;
    return this;
  }

  @Override
  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }

  @Override
  public UserSalutation setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  @Override
  public LocalDateTime getLastUpdated() {
    return this.lastUpdated;
  }

  @Override
  public UserSalutation setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }

  @Override
  public Integer getVersion() {
    return this.version;
  }

  public UserSalutation setVersion(Integer version) {
    this.version = version;
    return this;
  }
}
