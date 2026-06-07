package io.trishul.ai.service.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.ai.service.tool.model.repository.AiToolRepository;
import io.trishul.ai.service.tool.model.service.AiToolService;
import io.trishul.ai.service.tool.registry.AiToolRegistry;
import io.trishul.ai.tool.model.AiTool;
import io.trishul.ai.tool.model.AiToolAccessor;
import io.trishul.base.types.base.pojo.Refresher;
import io.trishul.crud.service.LockService;

class AiToolAutoConfigurationTest {

  private AiToolAutoConfiguration config;

  @BeforeEach
  void setUp() {
    config = new AiToolAutoConfiguration();
  }

  @Test
  void testAiToolRegistry_ReturnsNonNull() {
    AiToolRegistry result = config.aiToolRegistry();
    assertNotNull(result);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testAiToolService_ReturnsNonNull() {
    LockService mockLockService = mock(LockService.class);
    AiToolRepository mockRepository = mock(AiToolRepository.class);
    Refresher<AiTool, AiToolAccessor<?>> mockRefresher = mock(Refresher.class);

    AiToolService result = config.aiToolService(mockLockService, mockRepository, mockRefresher);

    assertNotNull(result);
  }
}
