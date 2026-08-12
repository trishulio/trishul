package sh.trishul.ai.guardrail.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.util.List;
import org.junit.jupiter.api.Test;
import sh.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;

class AiGuardrailRefresherTest {

  @Test
  @SuppressWarnings("unchecked")
  void testRefresh_DoesNothing() {
    AccessorRefresher<Long, AiGuardrailAccessor<?>, AiGuardrail> mockAccessorRefresher
        = mock(AccessorRefresher.class);
    AiGuardrailRefresher refresher = new AiGuardrailRefresher(mockAccessorRefresher);

    refresher.refresh(List.of(new AiGuardrail(1L)));
    verifyNoInteractions(mockAccessorRefresher);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testRefreshAccessors_DelegatesToAccessorRefresher() {
    AccessorRefresher<Long, AiGuardrailAccessor<?>, AiGuardrail> mockAccessorRefresher
        = mock(AccessorRefresher.class);
    AiGuardrailRefresher refresher = new AiGuardrailRefresher(mockAccessorRefresher);

    List<AiGuardrailAccessor<?>> accessors = List.of(mock(AiGuardrailAccessor.class));
    refresher.refreshAccessors(accessors);

    verify(mockAccessorRefresher).refreshAccessors(accessors);
  }
}
