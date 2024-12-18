package io.trishul.user.role.binding.model;

import io.trishul.base.types.base.pojo.Refresher;
import io.trishul.user.role.model.UserRole;
import io.trishul.user.role.model.UserRoleAccessor;
import java.util.Collection;

public class UserRoleBindingRefresher
        implements Refresher<UserRoleBinding, UserRoleBindingAccessor> {
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
