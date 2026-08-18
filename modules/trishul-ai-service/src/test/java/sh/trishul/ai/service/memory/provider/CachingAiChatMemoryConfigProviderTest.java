package sh.trishul.ai.service.memory.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import com.google.common.cache.LoadingCache;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.ai.memory.model.AiChatMemoryConfig;
import sh.trishul.ai.service.memory.model.service.AiChatMemoryConfigService;

class CachingAiChatMemoryConfigProviderTest {

  private CachingAiChatMemoryConfigProvider provider;
  private AiChatMemoryConfigService mockService;

  @BeforeEach
  void setUp() {
    mockService = mock(AiChatMemoryConfigService.class);
    provider = new CachingAiChatMemoryConfigProvider(mockService);
  }

  @Test
  void testGetChatMemoryConfig_LoadsFromServiceOnCacheMiss() {
    Long id = 1L;
    AiChatMemoryConfig config = new AiChatMemoryConfig(id);
    when(mockService.get(id)).thenReturn(config);

    AiChatMemoryConfig result = provider.getChatMemoryConfig(id);

    assertEquals(config, result);
    verify(mockService).get(id);
  }

  @Test
  void testGetChatMemoryConfig_ReturnsCachedValue_OnSecondCall() {
    Long id = 2L;
    AiChatMemoryConfig config = new AiChatMemoryConfig(id);
    when(mockService.get(id)).thenReturn(config);

    provider.getChatMemoryConfig(id);
    provider.getChatMemoryConfig(id);

    verify(mockService, times(1)).get(id);
  }

  @Test
  void testGetChatMemoryConfig_ReloadsAfterEvict() {
    Long id = 3L;
    AiChatMemoryConfig config1 = new AiChatMemoryConfig(id);
    AiChatMemoryConfig config2 = new AiChatMemoryConfig(id);
    when(mockService.get(id)).thenReturn(config1).thenReturn(config2);

    AiChatMemoryConfig result1 = provider.getChatMemoryConfig(id);
    provider.evict(id);
    AiChatMemoryConfig result2 = provider.getChatMemoryConfig(id);

    assertEquals(config1, result1);
    assertEquals(config2, result2);
    verify(mockService, times(2)).get(id);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testGetChatMemoryConfig_ThrowsRuntimeException_OnExecutionException() throws Exception {
    Long id = 4L;
    Exception cause = new RuntimeException("DB error");
    ExecutionException ex = new ExecutionException(cause);

    LoadingCache<Long, AiChatMemoryConfig> mockCache = mock(LoadingCache.class);
    when(mockCache.get(id)).thenThrow(ex);
    setField(provider, "cache", mockCache);

    RuntimeException thrown
        = assertThrows(RuntimeException.class, () -> provider.getChatMemoryConfig(id));
    assertEquals(ex, thrown.getCause());
  }
}
