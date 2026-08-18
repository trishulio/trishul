package sh.trishul.ai.memory.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.util.List;
import org.junit.jupiter.api.Test;
import sh.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;

class AiChatMemoryConfigRefresherTest {

  @Test
  @SuppressWarnings("unchecked")
  void testRefresh_DoesNothing() {
    AccessorRefresher<Long, AiChatMemoryConfigAccessor<?>, AiChatMemoryConfig> mockAccessorRefresher
        = mock(AccessorRefresher.class);
    AiChatMemoryConfigRefresher refresher = new AiChatMemoryConfigRefresher(mockAccessorRefresher);

    refresher.refresh(List.of(new AiChatMemoryConfig(1L)));
    verifyNoInteractions(mockAccessorRefresher);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testRefreshAccessors_DelegatesToAccessorRefresher() {
    AccessorRefresher<Long, AiChatMemoryConfigAccessor<?>, AiChatMemoryConfig> mockAccessorRefresher
        = mock(AccessorRefresher.class);
    AiChatMemoryConfigRefresher refresher = new AiChatMemoryConfigRefresher(mockAccessorRefresher);

    List<AiChatMemoryConfigAccessor<?>> accessors = List.of(mock(AiChatMemoryConfigAccessor.class));
    refresher.refreshAccessors(accessors);

    verify(mockAccessorRefresher).refreshAccessors(accessors);
  }
}
