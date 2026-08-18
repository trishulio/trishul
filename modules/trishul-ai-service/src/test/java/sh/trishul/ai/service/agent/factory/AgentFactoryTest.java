package sh.trishul.ai.service.agent.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.service.AiServiceContext;
import dev.langchain4j.service.AiServices;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.ai.agent.model.AiAgentConfig;
import sh.trishul.ai.chat.model.AiChatModelConfig;
import sh.trishul.ai.memory.model.AiChatMemoryConfig;
import sh.trishul.ai.service.agent.Assistant;
import sh.trishul.ai.service.memory.store.TenantChatMemoryStore;
import sh.trishul.ai.service.tool.registry.AiToolRegistry;

class AgentFactoryTest {

  private AgentFactory agentFactory;
  private TenantChatMemoryStore mockMemoryStore;
  private AiToolRegistry mockToolRegistry;
  private StreamingChatModelFactory mockStreamingModelFactory;
  private ChatModelFactory mockChatModelFactory;

  @BeforeEach
  @SuppressWarnings("unchecked")
  void setUp() {
    mockMemoryStore = mock(TenantChatMemoryStore.class);
    mockToolRegistry = mock(AiToolRegistry.class);
    mockStreamingModelFactory = mock(StreamingChatModelFactory.class);
    mockChatModelFactory = mock(ChatModelFactory.class);
    agentFactory = new AgentFactory(mockMemoryStore, mockToolRegistry, mockStreamingModelFactory,
        mockChatModelFactory);

    Map<Object, List<ChatMessage>> storeMap = new HashMap<>();
    when(mockMemoryStore.getMessages(any())).thenAnswer(
        invocation -> storeMap.getOrDefault(invocation.getArgument(0), new ArrayList<>()));
    doAnswer(invocation -> {
      storeMap.put(invocation.getArgument(0),
          new ArrayList<>((List<ChatMessage>) invocation.getArgument(1)));
      return null;
    }).when(mockMemoryStore).updateMessages(any(), any());
  }

  @Test
  void testBuildStreamingModel_CallsStreamingModelFactory() {
    AiChatModelConfig config = new AiChatModelConfig();
    config.setProvider("openai");
    StreamingChatModel mockModel = mock(StreamingChatModel.class);
    when(mockStreamingModelFactory.getModel(eq(AiProvider.OPENAI), eq(config)))
        .thenReturn(mockModel);

    StreamingChatModel result = agentFactory.buildStreamingModel(config);

    assertNotNull(result);
  }

  @Test
  void testBuildStreamingModel_ThrowsException_WhenConfigIsNull() {
    assertThrows(IllegalArgumentException.class, () -> agentFactory.buildStreamingModel(null));
  }

  @Test
  void testLegacyBuildAgent_ThrowsException_WhenConfigIsNull() {
    assertThrows(IllegalArgumentException.class, () -> agentFactory.buildAgent(null));
  }

  @Test
  void testBuildAgentWithMemory_ThrowsException_WhenConfigIsNull() {
    ChatMemory mockMemory = mock(ChatMemory.class);
    assertThrows(IllegalArgumentException.class, () -> agentFactory.buildAgent(null, mockMemory));
  }

  @Test
  void testBuildAgent_ReturnsAssistant() {
    AiChatModelConfig modelConfig = new AiChatModelConfig();
    modelConfig.setProvider("openai");

    AiChatMemoryConfig memoryConfig = new AiChatMemoryConfig();
    memoryConfig.setMaxMessages(10);

    AiAgentConfig config = new AiAgentConfig();
    config.setChatModelConfig(modelConfig);
    config.setChatMemoryConfig(memoryConfig);

    ChatModel mockChatModel = mock(ChatModel.class);
    StreamingChatModel mockStreamingChatModel = mock(StreamingChatModel.class);

    when(mockChatModelFactory.getModel(eq(AiProvider.OPENAI), eq(modelConfig)))
        .thenReturn(mockChatModel);
    when(mockStreamingModelFactory.getModel(eq(AiProvider.OPENAI), eq(modelConfig)))
        .thenReturn(mockStreamingChatModel);

    Assistant result = agentFactory.buildAgent(config, mock(ChatMemory.class));
    assertNotNull(result);

    AiServiceContext ctx = getAiServiceContext(result);
    assertNotNull(ctx);
    assertTrue(ctx.toolService.toolSpecifications().isEmpty());
  }

  @Test
  void testBuildAgent_ReturnsAssistant_WithTools() {
    AiChatModelConfig modelConfig = new AiChatModelConfig();
    modelConfig.setProvider("openai");

    AiChatMemoryConfig memoryConfig = new AiChatMemoryConfig();
    memoryConfig.setMaxMessages(10);

    AiAgentConfig config = new AiAgentConfig();
    config.setChatModelConfig(modelConfig);
    config.setChatMemoryConfig(memoryConfig);

    ChatModel mockChatModel = mock(ChatModel.class);
    StreamingChatModel mockStreamingChatModel = mock(StreamingChatModel.class);

    when(mockChatModelFactory.getModel(eq(AiProvider.OPENAI), eq(modelConfig)))
        .thenReturn(mockChatModel);
    when(mockStreamingModelFactory.getModel(eq(AiProvider.OPENAI), eq(modelConfig)))
        .thenReturn(mockStreamingChatModel);
    when(mockToolRegistry.getToolsByIds(any())).thenReturn(List.of(new MyTestTool()));

    Assistant result = agentFactory.buildAgent(config, mock(ChatMemory.class));
    assertNotNull(result);

    AiServiceContext ctx = getAiServiceContext(result);
    assertNotNull(ctx);
    assertFalse(ctx.toolService.toolSpecifications().isEmpty());
  }

