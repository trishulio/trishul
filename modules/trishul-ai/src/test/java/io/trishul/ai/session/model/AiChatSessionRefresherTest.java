package io.trishul.ai.session.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import io.trishul.ai.agent.model.AiAgentConfig;
import io.trishul.ai.agent.model.AiAgentConfigAccessor;
import io.trishul.ai.memory.model.AiChatMemoryConfig;
import io.trishul.ai.memory.model.AiChatMemoryConfigAccessor;
import io.trishul.base.types.base.pojo.Refresher;
import io.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;
import java.util.List;
import org.junit.jupiter.api.Test;

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
