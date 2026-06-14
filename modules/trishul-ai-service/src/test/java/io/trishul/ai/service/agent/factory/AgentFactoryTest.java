package io.trishul.ai.service.agent.factory;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import io.trishul.ai.agent.model.AiAgentConfig;
import io.trishul.ai.chat.model.AiChatModelConfig;
import io.trishul.ai.memory.model.AiChatMemoryConfig;
import io.trishul.ai.service.memory.store.TenantChatMemoryStore;
import io.trishul.ai.service.tool.registry.AiToolRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AgentFactoryTest {

  private AgentFactory agentFactory;
  private TenantChatMemoryStore mockMemoryStore;
  private AiToolRegistry mockToolRegistry;
  private StreamingChatModelFactory mockModelFactory;

  @BeforeEach
  void setUp() {
    mockMemoryStore = mock(TenantChatMemoryStore.class);
    mockToolRegistry = mock(AiToolRegistry.class);
    mockModelFactory = mock(StreamingChatModelFactory.class);
    agentFactory = new AgentFactory(mockMemoryStore, mockToolRegistry, mockModelFactory);
  }

  @Test
  void testBuildModel_CallsModelFactory() {
    AiChatModelConfig config = new AiChatModelConfig();
    config.setProvider("openai");
    StreamingChatLanguageModel mockModel = mock(StreamingChatLanguageModel.class);
    when(mockModelFactory.getModel(eq(AiProvider.OPENAI), eq(config))).thenReturn(mockModel);

    StreamingChatLanguageModel result = agentFactory.buildModel(config);

    assertNotNull(result);
  }

  @Test
  void testBuildModel_ThrowsException_WhenConfigIsNull() {
    assertThrows(IllegalArgumentException.class, () -> agentFactory.buildModel(null));
  }

  @Test
  void testBuildMemory_ReturnsNonNull() {
    AiChatMemoryConfig memoryConfig = new AiChatMemoryConfig();
    memoryConfig.setMaxMessages(20);

    ChatMemory result = agentFactory.buildMemory(memoryConfig, "memoryId");

    assertNotNull(result);
  }

  @Test
  void testBuildAgent_ReturnsModel_ForNow() {
    AiAgentConfig agentConfig = new AiAgentConfig();
    AiChatModelConfig modelConfig = new AiChatModelConfig();
    modelConfig.setProvider("openai");
    agentConfig.setChatModelConfig(modelConfig);

    StreamingChatLanguageModel mockModel = mock(StreamingChatLanguageModel.class);
    when(mockModelFactory.getModel(eq(AiProvider.OPENAI), eq(modelConfig))).thenReturn(mockModel);

    Object result = agentFactory.buildAgent(agentConfig);

    assertNotNull(result);
  }

  @Test
  void testBuildAgent_ThrowsException_WhenConfigIsNull() {
    assertThrows(IllegalArgumentException.class, () -> agentFactory.buildAgent(null));
  }

  @Test
  void testBuildMemory_UsesDefaultMaxMessages_WhenConfigIsNull() {
    ChatMemory result = agentFactory.buildMemory(null, "memoryId");
    assertNotNull(result);
  }

  @Test
  void testBuildMemory_UsesDefaultMaxMessages_WhenMaxMessagesIsNull() {
    AiChatMemoryConfig memoryConfig = new AiChatMemoryConfig();
    memoryConfig.setMaxMessages(null);

    ChatMemory result = agentFactory.buildMemory(memoryConfig, "memoryId");
    assertNotNull(result);
  }
}
