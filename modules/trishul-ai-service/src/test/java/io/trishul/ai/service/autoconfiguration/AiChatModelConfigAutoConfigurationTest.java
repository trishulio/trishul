package io.trishul.ai.service.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.ai.chat.model.AiChatModelConfig;
import io.trishul.ai.chat.model.AiChatModelConfigAccessor;
import io.trishul.ai.service.chat.model.controller.AiChatModelConfigController;
import io.trishul.ai.service.chat.model.repository.AiChatModelConfigRepository;
import io.trishul.ai.service.chat.model.service.AiChatModelConfigService;
import io.trishul.repo.jpa.converter.StringCryptoConverter;
import io.trishul.base.types.base.pojo.Refresher;
import io.trishul.crud.controller.filter.AttributeFilter;
import io.trishul.crud.service.LockService;
import io.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;

class AiChatModelConfigAutoConfigurationTest {

  private AiChatModelConfigAutoConfiguration config;

  @BeforeEach
  void setUp() {
    config = new AiChatModelConfigAutoConfiguration();
  }

  @Test
  void testStringCryptoConverter_ReturnsNonNull() {
    StringCryptoConverter result = config.stringCryptoConverter("AES", "key");
    assertNotNull(result);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testAiChatModelConfigService_ReturnsNonNull() {
    LockService mockLockService = mock(LockService.class);
    AiChatModelConfigRepository mockRepository = mock(AiChatModelConfigRepository.class);
    Refresher<AiChatModelConfig, AiChatModelConfigAccessor<?>> mockRefresher
        = mock(Refresher.class);

    AiChatModelConfigService result
        = config.aiChatModelConfigService(mockLockService, mockRepository, mockRefresher);

    assertNotNull(result);
  }

  @Test
  void testAiChatModelConfigController_ReturnsNonNull() {
    AiChatModelConfigService mockService = mock(AiChatModelConfigService.class);
    AttributeFilter mockFilter = mock(AttributeFilter.class);

    AiChatModelConfigController result
        = config.aiChatModelConfigController(mockService, mockFilter);

    assertNotNull(result);
  }

  @Test
  void testAiChatModelConfigAccessorRefresher_ReturnsNonNull() {
    AiChatModelConfigRepository mockRepository = mock(AiChatModelConfigRepository.class);
    AccessorRefresher<Long, AiChatModelConfigAccessor<?>, AiChatModelConfig> result
        = config.aiChatModelConfigAccessorRefresher(mockRepository);
    assertNotNull(result);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testAiChatModelConfigRefresher_ReturnsNonNull() {
    AccessorRefresher<Long, AiChatModelConfigAccessor<?>, AiChatModelConfig> mockAccessorRefresher
        = mock(AccessorRefresher.class);
    Refresher<AiChatModelConfig, AiChatModelConfigAccessor<?>> result
        = config.aiChatModelConfigRefresher(mockAccessorRefresher);
    assertNotNull(result);
  }
}
