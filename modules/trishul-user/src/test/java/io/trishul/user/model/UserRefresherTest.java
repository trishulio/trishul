package io.trishul.user.model;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.base.types.base.pojo.OwnedByAccessor;
import io.trishul.user.model.User;
import io.trishul.user.model.UserAccessor;
import io.trishul.user.role.model.UserRole;
import io.trishul.user.salutation.model.UserSalutation;
import io.trishul.user.salutation.model.UserSalutationAccessor;
import io.trishul.user.status.UserStatus;
import io.trishul.user.status.UserStatusAccessor;
import io.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;
import io.trishul.base.types.base.pojo.Refresher;
import io.trishul.user.role.binding.model.UserRoleBinding;
import io.trishul.user.role.binding.model.UserRoleBindingAccessor;

public class UserRefresherTest {
    private AccessorRefresher<Long, UserAccessor, User> mRefresher;
    private AccessorRefresher<Long, AssignedToAccessor, User> mAssignedToRefresher;
    private AccessorRefresher<Long, OwnedByAccessor, User> mOwnedByRefresher;
    private Refresher<UserStatus, UserStatusAccessor> mStatusRefresher;
    private Refresher<UserSalutation, UserSalutationAccessor> mSalutationRefresher;
    private Refresher<UserRoleBinding, UserRoleBindingAccessor> mRoleBindingRefresher;

    private UserRefresher userRefresher;

    @BeforeEach
    public void init() {
        mRefresher = mock(AccessorRefresher.class);
        mAssignedToRefresher = mock(AccessorRefresher.class);
        mOwnedByRefresher = mock(AccessorRefresher.class);
        mStatusRefresher = mock(Refresher.class);
        mSalutationRefresher = mock(Refresher.class);
        mRoleBindingRefresher = mock(Refresher.class);
        userRefresher = new UserRefresher(mRefresher, mAssignedToRefresher, mOwnedByRefresher, mStatusRefresher, mSalutationRefresher, mRoleBindingRefresher);
    }

    @Test
    public void testRefresh_RefreshedChildEntitiesAndBindings() {
        List<User> users = List.of(
            new User(1L),
            new User(2L)
        );

        users.get(0).setRoles(List.of(new UserRole(10L)));
        users.get(1).setRoles(List.of(new UserRole(20L)));

        userRefresher.refresh(users);

        List<UserRoleBinding> expected = List.of(
            new UserRoleBinding(null, new UserRole(10L), users.get(0)),
            new UserRoleBinding(null, new UserRole(20L), users.get(1))
        );
        verify(mRoleBindingRefresher, times(1)).refresh(expected);

        verify(mStatusRefresher, times(1)).refreshAccessors(users);
        verify(mSalutationRefresher, times(1)).refreshAccessors(users);
    }

    @Test
    public void testRefreshAccessors_CallsAccessorRefresher() {
        UserAccessor accessor = mock(UserAccessor.class);
        userRefresher.refreshAccessors(List.of(accessor));

        verify(mRefresher, times(1)).refreshAccessors(List.of(accessor));
    }

    @Test
    public void testAssignedToRefreshAccessors_CallsAccessorRefresher() {
        AssignedToAccessor accessor = mock(AssignedToAccessor.class);
        userRefresher.refreshAssignedToAccessors(List.of(accessor));

        verify(mAssignedToRefresher, times(1)).refreshAccessors(List.of(accessor));
    }

    @Test
    public void testOwnedByRefreshAccessors_CallsAccessorRefresher() {
        OwnedByAccessor accessor = mock(OwnedByAccessor.class);
        userRefresher.refreshOwnedByAccessors(List.of(accessor));

        verify(mOwnedByRefresher, times(1)).refreshAccessors(List.of(accessor));
    }
}