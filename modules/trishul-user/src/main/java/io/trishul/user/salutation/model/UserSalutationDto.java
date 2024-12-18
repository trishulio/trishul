package io.trishul.user.salutation.model;

import io.trishul.model.base.dto.BaseDto;
import java.time.LocalDateTime;

public class UserSalutationDto extends BaseDto {
    private Long id;

    private String title;

    private LocalDateTime createdAt;

    private LocalDateTime lastUpdated;

    private Integer version;

    public UserSalutationDto() {}

    public UserSalutationDto(Long id) {
        setId(id);
    }

    public UserSalutationDto(
            Long id,
            String title,
            LocalDateTime createdAt,
            LocalDateTime lastUpdated,
            Integer version) {
        this(id);
        setTitle(title);
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

    public String getTitle() {
        return title;
    }

    public final void setTitle(String title) {
        this.title = title;
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
