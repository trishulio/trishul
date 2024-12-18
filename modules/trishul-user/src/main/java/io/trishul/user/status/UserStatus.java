package io.trishul.user.status;

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

@Entity(name = "user_status")
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class UserStatus extends BaseEntity implements BaseUserStatus, UpdateUserStatus, Audited {
    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_status_generator")
    @SequenceGenerator(
            name = "user_status_generator",
            sequenceName = "user_status_sequence",
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

    @Version private Integer version;

    public UserStatus() {}

    public UserStatus(Long id) {
        this();
        setId(id);
    }

    public UserStatus(
            Long id,
            String name,
            LocalDateTime createdAt,
            LocalDateTime lastUpdated,
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
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
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
