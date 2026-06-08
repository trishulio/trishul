package io.trishul.ai.agent.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import io.trishul.ai.chat.model.AiChatModelConfig;
import io.trishul.ai.chat.model.AiChatModelConfigAccessor;
import io.trishul.ai.memory.model.AiChatMemoryConfig;
import io.trishul.ai.memory.model.AiChatMemoryConfigAccessor;
import io.trishul.base.types.base.pojo.Refresher;
import io.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;
import java.util.List;
import org.junit.jupiter.api.Test;

class AiAgentConfigRefresherTest {

  @Test
  @SuppressWarnings("unchecked")
  void testRefresh_DelegatesToModelAndMemoryRefreshers() {
    AccessorRefresher<Long, AiAgentConfigAccessor<?>, AiAgentConfig> mockAccessorRefresher
        = mock(AccessorRefresher.class);
    Refresher<AiChatModelConfig, AiChatModelConfigAccessor<?>> mockModelRefresher
        = mock(Refresher.class);
    Refresher<AiChatMemoryConfig, AiChatMemoryConfigAccessor<?>> mockMemoryRefresher
        = mock(Refresher.class);

    AiAgentConfigRefresher refresher = new AiAgentConfigRefresher(mockAccessorRefresher,
        mockModelRefresher, mockMemoryRefresher);

    List<AiAgentConfig> entities = List.of(new AiAgentConfig(1L));
    refresher.refresh(entities);

    verify(mockModelRefresher).refreshAccessors(entities);
    verify(mockMemoryRefresher).refreshAccessors(entities);
    verifyNoInteractions(mockAccessorRefresher);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testRefreshAccessors_DelegatesToAccessorRefresher() {
    AccessorRefresher<Long, AiAgentConfigAccessor<?>, AiAgentConfig> mockAccessorRefresher
        = mock(AccessorRefresher.class);
    Refresher<AiChatModelConfig, AiChatModelConfigAccessor<?>> mockModelRefresher
        = mock(Refresher.class);
    Refresher<AiChatMemoryConfig, AiChatMemoryConfigAccessor<?>> mockMemoryRefresher
        = mock(Refresher.class);

    AiAgentConfigRefresher refresher = new AiAgentConfigRefresher(mockAccessorRefresher,
        mockModelRefresher, mockMemoryRefresher);

    List<AiAgentConfigAccessor<?>> accessors = List.of(mock(AiAgentConfigAccessor.class));
    refresher.refreshAccessors(accessors);

    verify(mockAccessorRefresher).refreshAccessors(accessors);
    verifyNoInteractions(mockModelRefresher);
    verifyNoInteractions(mockMemoryRefresher);
  }
}
