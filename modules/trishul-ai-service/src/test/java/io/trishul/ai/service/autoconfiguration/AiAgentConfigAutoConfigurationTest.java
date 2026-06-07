package io.trishul.ai.service.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.ai.agent.model.AiAgentConfig;
import io.trishul.ai.agent.model.AiAgentConfigAccessor;
import io.trishul.ai.service.agent.cache.AgentCache;
import io.trishul.ai.service.agent.factory.AgentFactory;
import io.trishul.ai.service.agent.model.repository.AiAgentConfigRepository;
import io.trishul.ai.service.agent.model.service.AiAgentConfigService;
import io.trishul.base.types.base.pojo.Refresher;
import io.trishul.crud.service.LockService;

import io.trishul.ai.service.memory.store.TenantChatMemoryStore;
import io.trishul.ai.service.tool.registry.AiToolRegistry;

class AiAgentConfigAutoConfigurationTest {

  private AiAgentConfigAutoConfiguration config;

  @BeforeEach
  void setUp() {
    config = new AiAgentConfigAutoConfiguration();
  }

  @Test
  void testAgentFactory_ReturnsNonNull() {
    TenantChatMemoryStore mockMemoryStore = mock(TenantChatMemoryStore.class);
    AiToolRegistry mockToolRegistry = mock(AiToolRegistry.class);
    AgentFactory result = config.agentFactory(mockMemoryStore, mockToolRegistry);
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

    AiAgentConfigService result = config.aiAgentConfigService(mockLockService, mockRepository, mockRefresher);

    assertNotNull(result);
  }
}
