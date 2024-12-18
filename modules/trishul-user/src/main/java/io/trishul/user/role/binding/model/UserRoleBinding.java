package io.trishul.user.role.binding.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.trishul.base.types.base.pojo.Audited;
import io.trishul.base.types.base.pojo.UpdatableEntity;
import io.trishul.model.base.entity.BaseEntity;
import io.trishul.user.model.User;
import io.trishul.user.model.UserAccessor;
import io.trishul.user.role.model.UserRole;
import io.trishul.user.role.model.UserRoleAccessor;
import java.time.LocalDateTime;
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
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/***
 * There exists a many-to-many relationship between a user and a user-role. But
 * due to hibernate performance issues, it is better to represent a many-to-many
 * relationship as a bi-directional one-to-many relationship. This is an
 * intermediary class to create that relationship between a user and a role
 * entity. It's modelled not to be used directly outside the user context and
 * hence always hidden under the user class' implementation.
 *
 * @author Rishab Manocha
 *
 */

@Entity
@Table(name = "user_role_binding")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class UserRoleBinding extends BaseEntity
        implements Audited, UserRoleAccessor, UserAccessor, UpdatableEntity<Long> {
    public static final String FIELD_ID = "id";
    public static final String FIELD_USER_ROLE_TYPE = "role";
    public static final String FIELD_USER = "user";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_role_binding_generator")
    @SequenceGenerator(
            name = "user_role_binding_generator",
            sequenceName = "user_role_binding_sequence",
            allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_role_id", referencedColumnName = "id")
    private UserRole role;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private User user;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @Version private Integer version;

    public UserRoleBinding() {}

    public UserRoleBinding(Long id) {
        this();
        setId(id);
    }

    public UserRoleBinding(Long id, UserRole role, User user) {
        this(id);
        setRole(role);
        setUser(user);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public UserRole getRole() {
        return role;
    }

    @Override
    public void setRole(UserRole userRole) {
        this.role = userRole;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    @Override
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
