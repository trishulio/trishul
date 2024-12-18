package io.trishul.user.salutation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.trishul.base.types.base.pojo.Audited;
import io.trishul.model.base.entity.BaseEntity;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "user_salutation")
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class UserSalutation extends BaseEntity implements UpdateUserSalutation, Audited {
    public static final String FIELD_ID = "id";
    public static final String FIELD_TITLE = "title";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_salutation_generator")
    @SequenceGenerator(
            name = "user_salutation_generator",
            sequenceName = "user_salutation_sequence",
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

    @Version private Integer version;

    public UserSalutation() {}

    public UserSalutation(Long id) {
        this();
        setId(id);
    }

    public UserSalutation(
            Long id,
            String title,
            LocalDateTime createdAt,
            LocalDateTime lastUpdated,
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
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public LocalDateTime getLastUpdated() {
        return this.lastUpdated;
    }

    @Override
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
