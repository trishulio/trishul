package io.trishul.user.model;

import io.trishul.model.base.dto.BaseDto;
import java.net.URI;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AddUserDto extends BaseDto {
    @NotBlank private String userName;

    @NotBlank private String displayName;

    @NotBlank private String firstName;

    @NotBlank private String lastName;

    @NotBlank @Email private String email;

    @NotNull private Long statusId;

    private Long salutationId;

    private String phoneNumber;

    @NotEmpty private List<Long> roleIds;

    private URI imageSrc;

    public AddUserDto() {}

    public AddUserDto(
            @NotBlank String userName,
            @NotBlank String displayName,
            @NotBlank String firstName,
            @NotBlank String lastName,
            @NotBlank String email,
            @NotNull Long statusId,
            @NotNull Long salutationId,
            @NotBlank String phoneNumber,
            URI imageSrc,
            List<Long> roleIds) {
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

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getSalutationId() {
        return salutationId;
    }

    public void setSalutationId(Long salutationId) {
        this.salutationId = salutationId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public URI getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(URI imageSrc) {
        this.imageSrc = imageSrc;
    }
}
