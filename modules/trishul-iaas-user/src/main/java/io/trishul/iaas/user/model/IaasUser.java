package io.trishul.iaas.user.model;

import io.trishul.base.types.base.pojo.Audited;
import io.trishul.base.types.base.pojo.CrudEntity;
import io.trishul.model.base.entity.BaseEntity;
import java.time.LocalDateTime;

public class IaasUser extends BaseEntity implements CrudEntity<String>, UpdateIaasUser, Audited {
    private String userName;
    private String email;
    private String phoneNumber; // We are not persisting it in the IDP
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;

    public IaasUser() {
        super();
    }

    public IaasUser(String id) {
        this();
        setId(id);
    }

    public IaasUser(
            String userName,
            String email,
            String phoneNumber,
            LocalDateTime createdAt,
            LocalDateTime lastUpdated) {
        this(email);
        setUserName(userName);
        setPhoneNumber(phoneNumber);
        setCreatedAt(createdAt);
        setLastUpdated(lastUpdated);
    }

    @Override
    public String getId() {
        return getEmail();
    }

    @Override
    public void setId(String id) {
        setEmail(id);
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
        // Not implemented due to lack of use-case
        return null;
    }
}
