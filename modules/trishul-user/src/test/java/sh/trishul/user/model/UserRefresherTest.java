package sh.trishul.user.model;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.base.types.base.pojo.OwnedByAccessor;
import sh.trishul.base.types.base.pojo.Refresher;
import sh.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;
import sh.trishul.user.role.binding.model.UserRoleBinding;
import sh.trishul.user.role.binding.model.UserRoleBindingAccessor;
import sh.trishul.user.role.model.UserRole;
import sh.trishul.user.salutation.model.UserSalutation;
import sh.trishul.user.salutation.model.UserSalutationAccessor;
import sh.trishul.user.status.UserStatus;
import sh.trishul.user.status.UserStatusAccessor;

class UserRefresherTest {
  private AccessorRefresher<Long, UserAccessor<?>, User> mRefresher;
  private AccessorRefresher<Long, AssignedToAccessor<?>, User> mAssignedToRefresher;
  private AccessorRefresher<Long, OwnedByAccessor<User>, User> mOwnedByRefresher;
  private Refresher<UserStatus, UserStatusAccessor<?>> mStatusRefresher;
  private Refresher<UserSalutation, UserSalutationAccessor<?>> mSalutationRefresher;
  private Refresher<UserRoleBinding, UserRoleBindingAccessor<?>> mRoleBindingRefresher;

  private UserRefresher userRefresher;
  private User userReplacement;
  private User assignedToReplacement;
  private User ownedByReplacement;

  @BeforeEach
  @SuppressWarnings("unchecked")
  void init() {
    userReplacement = new User(1L);
    assignedToReplacement = new User(2L);
    ownedByReplacement = new User(3L);

    Function<Iterable<Long>, List<User>> userRetriever = ids -> List.of(userReplacement);
    Function<Iterable<Long>, List<User>> assignedToRetriever
        = ids -> List.of(assignedToReplacement);
    Function<Iterable<Long>, List<User>> ownedByRetriever = ids -> List.of(ownedByReplacement);

    mRefresher = new AccessorRefresher<>(User.class, UserAccessor::getUser,
        (accessor, user) -> accessor.setUser(user), userRetriever);
    mAssignedToRefresher = new AccessorRefresher<>(User.class, AssignedToAccessor::getAssignedTo,
        (accessor, user) -> accessor.setAssignedTo(user), assignedToRetriever);
    mOwnedByRefresher = new AccessorRefresher<>(User.class, OwnedByAccessor::getOwnedBy,
        (accessor, user) -> accessor.setOwnedBy(user), ownedByRetriever);
    mStatusRefresher = mock(Refresher.class);
    mSalutationRefresher = mock(Refresher.class);
    mRoleBindingRefresher = mock(Refresher.class);
    userRefresher = new UserRefresher(mRefresher, mAssignedToRefresher, mOwnedByRefresher,
        mStatusRefresher, mSalutationRefresher, mRoleBindingRefresher);
  }

  @Test
  void testRefresh_RefreshedChildEntitiesAndBindings() {
    List<User> users = List.of(new User(1L), new User(2L));

    users.get(0).setRoles(List.of(new UserRole(10L)));
    users.get(1).setRoles(List.of(new UserRole(20L)));

    userRefresher.refresh(users);

    List<UserRoleBinding> expected
        = List.of(new UserRoleBinding().setRole(new UserRole(10L)).setUser(users.get(0)),
            new UserRoleBinding().setRole(new UserRole(20L)).setUser(users.get(1)));
    verify(mRoleBindingRefresher, times(1)).refresh(expected);

    verify(mStatusRefresher, times(1)).refreshAccessors(users);
    verify(mSalutationRefresher, times(1)).refreshAccessors(users);
  }

  @Test
  void testRefresh_SkipsNullAndEmptyRoleBindings() {
    User withRoles = new User(1L);
    withRoles.setRoles(List.of(new UserRole(10L)));

    User withoutRoles = new User(2L);
    withoutRoles.setRoles(List.of());

    List<User> users = new ArrayList<>();
    users.add(null);
    users.add(withoutRoles);
    users.add(withRoles);

    userRefresher.refresh(users);

    List<UserRoleBinding> expected
        = List.of(new UserRoleBinding().setRole(new UserRole(10L)).setUser(withRoles));

    verify(mRoleBindingRefresher, times(1)).refresh(expected);
  }

  @Test
  void testRefreshAccessors_CallsAccessorRefresher() {
    UserAccessorImpl accessor = new UserAccessorImpl(new User(1L));
    userRefresher.refreshAccessors(List.of(accessor));

    assertSame(userReplacement, accessor.getUser());
  }

  @Test
  void testAssignedToRefreshAccessors_CallsAccessorRefresher() {
    UserLinkAccessorImpl accessor = new UserLinkAccessorImpl(new User(2L), new User(3L));
    userRefresher.refreshAssignedToAccessors(List.of(accessor));

    assertSame(assignedToReplacement, accessor.getAssignedTo());
  }

  @Test
  void testOwnedByRefreshAccessors_CallsAccessorRefresher() {
    UserLinkAccessorImpl accessor = new UserLinkAccessorImpl(new User(2L), new User(3L));
    userRefresher.refreshOwnedByAccessors(List.of(accessor));

    assertSame(ownedByReplacement, accessor.getOwnedBy());
  }

  private static final class UserAccessorImpl implements UserAccessor<UserAccessorImpl> {
    private User user;

    private UserAccessorImpl(User user) {
      this.user = user;
    }

    @Override
    public User getUser() {
      return user;
    }

    @Override
    public UserAccessorImpl setUser(User user) {
      this.user = user;
      return this;
    }
  }

  private static final class UserLinkAccessorImpl
      implements AssignedToAccessor<UserLinkAccessorImpl>, OwnedByAccessor<User> {
    private User assignedTo;
    private User ownedBy;

    private UserLinkAccessorImpl(User assignedTo, User ownedBy) {
      this.assignedTo = assignedTo;
      this.ownedBy = ownedBy;
    }

    @Override
    public User getAssignedTo() {
      return assignedTo;
    }

    @Override
    public UserLinkAccessorImpl setAssignedTo(User user) {
      this.assignedTo = user;
      return this;
    }

    @Override
    public User getOwnedBy() {
      return ownedBy;
    }

    @Override
    public void setOwnedBy(User user) {
      this.ownedBy = user;
    }
  }
}
