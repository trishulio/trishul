package sh.trishul.ai.tool.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.util.List;
import org.junit.jupiter.api.Test;
import sh.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;

class AiToolRefresherTest {

  @Test
  @SuppressWarnings("unchecked")
  void testRefresh_DoesNothing() {
    AccessorRefresher<Long, AiToolAccessor<?>, AiTool> mockAccessorRefresher
        = mock(AccessorRefresher.class);
    AiToolRefresher refresher = new AiToolRefresher(mockAccessorRefresher);

    refresher.refresh(List.of(new AiTool(1L)));
    verifyNoInteractions(mockAccessorRefresher);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testRefreshAccessors_DelegatesToAccessorRefresher() {
    AccessorRefresher<Long, AiToolAccessor<?>, AiTool> mockAccessorRefresher
        = mock(AccessorRefresher.class);
    AiToolRefresher refresher = new AiToolRefresher(mockAccessorRefresher);

    List<AiToolAccessor<?>> accessors = List.of(mock(AiToolAccessor.class));
    refresher.refreshAccessors(accessors);

    verify(mockAccessorRefresher).refreshAccessors(accessors);
  }
}
