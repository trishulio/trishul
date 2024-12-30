package io.trishul.user.role.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import io.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserRoleRefresherTest {
  private AccessorRefresher<Long, UserRoleAccessor<?>, UserRole> refresher;

  private UserRoleRefresher userRoleRefresher;

  @BeforeEach
  public void init() {
    class UserRoleAccessorRefresher extends AccessorRefresher<Long, UserRoleAccessor<?>, UserRole> {
      public UserRoleAccessorRefresher(Class<UserRole> clazz,
          Function<UserRoleAccessor<?>, UserRole> getter,
          BiConsumer<UserRoleAccessor<?>, UserRole> setter,
          Function<Iterable<Long>, List<UserRole>> entityRetriever) {
        super(clazz, getter, setter, entityRetriever);
      }
    }
    refresher = mock(UserRoleAccessorRefresher.class);
    userRoleRefresher = new UserRoleRefresher(refresher);
  }

  @Test
  public void testRefresh_DoesNothing() {
    userRoleRefresher.refresh(null);
  }

  @Test
  public void testRefreshAccessors_CallsRefreshAccessor() {
    UserRoleAccessor<?> accessor = mock(UserRoleAccessor.class);
    List<UserRoleAccessor<?>> accessors = List.of(accessor);
    userRoleRefresher.refreshAccessors(accessors);

    verify(refresher, times(1)).refreshAccessors(List.of(accessor));
  }
}
