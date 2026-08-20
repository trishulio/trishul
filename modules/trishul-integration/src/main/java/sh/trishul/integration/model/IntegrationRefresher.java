package sh.trishul.integration.model;

import java.util.Collection;
import sh.trishul.base.types.base.pojo.Refresher;
import sh.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;

public class IntegrationRefresher implements Refresher<Integration, IntegrationAccessor<?>> {
  private final AccessorRefresher<Long, IntegrationAccessor<?>, Integration> refresher;

  public IntegrationRefresher(
      AccessorRefresher<Long, IntegrationAccessor<?>, Integration> refresher) {
    this.refresher = refresher;
  }

  @Override
  public void refresh(Collection<Integration> integrations) {
    // Nothing to refresh
  }

  @Override
  public void refreshAccessors(Collection<? extends IntegrationAccessor<?>> accessors) {
    this.refresher.refreshAccessors(accessors);
  }
}
