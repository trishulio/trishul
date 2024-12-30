package io.trishul.user.role.model;

public interface UserRoleAccessor<T extends UserRoleAccessor<T>> {
  final String ATTR_ROLE_TYPE = "role";

  UserRole getRole();

  T setRole(UserRole role);
}
