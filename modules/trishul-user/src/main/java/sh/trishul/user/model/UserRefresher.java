package sh.trishul.user.model;

import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import sh.trishul.base.types.base.pojo.OwnedByAccessor;
import sh.trishul.base.types.base.pojo.Refresher;
import sh.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;
import sh.trishul.user.role.binding.model.UserRoleBinding;
import sh.trishul.user.role.binding.model.UserRoleBindingAccessor;
import sh.trishul.user.salutation.model.UserSalutation;
import sh.trishul.user.salutation.model.UserSalutationAccessor;
import sh.trishul.user.status.UserStatus;
import sh.trishul.user.status.UserStatusAccessor;

public class UserRefresher implements Refresher<User, UserAccessor<?>> {
  private final AccessorRefresher<Long, UserAccessor<?>, User> refresher;
  private final AccessorRefresher<Long, AssignedToAccessor<?>, User> assignedToAccessorRefresher;
  private final AccessorRefresher<Long, OwnedByAccessor<User>, User> ownedByAccessorRefresher;
  private final Refresher<UserStatus, UserStatusAccessor<?>> statusRefresher;
  private final Refresher<UserSalutation, UserSalutationAccessor<?>> salutationRefresher;
  private final Refresher<UserRoleBinding, UserRoleBindingAccessor<?>> roleBindingRefresher;

  @Autowired
  public UserRefresher(AccessorRefresher<Long, UserAccessor<?>, User> refresher,
      AccessorRefresher<Long, AssignedToAccessor<?>, User> assignedToAccessorRefresher,
      AccessorRefresher<Long, OwnedByAccessor<User>, User> ownedByAccessorRefresher,
      Refresher<UserStatus, UserStatusAccessor<?>> statusRefresher,
      Refresher<UserSalutation, UserSalutationAccessor<?>> salutationRefresher,
      Refresher<UserRoleBinding, UserRoleBindingAccessor<?>> roleBindingRefresher) {
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

    List<UserRoleBinding> bindings = users.stream()
        .filter(u -> u != null && u.getRoleBindings() != null && !u.getRoleBindings().isEmpty())
        .flatMap(u -> u.getRoleBindings().stream()).toList();
    this.roleBindingRefresher.refresh(bindings);
  }

  @Override
  public void refreshAccessors(Collection<? extends UserAccessor<?>> accessors) {
    refresher.refreshAccessors(accessors);
  }

  public void refreshAssignedToAccessors(Collection<? extends AssignedToAccessor<?>> accessors) {
    assignedToAccessorRefresher.refreshAccessors(accessors);
  }

  public void refreshOwnedByAccessors(Collection<? extends OwnedByAccessor<User>> accessors) {
    ownedByAccessorRefresher.refreshAccessors(accessors);
  }
}
