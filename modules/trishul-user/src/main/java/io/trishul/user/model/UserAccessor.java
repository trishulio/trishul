package io.trishul.user.model;

public interface UserAccessor<T extends UserAccessor<T>> {
  final String ATTR_USER = "user";

  User getUser();

  T setUser(User user);
}
