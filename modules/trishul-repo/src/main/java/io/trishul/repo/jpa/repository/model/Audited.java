package io.trishul.repo.jpa.repository.model;

import java.time.LocalDateTime;

public interface Audited {
    final String ATTR_CREATED_AT = "createdAt";
    final String ATTR_LAST_UPDATED = "lastUpdated";

    LocalDateTime getCreatedAt();

    void setCreatedAt(LocalDateTime createdAt);

    LocalDateTime getLastUpdated();

    void setLastUpdated(LocalDateTime lastUpdated);
}
