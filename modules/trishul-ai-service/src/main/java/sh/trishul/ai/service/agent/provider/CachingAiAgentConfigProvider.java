package sh.trishul.ai.service.agent.provider;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import sh.trishul.ai.agent.model.AiAgentConfig;
import sh.trishul.ai.service.agent.model.service.AiAgentConfigService;

/**
 * Caching implementation of AiAgentConfigProvider. Analogous to
 * TenantDataSourceConfigurationProvider: uses a Guava LoadingCache keyed by Long (agentConfigId)
 * and lazily fetches via AiAgentConfigService on cache miss.
 */
public class CachingAiAgentConfigProvider implements AiAgentConfigProvider {
  private static final Logger log = LoggerFactory.getLogger(CachingAiAgentConfigProvider.class);

  private final LoadingCache<Long, AiAgentConfig> cache;

  public CachingAiAgentConfigProvider(AiAgentConfigService agentConfigService) {
    this.cache = CacheBuilder.newBuilder().build(new CacheLoader<Long, AiAgentConfig>() {
      @Override
      public AiAgentConfig load(@NonNull Long agentConfigId) {
        log.debug("Loading AiAgentConfig for id: {}", agentConfigId);
        return agentConfigService.get(agentConfigId);
      }
    });
  }

  @Override
  public AiAgentConfig getAgentConfig(Long agentConfigId) {
    try {
      return this.cache.get(agentConfigId);
    } catch (ExecutionException e) {
      throw new RuntimeException(
          String.format("Failed to load AiAgentConfig for agentConfigId: '%s' because: %s",
              agentConfigId, e.getMessage()),
          e);
    }
  }

  public void evict(Long agentConfigId) {
    this.cache.invalidate(agentConfigId);
  }
}
