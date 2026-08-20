package sh.trishul.user.role.model;

public interface UserRoleAccessor<T extends UserRoleAccessor<T>> {
  String ATTR_ROLE_TYPE = "role";

  UserRole getRole();

  T setRole(UserRole role);
}
