package io.trishul.user.status;

import java.util.Collection;

import io.trishul.base.types.base.pojo.Refresher;
import io.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;

public class UserStatusRefresher implements Refresher<UserStatus, UserStatusAccessor> {
    private final AccessorRefresher<Long, UserStatusAccessor, UserStatus> refresher;

    public UserStatusRefresher(AccessorRefresher<Long, UserStatusAccessor, UserStatus> refresher) {
        this.refresher = refresher;
    }

    @Override
    public void refresh(Collection<UserStatus> statuses) {
        // No child entities, hence skipping.
    }

    @Override
    public void refreshAccessors(Collection<? extends UserStatusAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }
}
