package io.trishul.ai.speech.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import io.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;
import java.util.List;
import org.junit.jupiter.api.Test;

class AiSpeechConfigRefresherTest {

  @Test
  @SuppressWarnings("unchecked")
  void testRefresh_DoesNothing() {
    AccessorRefresher<Long, AiSpeechConfigAccessor<?>, AiSpeechConfig> mockAccessorRefresher
        = mock(AccessorRefresher.class);
    AiSpeechConfigRefresher refresher = new AiSpeechConfigRefresher(mockAccessorRefresher);

    refresher.refresh(List.of(new AiSpeechConfig(1L)));
    verifyNoInteractions(mockAccessorRefresher);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testRefreshAccessors_DelegatesToAccessorRefresher() {
    AccessorRefresher<Long, AiSpeechConfigAccessor<?>, AiSpeechConfig> mockAccessorRefresher
        = mock(AccessorRefresher.class);
    AiSpeechConfigRefresher refresher = new AiSpeechConfigRefresher(mockAccessorRefresher);

    List<AiSpeechConfigAccessor<?>> accessors = List.of(mock(AiSpeechConfigAccessor.class));
    refresher.refreshAccessors(accessors);

    verify(mockAccessorRefresher).refreshAccessors(accessors);
  }
}
