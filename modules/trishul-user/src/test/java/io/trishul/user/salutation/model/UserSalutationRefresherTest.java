package io.company.brewcraft.repository.user;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.user.UserSalutation;
import io.company.brewcraft.model.user.UserSalutationAccessor;
import io.company.brewcraft.repository.AccessorRefresher;
import io.company.brewcraft.repository.user.impl.UserSalutationRefresher;

public class UserSalutationRefresherTest {
    private AccessorRefresher<Long, UserSalutationAccessor, UserSalutation> mRefresher;

    private UserSalutationRefresher userSalutationRefresher;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void init() {
        mRefresher = mock(AccessorRefresher.class);

        userSalutationRefresher = new UserSalutationRefresher(mRefresher);
    }

    @Test
    public void testRefresh_DoesNothing() {
        userSalutationRefresher.refresh(null);
    }

    @Test
    public void testRefreshAccessors_CallsAccessorRefresher() {
        UserSalutationAccessor accessor = mock(UserSalutationAccessor.class);
        List<UserSalutationAccessor> accessors = List.of(accessor);

        userSalutationRefresher.refreshAccessors(accessors);

        verify(mRefresher, times(1)).refreshAccessors(List.of(accessor));
    }
}
