package sh.trishul.base.types.base.pojo;

import java.time.LocalDateTime;

public interface Audited<T extends Audited<T>> {
  String ATTR_CREATED_AT = "createdAt";
  String ATTR_LAST_UPDATED = "lastUpdated";

  LocalDateTime getCreatedAt();

  T setCreatedAt(LocalDateTime createdAt);

  LocalDateTime getLastUpdated();

  T setLastUpdated(LocalDateTime lastUpdated);
}
