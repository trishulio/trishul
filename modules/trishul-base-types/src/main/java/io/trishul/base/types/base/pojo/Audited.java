package io.trishul.base.types.base.pojo;

import java.time.LocalDateTime;

public interface Audited<T extends Audited<T>> {
  final String ATTR_CREATED_AT = "createdAt";
  final String ATTR_LAST_UPDATED = "lastUpdated";

  LocalDateTime getCreatedAt();

  T setCreatedAt(LocalDateTime createdAt);

  LocalDateTime getLastUpdated();

  T setLastUpdated(LocalDateTime lastUpdated);
}
