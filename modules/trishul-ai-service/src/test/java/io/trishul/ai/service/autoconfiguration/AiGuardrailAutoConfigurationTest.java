package io.trishul.ai.service.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.ai.guardrail.model.AiGuardrail;
import io.trishul.ai.guardrail.model.AiGuardrailAccessor;
import io.trishul.ai.service.guardrail.model.repository.AiGuardrailRepository;
import io.trishul.ai.service.guardrail.model.service.AiGuardrailService;
import io.trishul.ai.service.guardrail.pipeline.GuardrailPipeline;
import io.trishul.base.types.base.pojo.Refresher;
import io.trishul.crud.service.LockService;

class AiGuardrailAutoConfigurationTest {

  private AiGuardrailAutoConfiguration config;

  @BeforeEach
  void setUp() {
    config = new AiGuardrailAutoConfiguration();
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

    AiGuardrailService result = config.aiGuardrailService(mockLockService, mockRepository, mockRefresher);

    assertNotNull(result);
  }
}
