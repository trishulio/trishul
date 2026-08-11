package io.trishul.ai.service.agent.manager;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.langchain4j.memory.ChatMemory;
import io.trishul.ai.agent.model.AiAgentConfig;
import io.trishul.ai.memory.model.AiChatMemoryConfig;
import io.trishul.ai.service.agent.Assistant;
import io.trishul.ai.service.agent.factory.AgentFactory;
import io.trishul.ai.service.agent.provider.AiAgentConfigProvider;
import io.trishul.ai.service.memory.manager.AiChatMemoryManagerWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AiAgentManagerWrapperTest {

  private AiAgentManagerWrapper wrapper;
  private AiAgentConfigProvider mockConfigProvider;
  private AiChatMemoryManagerWrapper mockMemoryWrapper;
  private AgentFactory mockAgentFactory;

  @BeforeEach
  void setUp() {
    mockConfigProvider = mock(AiAgentConfigProvider.class);
    mockMemoryWrapper = mock(AiChatMemoryManagerWrapper.class);
    mockAgentFactory = mock(AgentFactory.class);
    wrapper = new AiAgentManagerWrapper(mockConfigProvider, mockMemoryWrapper, mockAgentFactory);
  }

  @Test
  void testGetAgent_ResolvesConfigAndBuildsAgent() {
    Long agentConfigId = 1L;
    Object memoryId = "session-1";

    AiChatMemoryConfig memCfg = new AiChatMemoryConfig(5L);
    AiAgentConfig config = new AiAgentConfig();
    config.setId(agentConfigId);
    config.setChatMemoryConfig(memCfg);

    ChatMemory mockMemory = mock(ChatMemory.class);
    Assistant mockAssistant = mock(Assistant.class);

    when(mockConfigProvider.getAgentConfig(agentConfigId)).thenReturn(config);
    when(mockMemoryWrapper.getChatMemory(eq(5L), eq(memoryId))).thenReturn(mockMemory);
    when(mockAgentFactory.buildAgent(config, mockMemory)).thenReturn(mockAssistant);

    Object result = wrapper.getAgent(agentConfigId, memoryId);

    assertNotNull(result);
    verify(mockConfigProvider).getAgentConfig(agentConfigId);
    verify(mockMemoryWrapper).getChatMemory(5L, memoryId);
    verify(mockAgentFactory).buildAgent(config, mockMemory);
  }

  @Test
  void testGetAgent_WithNoMemoryConfig_PassesNullMemoryConfigId() {
    Long agentConfigId = 2L;
    Object memoryId = "session-2";

    AiAgentConfig config = new AiAgentConfig();
    config.setId(agentConfigId);
    config.setChatMemoryConfig(null); // no memory config

    ChatMemory mockMemory = mock(ChatMemory.class);
    Assistant mockAssistant = mock(Assistant.class);

    when(mockConfigProvider.getAgentConfig(agentConfigId)).thenReturn(config);
    when(mockMemoryWrapper.getChatMemory(eq((Long) null), eq(memoryId))).thenReturn(mockMemory);
    when(mockAgentFactory.buildAgent(config, mockMemory)).thenReturn(mockAssistant);

    Object result = wrapper.getAgent(agentConfigId, memoryId);

    assertNotNull(result);
    verify(mockMemoryWrapper).getChatMemory(null, memoryId);
  }
}
