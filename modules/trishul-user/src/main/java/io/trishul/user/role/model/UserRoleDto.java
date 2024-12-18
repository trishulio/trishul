package io.trishul.user.role.model;

import io.trishul.model.base.dto.BaseDto;
import java.time.LocalDateTime;

public class UserRoleDto extends BaseDto {
    private Long id;

    private String name;

    private LocalDateTime createdAt;

    private LocalDateTime lastUpdated;

    private Integer version;

    public UserRoleDto() {}

    public UserRoleDto(Long id) {
        setId(id);
    }

    public UserRoleDto(
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

    public Long getId() {
        return id;
    }

    public final void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
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
