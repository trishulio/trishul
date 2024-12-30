package io.trishul.user.salutation.model;

import java.util.Collection;
import io.trishul.base.types.base.pojo.Refresher;
import io.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;

public class UserSalutationRefresher
    implements Refresher<UserSalutation, UserSalutationAccessor<?>> {
  private final AccessorRefresher<Long, UserSalutationAccessor<?>, UserSalutation> refresher;

  public UserSalutationRefresher(
      AccessorRefresher<Long, UserSalutationAccessor<?>, UserSalutation> refresher) {
    this.refresher = refresher;
  }

  @Override
  public void refresh(Collection<UserSalutation> salutations) {
    // No child entity to refresh, hence skipping
  }

  @Override
  public void refreshAccessors(Collection<? extends UserSalutationAccessor<?>> accessors) {
    refresher.refreshAccessors(accessors);
  }
}
