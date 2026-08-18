package sh.trishul.ai.service.memory.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.langchain4j.memory.ChatMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.ai.memory.model.AiChatMemoryConfig;
import sh.trishul.ai.service.memory.provider.AiChatMemoryConfigProvider;

class AiChatMemoryManagerWrapperTest {

  private AiChatMemoryManagerWrapper wrapper;
  private AiChatMemoryConfigProvider mockConfigProvider;
  private AiChatMemoryManager mockMemoryManager;

  @BeforeEach
  void setUp() {
    mockConfigProvider = mock(AiChatMemoryConfigProvider.class);
    mockMemoryManager = mock(AiChatMemoryManager.class);
    wrapper = new AiChatMemoryManagerWrapper(mockConfigProvider, mockMemoryManager);
  }

  @Test
  void testGetChatMemory_WithConfigId_ResolvesConfigAndBuildsMemory() {
    Long configId = 10L;
    Object memoryId = "session-abc";
    AiChatMemoryConfig config = new AiChatMemoryConfig(configId);
    ChatMemory mockMemory = mock(ChatMemory.class);

    when(mockConfigProvider.getChatMemoryConfig(configId)).thenReturn(config);
    when(mockMemoryManager.getChatMemory(config, memoryId)).thenReturn(mockMemory);

    ChatMemory result = wrapper.getChatMemory(configId, memoryId);

    assertEquals(mockMemory, result);
    verify(mockConfigProvider).getChatMemoryConfig(configId);
    verify(mockMemoryManager).getChatMemory(config, memoryId);
  }

  @Test
  void testGetChatMemory_WithNullConfigId_UsesNullConfig() {
    Object memoryId = "session-xyz";
    ChatMemory mockMemory = mock(ChatMemory.class);
    when(mockMemoryManager.getChatMemory(null, memoryId)).thenReturn(mockMemory);

    ChatMemory result = wrapper.getChatMemory(null, memoryId);

    assertEquals(mockMemory, result);
    verify(mockMemoryManager).getChatMemory(null, memoryId);
  }
}