  @Test
  void testLegacyBuildAgent_ReturnsAssistant() {
    AiChatModelConfig modelConfig = new AiChatModelConfig();
    modelConfig.setProvider("openai");

    AiChatMemoryConfig memoryConfig = new AiChatMemoryConfig();
    memoryConfig.setMaxMessages(10);

    AiAgentConfig config = new AiAgentConfig();
    config.setChatModelConfig(modelConfig);
    config.setChatMemoryConfig(memoryConfig);

    ChatModel mockChatModel = mock(ChatModel.class);
    StreamingChatModel mockStreamingChatModel = mock(StreamingChatModel.class);

    when(mockChatModelFactory.getModel(eq(AiProvider.OPENAI), eq(modelConfig)))
        .thenReturn(mockChatModel);
    when(mockStreamingModelFactory.getModel(eq(AiProvider.OPENAI), eq(modelConfig)))
        .thenReturn(mockStreamingChatModel);
    when(mockToolRegistry.getToolsByIds(any())).thenReturn(List.of(new MyTestTool()));

    Object result = agentFactory.buildAgent(config);
    assertNotNull(result);

    AiServiceContext ctx = getAiServiceContext(result);
    assertNotNull(ctx);
    assertFalse(ctx.toolService.toolSpecifications().isEmpty());
  }

  @Test
  void testLegacyBuildAgent_TriggersChatMemoryProvider_WhenChatIsInvoked() {
    AiChatModelConfig modelConfig = new AiChatModelConfig();
    modelConfig.setProvider("openai");

    AiChatMemoryConfig memoryConfig = new AiChatMemoryConfig();
    memoryConfig.setMaxMessages(10);

    AiAgentConfig config = new AiAgentConfig();
    config.setChatModelConfig(modelConfig);
    config.setChatMemoryConfig(memoryConfig);

    ChatModel mockChatModel = mock(ChatModel.class);
    StreamingChatModel mockStreamingChatModel = mock(StreamingChatModel.class);

    when(mockChatModelFactory.getModel(eq(AiProvider.OPENAI), eq(modelConfig)))
        .thenReturn(mockChatModel);
    when(mockStreamingModelFactory.getModel(eq(AiProvider.OPENAI), eq(modelConfig)))
        .thenReturn(mockStreamingChatModel);

    // Mock ChatModel response
    ChatResponse mockResponse
        = ChatResponse.builder().aiMessage(AiMessage.from("response")).build();
    when(mockChatModel.chat(any(ChatRequest.class))).thenReturn(mockResponse);
    when(mockChatModel.chat(any(ChatRequest.class), any())).thenReturn(mockResponse);

    Object result = agentFactory.buildAgent(config);
    assertNotNull(result);

    Assistant assistant = (Assistant) result;
    assistant.chat("session-123", UserMessage.from("hello"));
  }

  private AiServiceContext getAiServiceContext(Object assistantProxy) {
    try {
      InvocationHandler handler = Proxy.getInvocationHandler(assistantProxy);
      Field this0Field = handler.getClass().getDeclaredField("this$0");
      this0Field.setAccessible(true);
      Object defaultAiServices = this0Field.get(handler);
      Field contextField = AiServices.class.getDeclaredField("context");
      contextField.setAccessible(true);
      return (AiServiceContext) contextField.get(defaultAiServices);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


  @Test
  void testBuildMemory_WithNullConfig_UsesDefaultMaxMessages() {
    ChatMemory memory = agentFactory.buildMemory(null, "session-1");
    assertNotNull(memory);
    for (int i = 0; i < 15; i++) {
      memory.add(UserMessage.from("msg " + i));
    }
    assertEquals(10, memory.messages().size());
  }

  @Test
  void testBuildMemory_WithNullMaxMessages_UsesDefaultMaxMessages() {
    AiChatMemoryConfig config = new AiChatMemoryConfig();
    config.setMaxMessages(null);
    ChatMemory memory = agentFactory.buildMemory(config, "session-1");
    assertNotNull(memory);
    for (int i = 0; i < 15; i++) {
      memory.add(UserMessage.from("msg " + i));
    }
    assertEquals(10, memory.messages().size());
  }

  @Test
  void testBuildMemory_WithValidMaxMessages_UsesConfiguredMaxMessages() {
    AiChatMemoryConfig config = new AiChatMemoryConfig();
    config.setMaxMessages(5);
    ChatMemory memory = agentFactory.buildMemory(config, "session-1");
    assertNotNull(memory);
    for (int i = 0; i < 15; i++) {
      memory.add(UserMessage.from("msg " + i));
    }
    assertEquals(5, memory.messages().size());
  }

  public static class MyTestTool {
    @Tool("test tool")
    public String execute() {
      return "done";
    }
  }
}
