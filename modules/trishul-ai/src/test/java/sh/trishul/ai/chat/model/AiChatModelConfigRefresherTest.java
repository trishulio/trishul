package sh.trishul.ai.chat.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.util.List;
import org.junit.jupiter.api.Test;
import sh.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;

class AiChatModelConfigRefresherTest {

  @Test
  @SuppressWarnings("unchecked")
  void testRefresh_DoesNothing() {
    AccessorRefresher<Long, AiChatModelConfigAccessor<?>, AiChatModelConfig> mockAccessorRefresher
        = mock(AccessorRefresher.class);
    AiChatModelConfigRefresher refresher = new AiChatModelConfigRefresher(mockAccessorRefresher);

    refresher.refresh(List.of(new AiChatModelConfig(1L)));
    verifyNoInteractions(mockAccessorRefresher);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testRefreshAccessors_DelegatesToAccessorRefresher() {
    AccessorRefresher<Long, AiChatModelConfigAccessor<?>, AiChatModelConfig> mockAccessorRefresher
        = mock(AccessorRefresher.class);
    AiChatModelConfigRefresher refresher = new AiChatModelConfigRefresher(mockAccessorRefresher);

    List<AiChatModelConfigAccessor<?>> accessors = List.of(mock(AiChatModelConfigAccessor.class));
    refresher.refreshAccessors(accessors);

    verify(mockAccessorRefresher).refreshAccessors(accessors);
  }
}
