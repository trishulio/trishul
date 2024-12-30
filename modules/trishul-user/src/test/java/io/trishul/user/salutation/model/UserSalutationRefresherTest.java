package io.trishul.user.salutation.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import io.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserSalutationRefresherTest {
  private AccessorRefresher<Long, UserSalutationAccessor<?>, UserSalutation> mRefresher;

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
    UserSalutationAccessor<?> accessor = mock(UserSalutationAccessor.class);
    List<UserSalutationAccessor<?>> accessors = List.of(accessor);

    userSalutationRefresher.refreshAccessors(accessors);

    verify(mRefresher, times(1)).refreshAccessors(List.of(accessor));
  }
}
