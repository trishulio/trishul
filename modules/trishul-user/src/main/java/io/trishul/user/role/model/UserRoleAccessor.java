package io.trishul.user.role.model;

public interface UserRoleAccessor {
    final String ATTR_ROLE_TYPE = "role";

    UserRole getRole();

    void setRole(UserRole role);
}
