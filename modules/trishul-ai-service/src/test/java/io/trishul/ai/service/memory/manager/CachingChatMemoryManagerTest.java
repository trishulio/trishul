package io.trishul.ai.service.memory.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.langchain4j.memory.ChatMemory;
import io.trishul.ai.memory.model.AiChatMemoryConfig;
import io.trishul.ai.service.agent.factory.AgentFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CachingChatMemoryManagerTest {

  private CachingChatMemoryManager manager;
  private AgentFactory mockAgentFactory;

  @BeforeEach
  void setUp() {
    mockAgentFactory = mock(AgentFactory.class);
    manager = new CachingChatMemoryManager(mockAgentFactory);
  }

  @Test
  void testGetChatMemory_DelegatesToAgentFactory() {
    AiChatMemoryConfig config = new AiChatMemoryConfig(1L);
    Object memoryId = "session-1";
    ChatMemory mockMemory = mock(ChatMemory.class);
    when(mockAgentFactory.buildMemory(config, memoryId)).thenReturn(mockMemory);

    ChatMemory result = manager.getChatMemory(config, memoryId);

    assertEquals(mockMemory, result);
    verify(mockAgentFactory).buildMemory(config, memoryId);
  }

  @Test
  void testGetChatMemory_WithNullConfig_DelegatesToAgentFactory() {
    Object memoryId = "session-2";
    ChatMemory mockMemory = mock(ChatMemory.class);
    when(mockAgentFactory.buildMemory(null, memoryId)).thenReturn(mockMemory);

    ChatMemory result = manager.getChatMemory(null, memoryId);

    assertEquals(mockMemory, result);
    verify(mockAgentFactory).buildMemory(null, memoryId);
  }
}
