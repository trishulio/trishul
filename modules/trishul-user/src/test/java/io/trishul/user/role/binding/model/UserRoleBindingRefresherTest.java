package io.trishul.user.role.binding.model;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import io.trishul.base.types.base.pojo.Refresher;
import io.trishul.user.role.model.UserRole;
import io.trishul.user.role.model.UserRoleAccessor;

public class UserRoleBindingRefresherTest {
  private Refresher<UserRole, UserRoleAccessor<?>> mUserRoleRefresher;

  private Refresher<UserRoleBinding, UserRoleBindingAccessor<?>> userRoleBindingRefresher;

  @BeforeEach
  public void init() {
    mUserRoleRefresher = mock(Refresher.class);
    userRoleBindingRefresher = new UserRoleBindingRefresher(mUserRoleRefresher);
  }

  @Test
  public void testRefresh_RefreshersChildEntities() {
    List<UserRoleBinding> bindings = List.of(new UserRoleBinding(1L), new UserRoleBinding(2L));
    userRoleBindingRefresher.refresh(bindings);

    verify(mUserRoleRefresher, times(1))
        .refreshAccessors(List.of(new UserRoleBinding(1L), new UserRoleBinding(2L)));
  }

  @Test
  public void testRefreshAccessors_DoesNothing() {
    List<UserRoleBindingAccessor<?>> accessors
        = List.of(mock(UserRoleBindingAccessor.class), mock(UserRoleBindingAccessor.class));

    userRoleBindingRefresher.refreshAccessors(accessors);

    verifyNoInteractions(accessors.get(0));
    verifyNoInteractions(accessors.get(1));
  }
}
