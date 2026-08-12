package sh.trishul.ai.service.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.ai.agent.model.AiAgentConfig;
import sh.trishul.ai.agent.model.AiAgentConfigAccessor;
import sh.trishul.ai.chat.model.AiChatModelConfig;
import sh.trishul.ai.chat.model.AiChatModelConfigAccessor;
import sh.trishul.ai.memory.model.AiChatMemoryConfig;
import sh.trishul.ai.memory.model.AiChatMemoryConfigAccessor;
import sh.trishul.ai.service.agent.cache.AgentCache;
import sh.trishul.ai.service.agent.factory.AgentFactory;
import sh.trishul.ai.service.agent.factory.ChatModelFactory;
import sh.trishul.ai.service.agent.factory.StreamingChatModelFactory;
import sh.trishul.ai.service.agent.manager.AiAgentManagerWrapper;
import sh.trishul.ai.service.agent.model.controller.AiAgentConfigController;
import sh.trishul.ai.service.agent.model.repository.AiAgentConfigRepository;
import sh.trishul.ai.service.agent.model.service.AiAgentConfigService;
import sh.trishul.ai.service.agent.provider.AiAgentConfigProvider;
import sh.trishul.ai.service.memory.manager.AiChatMemoryManagerWrapper;
import sh.trishul.ai.service.memory.store.TenantChatMemoryStore;
import sh.trishul.ai.service.tool.registry.AiToolRegistry;
import sh.trishul.base.types.base.pojo.Refresher;
import sh.trishul.crud.controller.filter.AttributeFilter;
import sh.trishul.crud.service.LockService;
import sh.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;

class AiAgentConfigAutoConfigurationTest {

  private AiAgentConfigAutoConfiguration config;

  @BeforeEach
  void setUp() {
    config = new AiAgentConfigAutoConfiguration();
  }

  @Test
  void testStreamingChatModelFactory_ReturnsNonNull() {
    StreamingChatModelFactory result = config.streamingChatModelFactory();
    assertNotNull(result);
  }

  @Test
  void testChatModelFactory_ReturnsNonNull() {
    ChatModelFactory result = config.chatModelFactory();
    assertNotNull(result);
  }

  @Test
  void testAgentFactory_ReturnsNonNull() {
    TenantChatMemoryStore mockMemoryStore = mock(TenantChatMemoryStore.class);
    AiToolRegistry mockToolRegistry = mock(AiToolRegistry.class);
    StreamingChatModelFactory mockStreamingModelFactory = mock(StreamingChatModelFactory.class);
    ChatModelFactory mockChatModelFactory = mock(ChatModelFactory.class);
    AgentFactory result = config.agentFactory(mockMemoryStore, mockToolRegistry,
        mockStreamingModelFactory, mockChatModelFactory);
    assertNotNull(result);
  }

  @Test
  void testAiAgentConfigProvider_ReturnsNonNull() {
    AiAgentConfigService mockService = mock(AiAgentConfigService.class);
    AiAgentConfigProvider result = config.aiAgentConfigProvider(mockService);
    assertNotNull(result);
  }

  @Test
  void testAiAgentManagerWrapper_ReturnsNonNull() {
    AiAgentConfigProvider mockProvider = mock(AiAgentConfigProvider.class);
    AiChatMemoryManagerWrapper mockMemoryWrapper = mock(AiChatMemoryManagerWrapper.class);
    AgentFactory mockAgentFactory = mock(AgentFactory.class);
    AiAgentManagerWrapper result
        = config.aiAgentManagerWrapper(mockProvider, mockMemoryWrapper, mockAgentFactory);
    assertNotNull(result);
  }

  @Test
  void testAiAgentConfigController_ReturnsNonNull() {
    AiAgentConfigService mockService = mock(AiAgentConfigService.class);
    AttributeFilter mockFilter = mock(AttributeFilter.class);

    AiAgentConfigController result = config.aiAgentConfigController(mockService, mockFilter);

    assertNotNull(result);
  }

  @Test
  void testAgentCache_ReturnsNonNull() {
    AgentFactory mockAgentFactory = mock(AgentFactory.class);
    AgentCache result = config.agentCache(mockAgentFactory);
    assertNotNull(result);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testAiAgentConfigService_ReturnsNonNull() {
    LockService mockLockService = mock(LockService.class);
    AiAgentConfigRepository mockRepository = mock(AiAgentConfigRepository.class);
    Refresher<AiAgentConfig, AiAgentConfigAccessor<?>> mockRefresher = mock(Refresher.class);

    AiAgentConfigService result
        = config.aiAgentConfigService(mockLockService, mockRepository, mockRefresher);

    assertNotNull(result);
  }

  @Test
  void testAiAgentConfigAccessorRefresher_LambdaCoverage() {
    AiAgentConfigRepository mockRepo = mock(AiAgentConfigRepository.class);
    AccessorRefresher<Long, AiAgentConfigAccessor<?>, AiAgentConfig> refresher
        = config.aiAgentConfigAccessorRefresher(mockRepo);

    Long id = 1L;
    AiAgentConfig agentConfig = new AiAgentConfig(id);
    when(mockRepo.findAllById(any())).thenReturn(List.of(agentConfig));

    AiAgentConfigAccessor<?> mockAccessor = mock(AiAgentConfigAccessor.class);
    when(mockAccessor.getAgentConfig()).thenReturn(new AiAgentConfig(id));

    refresher.refreshAccessors(List.of(mockAccessor));

    verify(mockAccessor).setAgentConfig(null);
    verify(mockAccessor).setAgentConfig(agentConfig);
    verify(mockRepo).findAllById(Set.of(id));
  }

  @Test
  @SuppressWarnings("unchecked")
  void testAiAgentConfigRefresher_ReturnsNonNull() {
    AccessorRefresher<Long, AiAgentConfigAccessor<?>, AiAgentConfig> mockAccessorRefresher
        = mock(AccessorRefresher.class);
    Refresher<AiChatModelConfig, AiChatModelConfigAccessor<?>> mockModelRefresher
        = mock(Refresher.class);
    Refresher<AiChatMemoryConfig, AiChatMemoryConfigAccessor<?>> mockMemoryRefresher
        = mock(Refresher.class);

    Refresher<AiAgentConfig, AiAgentConfigAccessor<?>> result = config
        .aiAgentConfigRefresher(mockAccessorRefresher, mockModelRefresher, mockMemoryRefresher);

    assertNotNull(result);
  }
}
