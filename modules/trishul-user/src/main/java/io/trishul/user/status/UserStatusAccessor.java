package io.trishul.user.status;

public interface UserStatusAccessor<T extends UserStatusAccessor<T>> {
  final String ATTR_STATUS = "status";

  UserStatus getStatus();

  T setStatus(UserStatus status);
}
