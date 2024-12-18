package io.trishul.user.model;

import io.trishul.model.base.dto.BaseDto;
import io.trishul.model.validation.NullOrNotBlank;
import java.net.URI;
import java.util.List;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UpdateUserDto extends BaseDto {
    private Long id;

    @NullOrNotBlank private String userName;

    @NullOrNotBlank private String displayName;

    @NullOrNotBlank private String firstName;

    @NullOrNotBlank private String lastName;

    private Long statusId;

    private Long salutationId;

    @NullOrNotBlank private String phoneNumber;

    private URI imageSrc;

    @Size(min = 1)
    private List<Long> roleIds;

    @NotNull private Integer version;

    public UpdateUserDto() {
        super();
    }

    public UpdateUserDto(Long id) {
        this();
        setId(id);
    }

    public UpdateUserDto(
            Long id,
            String userName,
            String displayName,
            String firstName,
            String lastName,
            Long statusId,
            Long salutationId,
            String phoneNumber,
            URI imageSrc,
            List<Long> roleIds,
            @NotNull Integer version) {
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

    public void setId(Long id) {
        this.id = id;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
