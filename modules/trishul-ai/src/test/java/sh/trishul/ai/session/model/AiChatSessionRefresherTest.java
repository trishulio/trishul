package sh.trishul.ai.session.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.util.List;
import org.junit.jupiter.api.Test;
import sh.trishul.ai.agent.model.AiAgentConfig;
import sh.trishul.ai.agent.model.AiAgentConfigAccessor;
import sh.trishul.ai.memory.model.AiChatMemoryConfig;
import sh.trishul.ai.memory.model.AiChatMemoryConfigAccessor;
import sh.trishul.base.types.base.pojo.Refresher;
import sh.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;

class AiChatSessionRefresherTest {

  @Test
  @SuppressWarnings("unchecked")
  void testRefresh_DelegatesToAgentAndMemoryRefreshers() {
    AccessorRefresher<Long, AiChatSessionAccessor<?>, AiChatSession> mockAccessorRefresher
        = mock(AccessorRefresher.class);
    Refresher<AiAgentConfig, AiAgentConfigAccessor<?>> mockAgentRefresher = mock(Refresher.class);
    Refresher<AiChatMemoryConfig, AiChatMemoryConfigAccessor<?>> mockMemoryRefresher
        = mock(Refresher.class);

    AiChatSessionRefresher refresher = new AiChatSessionRefresher(mockAccessorRefresher,
        mockAgentRefresher, mockMemoryRefresher);

    List<AiChatSession> entities = List.of(new AiChatSession(1L));
    refresher.refresh(entities);

    verify(mockAgentRefresher).refreshAccessors(entities);
    verify(mockMemoryRefresher).refreshAccessors(entities);
    verifyNoInteractions(mockAccessorRefresher);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testRefreshAccessors_DelegatesToAccessorRefresher() {
    AccessorRefresher<Long, AiChatSessionAccessor<?>, AiChatSession> mockAccessorRefresher
        = mock(AccessorRefresher.class);
    Refresher<AiAgentConfig, AiAgentConfigAccessor<?>> mockAgentRefresher = mock(Refresher.class);
    Refresher<AiChatMemoryConfig, AiChatMemoryConfigAccessor<?>> mockMemoryRefresher
        = mock(Refresher.class);

    AiChatSessionRefresher refresher = new AiChatSessionRefresher(mockAccessorRefresher,
        mockAgentRefresher, mockMemoryRefresher);

    List<AiChatSessionAccessor<?>> accessors = List.of(mock(AiChatSessionAccessor.class));
    refresher.refreshAccessors(accessors);

    verify(mockAccessorRefresher).refreshAccessors(accessors);
    verifyNoInteractions(mockAgentRefresher);
    verifyNoInteractions(mockMemoryRefresher);
  }
}
