package io.trishul.ai.service.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.ai.service.speech.model.controller.AiSpeechConfigController;
import io.trishul.ai.service.speech.model.repository.AiSpeechConfigRepository;
import io.trishul.ai.service.speech.model.service.AiSpeechConfigService;
import io.trishul.ai.speech.model.AiSpeechConfig;
import io.trishul.ai.speech.model.AiSpeechConfigAccessor;
import io.trishul.base.types.base.pojo.Refresher;
import io.trishul.crud.controller.filter.AttributeFilter;
import io.trishul.crud.service.LockService;
import io.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;

class AiSpeechConfigAutoConfigurationTest {

  private AiSpeechConfigAutoConfiguration config;

  @BeforeEach
  void setUp() {
    config = new AiSpeechConfigAutoConfiguration();
  }

  @Test
  @SuppressWarnings("unchecked")
  void testAiSpeechConfigService_ReturnsNonNull() {
    LockService mockLockService = mock(LockService.class);
    AiSpeechConfigRepository mockRepository = mock(AiSpeechConfigRepository.class);
    Refresher<AiSpeechConfig, AiSpeechConfigAccessor<?>> mockRefresher = mock(Refresher.class);

    AiSpeechConfigService result
        = config.aiSpeechConfigService(mockLockService, mockRepository, mockRefresher);

    assertNotNull(result);
  }

  @Test
  void testAiSpeechConfigController_ReturnsNonNull() {
    AiSpeechConfigService mockService = mock(AiSpeechConfigService.class);
    AttributeFilter mockFilter = mock(AttributeFilter.class);

    AiSpeechConfigController result = config.aiSpeechConfigController(mockService, mockFilter);

    assertNotNull(result);
  }

  @Test
  void testAiSpeechConfigAccessorRefresher_ReturnsNonNull() {
    AiSpeechConfigRepository mockRepository = mock(AiSpeechConfigRepository.class);
    AccessorRefresher<Long, AiSpeechConfigAccessor<?>, AiSpeechConfig> result
        = config.aiSpeechConfigAccessorRefresher(mockRepository);
    assertNotNull(result);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testAiSpeechConfigRefresher_ReturnsNonNull() {
    AccessorRefresher<Long, AiSpeechConfigAccessor<?>, AiSpeechConfig> mockAccessorRefresher
        = mock(AccessorRefresher.class);
    Refresher<AiSpeechConfig, AiSpeechConfigAccessor<?>> result
        = config.aiSpeechConfigRefresher(mockAccessorRefresher);
    assertNotNull(result);
  }
}
