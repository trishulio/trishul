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
import sh.trishul.ai.memory.model.AiChatMemoryConfig;
import sh.trishul.ai.memory.model.AiChatMemoryConfigAccessor;
import sh.trishul.ai.service.agent.factory.AgentFactory;
import sh.trishul.ai.service.memory.manager.AiChatMemoryManager;
import sh.trishul.ai.service.memory.manager.AiChatMemoryManagerWrapper;
import sh.trishul.ai.service.memory.model.controller.AiChatMemoryConfigController;
import sh.trishul.ai.service.memory.model.repository.AiChatMemoryConfigRepository;
import sh.trishul.ai.service.memory.model.service.AiChatMemoryConfigService;
import sh.trishul.ai.service.memory.provider.AiChatMemoryConfigProvider;
import sh.trishul.ai.service.memory.store.TenantChatMemoryStore;
import sh.trishul.base.types.base.pojo.Refresher;
import sh.trishul.crud.controller.filter.AttributeFilter;
import sh.trishul.crud.service.LockService;
import sh.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;

class AiChatMemoryConfigAutoConfigurationTest {

  private AiChatMemoryConfigAutoConfiguration config;

  @BeforeEach
  void setUp() {
    config = new AiChatMemoryConfigAutoConfiguration();
  }

  @Test
  void testAiChatMemoryConfigController_ReturnsNonNull() {
    AiChatMemoryConfigService mockService = mock(AiChatMemoryConfigService.class);
    AttributeFilter mockFilter = mock(AttributeFilter.class);

    AiChatMemoryConfigController result
        = config.aiChatMemoryConfigController(mockService, mockFilter);

    assertNotNull(result);
  }

  @Test
  void testTenantChatMemoryStore_ReturnsNonNull() {
    TenantChatMemoryStore result = config.tenantChatMemoryStore();
    assertNotNull(result);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testAiChatMemoryConfigService_ReturnsNonNull() {
    LockService mockLockService = mock(LockService.class);
    AiChatMemoryConfigRepository mockRepository = mock(AiChatMemoryConfigRepository.class);
    Refresher<AiChatMemoryConfig, AiChatMemoryConfigAccessor<?>> mockRefresher
        = mock(Refresher.class);

    AiChatMemoryConfigService result
        = config.aiChatMemoryConfigService(mockLockService, mockRepository, mockRefresher);

    assertNotNull(result);
  }

  @Test
  void testAiChatMemoryConfigAccessorRefresher_LambdaCoverage() {
    AiChatMemoryConfigRepository mockRepo = mock(AiChatMemoryConfigRepository.class);
    AccessorRefresher<Long, AiChatMemoryConfigAccessor<?>, AiChatMemoryConfig> refresher
        = config.aiChatMemoryConfigAccessorRefresher(mockRepo);

    Long id = 1L;
    AiChatMemoryConfig configObj = new AiChatMemoryConfig(id);
    when(mockRepo.findAllById(any())).thenReturn(List.of(configObj));

    AiChatMemoryConfigAccessor<?> mockAccessor = mock(AiChatMemoryConfigAccessor.class);
    when(mockAccessor.getChatMemoryConfig()).thenReturn(new AiChatMemoryConfig(id));

    refresher.refreshAccessors(List.of(mockAccessor));

    verify(mockAccessor).setChatMemoryConfig(null);
    verify(mockAccessor).setChatMemoryConfig(configObj);
    verify(mockRepo).findAllById(Set.of(id));
  }

  @Test
  @SuppressWarnings("unchecked")
  void testAiChatMemoryConfigRefresher_ReturnsNonNull() {
    AccessorRefresher<Long, AiChatMemoryConfigAccessor<?>, AiChatMemoryConfig> mockAccessorRefresher
        = mock(AccessorRefresher.class);

    Refresher<AiChatMemoryConfig, AiChatMemoryConfigAccessor<?>> result
        = config.aiChatMemoryConfigRefresher(mockAccessorRefresher);

    assertNotNull(result);
  }

  @Test
  void testAiChatMemoryConfigProvider_ReturnsNonNull() {
    AiChatMemoryConfigService mockService = mock(AiChatMemoryConfigService.class);
    AiChatMemoryConfigProvider result = config.aiChatMemoryConfigProvider(mockService);
    assertNotNull(result);
  }

  @Test
  void testAiChatMemoryManager_ReturnsNonNull() {
    AgentFactory mockAgentFactory = mock(AgentFactory.class);
    AiChatMemoryManager result = config.aiChatMemoryManager(mockAgentFactory);
    assertNotNull(result);
  }

  @Test
  void testAiChatMemoryManagerWrapper_ReturnsNonNull() {
    AiChatMemoryConfigProvider mockProvider = mock(AiChatMemoryConfigProvider.class);
    AiChatMemoryManager mockManager = mock(AiChatMemoryManager.class);
    AiChatMemoryManagerWrapper result
        = config.aiChatMemoryManagerWrapper(mockProvider, mockManager);
    assertNotNull(result);
  }
}
