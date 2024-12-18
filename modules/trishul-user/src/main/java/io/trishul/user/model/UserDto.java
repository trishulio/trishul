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

public class UserDto extends BaseDto implements DecoratedIaasObjectStoreFileAccessor {
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

    public UserDto(
            Long id,
            String userName,
            String displayName,
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            URI imageSrc,
            IaasObjectStoreFileDto objectStoreFile,
            UserStatusDto status,
            UserSalutationDto salutation,
            List<UserRoleDto> roles,
            LocalDateTime createdAt,
            LocalDateTime lastUpdated,
            Integer version) {
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

    public final void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public final void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public final void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFirstName() {
        return firstName;
    }

    public final void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public final void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public final void setEmail(String email) {
        this.email = email;
    }

    public UserStatusDto getStatus() {
        return status;
    }

    public final void setStatus(UserStatusDto status) {
        this.status = status;
    }

    public UserSalutationDto getSalutation() {
        return salutation;
    }

    public final void setSalutation(UserSalutationDto salutation) {
        this.salutation = salutation;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public final void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<UserRoleDto> getRoles() {
        return roles;
    }

    public final void setRoles(List<UserRoleDto> roles) {
        this.roles = roles;
    }

    @Override
    public URI getImageSrc() {
        return this.imageSrc;
    }

    public final void setImageSrc(URI imageSrc) {
        this.imageSrc = imageSrc;
    }

    public IaasObjectStoreFileDto getObjectStoreFile() {
        return this.objectStoreFile;
    }

    @Override
    public final void setObjectStoreFile(IaasObjectStoreFileDto objectStoreFile) {
        this.objectStoreFile = objectStoreFile;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public final void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public final void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Integer getVersion() {
        return version;
    }

    public final void setVersion(Integer version) {
        this.version = version;
    }
}
