package sh.trishul.ai.agent.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.util.List;
import org.junit.jupiter.api.Test;
import sh.trishul.ai.chat.model.AiChatModelConfig;
import sh.trishul.ai.chat.model.AiChatModelConfigAccessor;
import sh.trishul.ai.memory.model.AiChatMemoryConfig;
import sh.trishul.ai.memory.model.AiChatMemoryConfigAccessor;
import sh.trishul.base.types.base.pojo.Refresher;
import sh.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;

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
