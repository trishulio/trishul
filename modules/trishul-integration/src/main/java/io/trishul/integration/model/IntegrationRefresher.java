package io.trishul.integration.model;

import io.trishul.base.types.base.pojo.Refresher;
import io.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntegrationRefresher implements Refresher<Integration, IntegrationAccessor<?>> {
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(IntegrationRefresher.class);

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
