package io.trishul.user.status;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;

public class UserStatusRefresherTest {
    private AccessorRefresher<Long, UserStatusAccessor, UserStatus> mRefresher;

    private UserStatusRefresher userStatusRefresher;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void init() {
        mRefresher = mock(AccessorRefresher.class);

        userStatusRefresher = new UserStatusRefresher(mRefresher);
    }

    @Test
    public void testRefresh_DoesNothing() {
        userStatusRefresher.refresh(null);
    }

    @Test
    public void testRefreshAccessors_CallsAccessorRefresher() {
        UserStatusAccessor accessor = mock(UserStatusAccessor.class);
        List<UserStatusAccessor> accessors = List.of(accessor);

        userStatusRefresher.refreshAccessors(accessors);

        verify(mRefresher, times(1)).refreshAccessors(List.of(accessor));
    }
}
