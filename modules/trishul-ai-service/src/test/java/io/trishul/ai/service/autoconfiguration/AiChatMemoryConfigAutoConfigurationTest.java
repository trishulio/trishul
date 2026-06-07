package io.trishul.ai.service.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.ai.memory.model.AiChatMemoryConfig;
import io.trishul.ai.memory.model.AiChatMemoryConfigAccessor;
import io.trishul.ai.service.memory.model.repository.AiChatMemoryConfigRepository;
import io.trishul.ai.service.memory.model.service.AiChatMemoryConfigService;
import io.trishul.ai.service.memory.store.TenantChatMemoryStore;
import io.trishul.base.types.base.pojo.Refresher;
import io.trishul.crud.service.LockService;

class AiChatMemoryConfigAutoConfigurationTest {

  private AiChatMemoryConfigAutoConfiguration config;

  @BeforeEach
  void setUp() {
    config = new AiChatMemoryConfigAutoConfiguration();
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
    Refresher<AiChatMemoryConfig, AiChatMemoryConfigAccessor<?>> mockRefresher = mock(Refresher.class);

    AiChatMemoryConfigService result = config.aiChatMemoryConfigService(mockLockService, mockRepository, mockRefresher);

    assertNotNull(result);
  }
}
