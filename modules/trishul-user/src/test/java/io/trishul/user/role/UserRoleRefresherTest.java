package io.trishul.user.role;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserRoleRefresherTest {
    private AccessorRefresher<Long, UserRoleAccessor, UserRole> refresher;

    private UserRoleRefresher userRoleRefresher;

    @BeforeEach
    public void init() {
        refresher = mock(AccessorRefresher.class);
        userRoleRefresher = new UserRoleRefresher(refresher);
    }

    @Test
    public void testRefresh_DoesNothing() {
        userRoleRefresher.refresh(null);
    }

    @Test
    public void testRefreshAccessors_CallsRefreshAccessor() {
        UserRoleAccessor accessor = mock(UserRoleAccessor.class);
        List<UserRoleAccessor> accessors = List.of(accessor);
        userRoleRefresher.refreshAccessors(accessors);

        verify(refresher, times(1)).refreshAccessors(List.of(accessor));
    }
}