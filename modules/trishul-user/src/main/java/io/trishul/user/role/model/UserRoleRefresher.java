package io.trishul.user.role.model;

import java.util.Collection;

public class UserRoleRefresher implements Refresher<UserRole, UserRoleAccessor> {
    private AccessorRefresher<Long, UserRoleAccessor, UserRole> refresher;

    public UserRoleRefresher(AccessorRefresher<Long, UserRoleAccessor, UserRole> refresher) {
        this.refresher = refresher;
    }

    @Override
    public void refresh(Collection<UserRole> roles) {
        // Role has no child entity, hence, skipping.
    }

    @Override
    public void refreshAccessors(Collection<? extends UserRoleAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }
}
