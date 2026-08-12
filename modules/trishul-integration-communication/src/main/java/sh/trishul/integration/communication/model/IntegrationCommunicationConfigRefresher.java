package sh.trishul.integration.communication.model;

import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sh.trishul.base.types.base.pojo.Refresher;
import sh.trishul.integration.model.Integration;
import sh.trishul.integration.model.IntegrationAccessor;
import sh.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;

public class IntegrationCommunicationConfigRefresher implements
    Refresher<IntegrationCommunicationConfig, IntegrationCommunicationConfigAccessor<?>> {
  @SuppressWarnings("unused")
  private static final Logger log
      = LoggerFactory.getLogger(IntegrationCommunicationConfigRefresher.class);

  private final AccessorRefresher<Long, IntegrationCommunicationConfigAccessor<?>, IntegrationCommunicationConfig> refresher;
  private final Refresher<Integration, IntegrationAccessor<?>> integrationRefresher;

  public IntegrationCommunicationConfigRefresher(
      AccessorRefresher<Long, IntegrationCommunicationConfigAccessor<?>, IntegrationCommunicationConfig> refresher,
      Refresher<Integration, IntegrationAccessor<?>> integrationRefresher) {
    this.refresher = refresher;
    this.integrationRefresher = integrationRefresher;
  }

  @Override
  public void refresh(Collection<IntegrationCommunicationConfig> configs) {
    this.integrationRefresher.refreshAccessors(configs);
  }

  @Override
  public void refreshAccessors(
      Collection<? extends IntegrationCommunicationConfigAccessor<?>> accessors) {
    this.refresher.refreshAccessors(accessors);
  }
}
