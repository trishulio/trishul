package io.trishul.base.types.base.pojo;

public interface VersionAccessor<T extends VersionAccessor<T>> extends Versioned {
  T setVersion(Integer version);
}
