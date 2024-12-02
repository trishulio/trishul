package io.trishul.user.role.binding.model;

import java.util.List;

public interface UserRoleBindingAccessor {
    List<UserRoleBinding> getRoleBindings();

    void setRoleBindings(List<UserRoleBinding> roleBindings);

}
