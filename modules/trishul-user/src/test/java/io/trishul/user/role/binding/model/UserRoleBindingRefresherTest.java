package io.company.brewcraft.repository.user;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.user.UserRole;
import io.company.brewcraft.model.user.UserRoleAccessor;
import io.company.brewcraft.model.user.UserRoleBinding;
import io.company.brewcraft.model.user.UserRoleBindingAccessor;
import io.company.brewcraft.repository.Refresher;
import io.company.brewcraft.repository.user.impl.UserRoleBindingRefresher;

public class UserRoleBindingRefresherTest {
    private Refresher<UserRole, UserRoleAccessor> mUserRoleRefresher;

    private Refresher<UserRoleBinding, UserRoleBindingAccessor> userRoleBindingRefresher;

    @BeforeEach
    public void init() {
        mUserRoleRefresher = mock(Refresher.class);
        userRoleBindingRefresher = new UserRoleBindingRefresher(mUserRoleRefresher);
    }

    @Test
    public void testRefresh_RefreshersChildEntities() {
        List<UserRoleBinding> bindings = List.of(new UserRoleBinding(1L), new UserRoleBinding(2L));
        userRoleBindingRefresher.refresh(bindings);

        verify(mUserRoleRefresher, times(1)).refreshAccessors(List.of(new UserRoleBinding(1L), new UserRoleBinding(2L)));
    }

    @Test
    public void testRefreshAccessors_DoesNothing() {
        List<UserRoleBindingAccessor> accessors = List.of(mock(UserRoleBindingAccessor.class), mock(UserRoleBindingAccessor.class));

        userRoleBindingRefresher.refreshAccessors(accessors);

        verifyNoInteractions(accessors.get(0));
        verifyNoInteractions(accessors.get(1));
    }
}