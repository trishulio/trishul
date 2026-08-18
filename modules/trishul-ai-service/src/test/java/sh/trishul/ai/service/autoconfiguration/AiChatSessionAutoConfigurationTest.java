package sh.trishul.ai.service.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.ai.agent.model.AiAgentConfig;
import sh.trishul.ai.agent.model.AiAgentConfigAccessor;
import sh.trishul.ai.memory.model.AiChatMemoryConfig;
import sh.trishul.ai.memory.model.AiChatMemoryConfigAccessor;
import sh.trishul.ai.service.session.model.controller.AiChatSessionController;
import sh.trishul.ai.service.session.model.repository.AiChatSessionRepository;
import sh.trishul.ai.service.session.model.service.AiChatSessionService;
import sh.trishul.ai.session.model.AiChatSession;
import sh.trishul.ai.session.model.AiChatSessionAccessor;
import sh.trishul.base.types.base.pojo.Refresher;
import sh.trishul.crud.controller.filter.AttributeFilter;
import sh.trishul.crud.service.LockService;
import sh.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;

class AiChatSessionAutoConfigurationTest {

  private AiChatSessionAutoConfiguration config;

  @BeforeEach
  void setUp() {
    config = new AiChatSessionAutoConfiguration();
  }

  @Test
  @SuppressWarnings("unchecked")
  void testAiChatSessionService_ReturnsNonNull() {
    LockService mockLockService = mock(LockService.class);
    AiChatSessionRepository mockRepository = mock(AiChatSessionRepository.class);
    Refresher<AiChatSession, AiChatSessionAccessor<?>> mockRefresher = mock(Refresher.class);

    AiChatSessionService result
        = config.aiChatSessionService(mockLockService, mockRepository, mockRefresher);

    assertNotNull(result);
  }

  @Test
  void testAiChatSessionController_ReturnsNonNull() {
    AiChatSessionService mockService = mock(AiChatSessionService.class);
    AttributeFilter mockFilter = mock(AttributeFilter.class);

    AiChatSessionController result = config.aiChatSessionController(mockService, mockFilter);

    assertNotNull(result);
  }

  @Test
  void testAiChatSessionAccessorRefresher_ReturnsNonNull() {
    AiChatSessionRepository mockRepository = mock(AiChatSessionRepository.class);
    AccessorRefresher<Long, AiChatSessionAccessor<?>, AiChatSession> result
        = config.aiChatSessionAccessorRefresher(mockRepository);
    assertNotNull(result);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testAiChatSessionRefresher_ReturnsNonNull() {
    AccessorRefresher<Long, AiChatSessionAccessor<?>, AiChatSession> mockAccessorRefresher
        = mock(AccessorRefresher.class);
    Refresher<AiAgentConfig, AiAgentConfigAccessor<?>> mockAgentConfigRefresher
        = mock(Refresher.class);
    Refresher<AiChatMemoryConfig, AiChatMemoryConfigAccessor<?>> mockChatMemoryConfigRefresher
        = mock(Refresher.class);

    Refresher<AiChatSession, AiChatSessionAccessor<?>> result = config.aiChatSessionRefresher(
        mockAccessorRefresher, mockAgentConfigRefresher, mockChatMemoryConfigRefresher);

    assertNotNull(result);
  }
}
