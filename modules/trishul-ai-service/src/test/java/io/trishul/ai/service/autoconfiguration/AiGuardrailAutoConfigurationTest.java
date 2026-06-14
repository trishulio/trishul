package io.trishul.ai.service.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import io.trishul.ai.guardrail.model.AiGuardrail;
import io.trishul.ai.guardrail.model.AiGuardrailAccessor;
import io.trishul.ai.service.guardrail.model.controller.AiGuardrailController;
import io.trishul.ai.service.guardrail.model.repository.AiGuardrailRepository;
import io.trishul.ai.service.guardrail.model.service.AiGuardrailService;
import io.trishul.ai.service.guardrail.pipeline.GuardrailPipeline;
import io.trishul.base.types.base.pojo.Refresher;
import io.trishul.crud.controller.filter.AttributeFilter;
import io.trishul.crud.service.LockService;
import io.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AiGuardrailAutoConfigurationTest {

  private AiGuardrailAutoConfiguration config;

  @BeforeEach
  void setUp() {
    config = new AiGuardrailAutoConfiguration();
  }

  @Test
  void testAiGuardrailController_ReturnsNonNull() {
    AiGuardrailService mockService = mock(AiGuardrailService.class);
    AttributeFilter mockFilter = mock(AttributeFilter.class);

    AiGuardrailController result = config.aiGuardrailController(mockService, mockFilter);

    assertNotNull(result);
  }

  @Test
  void testAiGuardrailAccessorRefresher_ReturnsNonNull() {
    AiGuardrailRepository mockRepository = mock(AiGuardrailRepository.class);
    AccessorRefresher<Long, AiGuardrailAccessor<?>, AiGuardrail> result
        = config.aiGuardrailAccessorRefresher(mockRepository);
    assertNotNull(result);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testAiGuardrailRefresher_ReturnsNonNull() {
    AccessorRefresher<Long, AiGuardrailAccessor<?>, AiGuardrail> mockAccessorRefresher
        = mock(AccessorRefresher.class);
    Refresher<AiGuardrail, AiGuardrailAccessor<?>> result
        = config.aiGuardrailRefresher(mockAccessorRefresher);
    assertNotNull(result);
  }

  @Test
  void testGuardrailPipeline_ReturnsNonNull() {
    GuardrailPipeline result = config.guardrailPipeline();
    assertNotNull(result);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testAiGuardrailService_ReturnsNonNull() {
    LockService mockLockService = mock(LockService.class);
    AiGuardrailRepository mockRepository = mock(AiGuardrailRepository.class);
    Refresher<AiGuardrail, AiGuardrailAccessor<?>> mockRefresher = mock(Refresher.class);

    AiGuardrailService result
        = config.aiGuardrailService(mockLockService, mockRepository, mockRefresher);

    assertNotNull(result);
  }
}
