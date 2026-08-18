package io.trishul.integration.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import io.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;
import java.util.List;
import org.junit.jupiter.api.Test;

class IntegrationRefresherTest {

  @Test
  void testRefreshAccessors_DelegatesToAccessorRefresher() {
    @SuppressWarnings("unchecked")
    AccessorRefresher<Long, IntegrationAccessor<?>, Integration> refresher
        = mock(AccessorRefresher.class);
    IntegrationRefresher integrationRefresher = new IntegrationRefresher(refresher);
    IntegrationAccessor<?> accessor = mock(IntegrationAccessor.class);

    integrationRefresher.refreshAccessors(List.of(accessor));

    verify(refresher, times(1)).refreshAccessors(List.of(accessor));
  }

  @Test
  void testRefresh_DoesNothing() {
    @SuppressWarnings("unchecked")
    AccessorRefresher<Long, IntegrationAccessor<?>, Integration> refresher
        = mock(AccessorRefresher.class);
    IntegrationRefresher integrationRefresher = new IntegrationRefresher(refresher);
    integrationRefresher.refresh(List.of(new Integration()));
    // No exception expected
  }
}
