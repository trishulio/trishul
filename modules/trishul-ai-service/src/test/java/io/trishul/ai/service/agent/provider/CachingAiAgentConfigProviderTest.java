package io.trishul.ai.service.agent.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import com.google.common.cache.LoadingCache;
import io.trishul.ai.agent.model.AiAgentConfig;
import io.trishul.ai.service.agent.model.service.AiAgentConfigService;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CachingAiAgentConfigProviderTest {

  private CachingAiAgentConfigProvider provider;
  private AiAgentConfigService mockService;

  @BeforeEach
  void setUp() {
    mockService = mock(AiAgentConfigService.class);
    provider = new CachingAiAgentConfigProvider(mockService);
  }

  @Test
  void testGetAgentConfig_LoadsFromServiceOnCacheMiss() {
    Long id = 1L;
    AiAgentConfig config = new AiAgentConfig(id);
    when(mockService.get(id)).thenReturn(config);

    AiAgentConfig result = provider.getAgentConfig(id);

    assertEquals(config, result);
    verify(mockService).get(id);
  }

  @Test
  void testGetAgentConfig_ReturnsCachedValue_OnSecondCall() {
    Long id = 2L;
    AiAgentConfig config = new AiAgentConfig(id);
    when(mockService.get(id)).thenReturn(config);

    provider.getAgentConfig(id);
    provider.getAgentConfig(id);

    // Service should only be called once due to caching
    verify(mockService, times(1)).get(id);
  }

  @Test
  void testGetAgentConfig_ReloadsAfterEvict() {
    Long id = 3L;
    AiAgentConfig config1 = new AiAgentConfig(id);
    AiAgentConfig config2 = new AiAgentConfig(id);
    when(mockService.get(id)).thenReturn(config1).thenReturn(config2);

    AiAgentConfig result1 = provider.getAgentConfig(id);
    provider.evict(id);
    AiAgentConfig result2 = provider.getAgentConfig(id);

    assertEquals(config1, result1);
    assertEquals(config2, result2);
    verify(mockService, times(2)).get(id);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testGetAgentConfig_ThrowsRuntimeException_OnExecutionException() throws Exception {
    Long id = 4L;
    Exception cause = new RuntimeException("DB error");
    ExecutionException ex = new ExecutionException(cause);

    LoadingCache<Long, AiAgentConfig> mockCache = mock(LoadingCache.class);
    when(mockCache.get(id)).thenThrow(ex);
    setField(provider, "cache", mockCache);

    RuntimeException thrown
        = assertThrows(RuntimeException.class, () -> provider.getAgentConfig(id));
    assertEquals(ex, thrown.getCause());
  }
}
