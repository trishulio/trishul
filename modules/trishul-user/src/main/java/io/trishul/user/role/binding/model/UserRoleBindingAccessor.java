package io.trishul.user.role.binding.model;

import java.util.List;

public interface UserRoleBindingAccessor<T extends UserRoleBindingAccessor<T>> {
  List<UserRoleBinding> getRoleBindings();

  T setRoleBindings(List<UserRoleBinding> roleBindings);
}
