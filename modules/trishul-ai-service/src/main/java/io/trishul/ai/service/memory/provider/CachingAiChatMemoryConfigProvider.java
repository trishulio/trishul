package io.trishul.ai.service.memory.provider;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.trishul.ai.memory.model.AiChatMemoryConfig;
import io.trishul.ai.service.memory.model.service.AiChatMemoryConfigService;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

/**
 * Caching implementation of AiChatMemoryConfigProvider. Analogous to
 * TenantDataSourceConfigurationProvider: uses a Guava LoadingCache keyed by Long (memoryConfigId)
 * and lazily fetches via AiChatMemoryConfigService on cache miss.
 */
public class CachingAiChatMemoryConfigProvider implements AiChatMemoryConfigProvider {
  private static final Logger log
      = LoggerFactory.getLogger(CachingAiChatMemoryConfigProvider.class);

  private final LoadingCache<Long, AiChatMemoryConfig> cache;

  public CachingAiChatMemoryConfigProvider(AiChatMemoryConfigService chatMemoryConfigService) {
    this.cache = CacheBuilder.newBuilder().build(new CacheLoader<Long, AiChatMemoryConfig>() {
      @Override
      public AiChatMemoryConfig load(@NonNull Long memoryConfigId) {
        log.debug("Loading AiChatMemoryConfig for id: {}", memoryConfigId);
        return chatMemoryConfigService.get(memoryConfigId);
      }
    });
  }

  @Override
  public AiChatMemoryConfig getChatMemoryConfig(Long memoryConfigId) {
    try {
      return this.cache.get(memoryConfigId);
    } catch (ExecutionException e) {
      throw new RuntimeException(
          String.format("Failed to load AiChatMemoryConfig for memoryConfigId: '%s' because: %s",
              memoryConfigId, e.getMessage()),
          e);
    }
  }

  public void evict(Long memoryConfigId) {
    this.cache.invalidate(memoryConfigId);
  }
}
