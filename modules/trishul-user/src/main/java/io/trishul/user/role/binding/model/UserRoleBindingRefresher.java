package io.trishul.user.role.binding.model;

import java.util.Collection;

import io.trishul.model.base.pojo.refresher.Refresher;
import io.trishul.user.role.model.UserRole;
import io.trishul.user.role.model.UserRoleAccessor;

public class UserRoleBindingRefresher implements Refresher<UserRoleBinding, UserRoleBindingAccessor> {
    private final Refresher<UserRole, UserRoleAccessor> userRoleRefresher;

    public UserRoleBindingRefresher(Refresher<UserRole, UserRoleAccessor> userRoleRefresher) {
        this.userRoleRefresher = userRoleRefresher;
    }

    @Override
    public void refresh(Collection<UserRoleBinding> bindings) {
        userRoleRefresher.refreshAccessors(bindings);
    }

    @Override
    public void refreshAccessors(Collection<? extends UserRoleBindingAccessor> accessors) {
        // NOTE: Not needed at this time
    }
}
