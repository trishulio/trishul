package io.trishul.user.model;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import io.trishul.model.base.dto.BaseDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class AddUserDto extends BaseDto {
  @NotBlank
  private String userName;

  @NotBlank
  private String displayName;

  @NotBlank
  private String firstName;

  @NotBlank
  private String lastName;

  @NotBlank
  @Email
  private String email;

  @NotNull
  private Long statusId;

  private Long salutationId;

  private String phoneNumber;

  @NotEmpty
  private List<Long> roleIds;

  private URI imageSrc;

  public AddUserDto() {}

  public AddUserDto(@NotBlank String userName, @NotBlank String displayName,
      @NotBlank String firstName, @NotBlank String lastName, @NotBlank String email,
      @NotNull Long statusId, @NotNull Long salutationId, @NotBlank String phoneNumber,
      URI imageSrc, List<Long> roleIds) {
    setUserName(userName);
    setDisplayName(displayName);
    setFirstName(firstName);
    setLastName(lastName);
    setEmail(email);
    setStatusId(statusId);
    setSalutationId(salutationId);
    setPhoneNumber(phoneNumber);
    setImageSrc(imageSrc);
    setRoleIds(roleIds);
  }

  public String getUserName() {
    return userName;
  }

  public AddUserDto setUserName(String userName) {
    this.userName = userName;
    return this;
  }

  public String getDisplayName() {
    return displayName;
  }

  public AddUserDto setDisplayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

  public String getFirstName() {
    return firstName;
  }

  public AddUserDto setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public AddUserDto setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public AddUserDto setEmail(String email) {
    this.email = email;
    return this;
  }

  public Long getStatusId() {
    return statusId;
  }

  public AddUserDto setStatusId(Long statusId) {
    this.statusId = statusId;
    return this;
  }

  public Long getSalutationId() {
    return salutationId;
  }

  public AddUserDto setSalutationId(Long salutationId) {
    this.salutationId = salutationId;
    return this;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public AddUserDto setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  public List<Long> getRoleIds() {
    return roleIds == null ? null : new ArrayList<>(roleIds);
  }

  public AddUserDto setRoleIds(List<Long> roleIds) {
    this.roleIds = roleIds == null ? null : new ArrayList<>(roleIds);
    return this;
  }

  public URI getImageSrc() {
    return imageSrc;
  }

  public AddUserDto setImageSrc(URI imageSrc) {
    this.imageSrc = imageSrc;
    return this;
  }
}
