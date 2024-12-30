package io.trishul.user.model;

import java.net.URI;
import java.util.List;
import io.trishul.model.base.dto.BaseDto;
import io.trishul.model.validation.NullOrNotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UpdateUserDto extends BaseDto {
  private Long id;

  @NullOrNotBlank
  private String userName;

  @NullOrNotBlank
  private String displayName;

  @NullOrNotBlank
  private String firstName;

  @NullOrNotBlank
  private String lastName;

  private Long statusId;

  private Long salutationId;

  @NullOrNotBlank
  private String phoneNumber;

  private URI imageSrc;

  @Size(min = 1)
  private List<Long> roleIds;

  @NotNull
  private Integer version;

  public UpdateUserDto() {
    super();
  }

  public UpdateUserDto(Long id) {
    this();
    setId(id);
  }

  public UpdateUserDto(Long id, String userName, String displayName, String firstName,
      String lastName, Long statusId, Long salutationId, String phoneNumber, URI imageSrc,
      List<Long> roleIds, @NotNull Integer version) {
    this(id);
    setUserName(userName);
    setDisplayName(displayName);
    setFirstName(firstName);
    setLastName(lastName);
    setStatusId(statusId);
    setSalutationId(salutationId);
    setPhoneNumber(phoneNumber);
    setRoleIds(roleIds);
    setImageSrc(imageSrc);
    setVersion(version);
  }

  public Long getId() {
    return id;
  }

  public final UpdateUserDto setId(Long id) {
    this.id = id;
    return this;
  }

  public String getUserName() {
    return userName;
  }

  public final UpdateUserDto setUserName(String userName) {
    this.userName = userName;
    return this;
  }

  public String getDisplayName() {
    return displayName;
  }

  public final UpdateUserDto setDisplayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

  public String getFirstName() {
    return firstName;
  }

  public final UpdateUserDto setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public final UpdateUserDto setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public Long getStatusId() {
    return statusId;
  }

  public final UpdateUserDto setStatusId(Long statusId) {
    this.statusId = statusId;
    return this;
  }

  public Long getSalutationId() {
    return salutationId;
  }

  public final UpdateUserDto setSalutationId(Long salutationId) {
    this.salutationId = salutationId;
    return this;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public final UpdateUserDto setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  public List<Long> getRoleIds() {
    return roleIds;
  }

  public final UpdateUserDto setRoleIds(List<Long> roleIds) {
    this.roleIds = roleIds;
    return this;
  }

  public URI getImageSrc() {
    return imageSrc;
  }

  public final UpdateUserDto setImageSrc(URI imageSrc) {
    this.imageSrc = imageSrc;
    return this;
  }

  public Integer getVersion() {
    return version;
  }

  public final UpdateUserDto setVersion(Integer version) {
    this.version = version;
    return this;
  }
}
