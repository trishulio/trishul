package io.trishul.user.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.trishul.base.types.base.pojo.Audited;
import io.trishul.base.types.base.pojo.CrudEntity;
import io.trishul.model.base.entity.BaseEntity;
import io.trishul.model.base.entity.CriteriaJoin;
import io.trishul.user.role.binding.model.UserRoleBinding;
import io.trishul.user.role.model.UserRole;
import io.trishul.user.salutation.model.UserSalutation;
import io.trishul.user.status.UserStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Email;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "user")
@Table(name = "_user")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class User extends BaseEntity
    implements CrudEntity<Long, User>, UpdateUser<User>, Audited<User> {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
  @SequenceGenerator(name = "user_generator", sequenceName = "user_sequence", allocationSize = 1)
  private Long id;

  @Column(name = "user_name", unique = true)
  private String userName;

  @Column(name = "display_name")
  private String displayName;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "email", updatable = false, unique = true)
  @Email
  private String email;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true,
      fetch = FetchType.LAZY)
  @JsonManagedReference
  @CriteriaJoin
  private List<UserRoleBinding> roleBindings;

  @Column(name = "image_source")
  private String imageSrc;

  @Column(name = "phone_number")
  private String phoneNumber;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_status_id", referencedColumnName = "id")
  private UserStatus status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_salutation_id", referencedColumnName = "id")
  private UserSalutation salutation;

  @Version
  private Integer version;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "last_updated")
  private LocalDateTime lastUpdated;

  public User() {}

  public User(Long id) {
    this();
    setId(id);
  }

  public User(Long id, String userName, String displayName, String firstName, String lastName,
      String email, String phoneNumber, URI imageSrc, UserStatus status, UserSalutation salutation,
      List<UserRole> roles, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
    this(id);
    setUserName(userName);
    setDisplayName(displayName);
    setFirstName(firstName);
    setLastName(lastName);
    setEmail(email);
    setPhoneNumber(phoneNumber);
    setStatus(status);
    setImageSrc(imageSrc);
    setRoles(roles);
    setSalutation(salutation);
    setCreatedAt(createdAt);
    setLastUpdated(lastUpdated);
    setVersion(version);
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public User setId(Long id) {
    this.id = id;
    return this;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }

  @Override
  public User setDisplayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

  @Override
  public String getFirstName() {
    return firstName;
  }

  @Override
  public User setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  @Override
  public String getLastName() {
    return lastName;
  }

  @Override
  public User setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  @Override
  public String getEmail() {
    return email;
  }

  @Override
  public User setEmail(String email) {
    this.email = email;
    return this;
  }

  @Override
  public URI getImageSrc() {
    URI uri = null;
    if (this.imageSrc != null) {
      try {
        uri = new URI(this.imageSrc);
      } catch (URISyntaxException e) {
        throw new RuntimeException(
            String.format("Failed to convert to URI, value: %s", this.imageSrc), e);
      }
    }

    return uri;
  }

  @Override
  public User setImageSrc(URI imageSrc) {
    if (imageSrc != null) {
      this.imageSrc = imageSrc.toString();
    } else {
      this.imageSrc = null;
    }
    return this;
  }

  @Override
  public String getPhoneNumber() {
    return phoneNumber;
  }

  @Override
  public User setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  @Override
  public UserStatus getStatus() {
    return status == null ? null : status.deepClone();
  }

  @Override
  public User setStatus(UserStatus status) {
    this.status = status == null ? null : status.deepClone();
    return this;
  }

  @Override
  public Integer getVersion() {
    return version;
  }

  public User setVersion(Integer version) {
    this.version = version;
    return this;
  }

  @Override
  public String getUserName() {
    return userName;
  }

  @Override
  public User setUserName(String userName) {
    this.userName = userName;
    return this;
  }

  @Override
  public User setRoles(List<UserRole> roles) {
    if (roles == null) {
      if (this.roleBindings != null) {
        this.roleBindings.clear();
      } else {
        this.roleBindings = null;
      }
      return this;
    }

    // Reusing existing bindings instead of adding new to avoid creating dangling
    // child
    // entities.
    if (roleBindings == null) {
      roleBindings = new ArrayList<>();
    }

    Map<Long, UserRoleBinding> existingBindings = roleBindings.stream()
        .collect(Collectors.toMap(binding -> binding.getRole().getId(), binding -> binding));
    this.roleBindings.clear();
    roles.forEach(role -> {
      UserRoleBinding existing = existingBindings.remove(role.getId());
      if (existing == null) {
        existing = new UserRoleBinding();
      }

      existing.setRole(role);
      existing.setUser(this);

      this.roleBindings.add(existing);
    });
    return this;
  }

  @Override
  public List<UserRole> getRoles() {
    if (this.roleBindings == null) {
      return null;
    }

    return this.roleBindings.stream().map(binding -> binding.getRole()).toList();
  }

  /**
   * Used by the repository to directly access the bindings. Refrain from using this is business
   * logic. Prefer, getRoles() method.
   *
   * @return
   */
  public List<UserRoleBinding> getRoleBindings() {
    return new ArrayList<>(this.roleBindings);
  }

  @Override
  public UserSalutation getSalutation() {
    return salutation == null ? null : salutation.deepClone();
  }

  @Override
  public User setSalutation(UserSalutation salutation) {
    this.salutation = salutation == null ? null : salutation.deepClone();
    return this;
  }

  @Override
  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  @Override
  public User setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }

  @Override
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  @Override
  public User setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }
}
