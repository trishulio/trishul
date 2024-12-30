package io.trishul.user.model;

import io.trishul.model.base.dto.BaseDto;
import io.trishul.object.store.file.model.accessor.DecoratedIaasObjectStoreFileAccessor;
import io.trishul.object.store.file.model.dto.IaasObjectStoreFileDto;
import io.trishul.user.role.model.UserRoleDto;
import io.trishul.user.salutation.model.UserSalutationDto;
import io.trishul.user.status.UserStatusDto;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

public class UserDto extends BaseDto implements DecoratedIaasObjectStoreFileAccessor<UserDto> {
  private Long id;

  private String userName;

  private String displayName;

  private String firstName;

  private String lastName;

  private String email;

  private String phoneNumber;

  private URI imageSrc;

  private IaasObjectStoreFileDto objectStoreFile;

  private UserStatusDto status;

  private UserSalutationDto salutation;

  private List<UserRoleDto> roles;

  private LocalDateTime createdAt;

  private LocalDateTime lastUpdated;

  private Integer version;

  public UserDto() {}

  public UserDto(Long id) {
    this();
    setId(id);
  }

  public UserDto(Long id, String userName, String displayName, String firstName, String lastName,
      String email, String phoneNumber, URI imageSrc, IaasObjectStoreFileDto objectStoreFile,
      UserStatusDto status, UserSalutationDto salutation, List<UserRoleDto> roles,
      LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
    this(id);
    setUserName(userName);
    setDisplayName(displayName);
    setFirstName(firstName);
    setLastName(lastName);
    setEmail(email);
    setPhoneNumber(phoneNumber);
    setImageSrc(imageSrc);
    setObjectStoreFile(objectStoreFile);
    setStatus(status);
    setSalutation(salutation);
    setRoles(roles);
    setCreatedAt(createdAt);
    setLastUpdated(lastUpdated);
    setLastName(lastName);
    setVersion(version);
  }

  public Long getId() {
    return id;
  }

  public final UserDto setId(Long id) {
    this.id = id;
    return this;
  }

  public String getUserName() {
    return userName;
  }

  public final UserDto setUserName(String userName) {
    this.userName = userName;
    return this;
  }

  public String getDisplayName() {
    return displayName;
  }

  public final UserDto setDisplayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

  public String getFirstName() {
    return firstName;
  }

  public final UserDto setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public final UserDto setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public final UserDto setEmail(String email) {
    this.email = email;
    return this;
  }

  public UserStatusDto getStatus() {
    return status;
  }

  public final UserDto setStatus(UserStatusDto status) {
    this.status = status;
    return this;
  }

  public UserSalutationDto getSalutation() {
    return salutation;
  }

  public final UserDto setSalutation(UserSalutationDto salutation) {
    this.salutation = salutation;
    return this;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public final UserDto setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  public List<UserRoleDto> getRoles() {
    return roles;
  }

  public final UserDto setRoles(List<UserRoleDto> roles) {
    this.roles = roles;
    return this;
  }

  @Override
  public URI getImageSrc() {
    return this.imageSrc;
  }

  public final UserDto setImageSrc(URI imageSrc) {
    this.imageSrc = imageSrc;
    return this;
  }

  public IaasObjectStoreFileDto getObjectStoreFile() {
    return this.objectStoreFile;
  }

  @Override
  public final UserDto setObjectStoreFile(IaasObjectStoreFileDto objectStoreFile) {
    this.objectStoreFile = objectStoreFile;
    return this;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public final UserDto setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  public final UserDto setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }

  public Integer getVersion() {
    return version;
  }

  public final UserDto setVersion(Integer version) {
    this.version = version;
    return this;
  }
}
