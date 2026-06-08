package io.trishul.ai.service.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.ai.agent.model.AiAgentConfig;
import io.trishul.ai.agent.model.AiAgentConfigAccessor;
import io.trishul.ai.chat.model.AiChatModelConfig;
import io.trishul.ai.chat.model.AiChatModelConfigAccessor;
import io.trishul.ai.memory.model.AiChatMemoryConfig;
import io.trishul.ai.memory.model.AiChatMemoryConfigAccessor;
import io.trishul.ai.service.agent.cache.AgentCache;
import io.trishul.ai.service.agent.factory.AgentFactory;
import io.trishul.ai.service.agent.model.repository.AiAgentConfigRepository;
import io.trishul.ai.service.agent.model.service.AiAgentConfigService;
import io.trishul.base.types.base.pojo.Refresher;
import io.trishul.crud.service.LockService;
import io.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;
import java.util.List;
import java.util.Set;

import io.trishul.ai.service.agent.factory.StreamingChatModelFactory;
import io.trishul.ai.service.agent.model.controller.AiAgentConfigController;
import io.trishul.crud.controller.filter.AttributeFilter;
import io.trishul.ai.service.memory.store.TenantChatMemoryStore;
import io.trishul.ai.service.tool.registry.AiToolRegistry;

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
  void testAgentFactory_ReturnsNonNull() {
    TenantChatMemoryStore mockMemoryStore = mock(TenantChatMemoryStore.class);
    AiToolRegistry mockToolRegistry = mock(AiToolRegistry.class);
    StreamingChatModelFactory mockModelFactory = mock(StreamingChatModelFactory.class);
    AgentFactory result = config.agentFactory(mockMemoryStore, mockToolRegistry, mockModelFactory);
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
