package io.trishul.user.model;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.trishul.base.types.base.pojo.OwnedByAccessor;
import io.trishul.base.types.base.pojo.Refresher;
import io.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;
import io.trishul.user.role.binding.model.UserRoleBinding;
import io.trishul.user.role.binding.model.UserRoleBindingAccessor;
import io.trishul.user.salutation.model.UserSalutation;
import io.trishul.user.salutation.model.UserSalutationAccessor;
import io.trishul.user.status.UserStatus;
import io.trishul.user.status.UserStatusAccessor;

public class UserRefresher implements Refresher<User, UserAccessor> {
    private static final Logger log = LoggerFactory.getLogger(UserRefresher.class);

    private final AccessorRefresher<Long, UserAccessor, User> refresher;
    private final AccessorRefresher<Long, AssignedToAccessor, User> assignedToAccessorRefresher;
    private final AccessorRefresher<Long, OwnedByAccessor<User>, User> ownedByAccessorRefresher;
    private final Refresher<UserStatus, UserStatusAccessor> statusRefresher;
    private final Refresher<UserSalutation, UserSalutationAccessor> salutationRefresher;
    private final Refresher<UserRoleBinding, UserRoleBindingAccessor> roleBindingRefresher;

    @Autowired
    public UserRefresher(AccessorRefresher<Long, UserAccessor, User> refresher, AccessorRefresher<Long, AssignedToAccessor, User> assignedToAccessorRefresher, AccessorRefresher<Long, OwnedByAccessor<User>, User> ownedByAccessorRefresher, Refresher<UserStatus, UserStatusAccessor> statusRefresher, Refresher<UserSalutation, UserSalutationAccessor> salutationRefresher, Refresher<UserRoleBinding, UserRoleBindingAccessor> roleBindingRefresher) {
        this.refresher = refresher;
        this.assignedToAccessorRefresher = assignedToAccessorRefresher;
        this.ownedByAccessorRefresher = ownedByAccessorRefresher;
        this.statusRefresher = statusRefresher;
        this.salutationRefresher = salutationRefresher;
        this.roleBindingRefresher = roleBindingRefresher;
    }

    @Override
    public void refresh(Collection<User> users) {
        this.statusRefresher.refreshAccessors(users);
        this.salutationRefresher.refreshAccessors(users);

        List<UserRoleBinding> bindings = users.stream().filter(u -> u != null && u.getRoleBindings() != null && u.getRoleBindings().size() > 0).flatMap(u -> u.getRoleBindings().stream()).toList();
        this.roleBindingRefresher.refresh(bindings);
    }

    @Override
    public void refreshAccessors(Collection<? extends UserAccessor> accessors) {
        refresher.refreshAccessors(accessors);
    }

    public void refreshAssignedToAccessors(Collection<? extends AssignedToAccessor> accessors) {
        assignedToAccessorRefresher.refreshAccessors(accessors);
    }

    public void refreshOwnedByAccessors(Collection<? extends OwnedByAccessor<User>> accessors) {
        ownedByAccessorRefresher.refreshAccessors(accessors);
    }
}