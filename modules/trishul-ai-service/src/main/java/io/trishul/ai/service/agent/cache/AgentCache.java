package io.trishul.ai.service.agent.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.trishul.ai.agent.model.AiAgentConfig;
import io.trishul.ai.service.agent.factory.AgentFactory;
import java.util.concurrent.ExecutionException;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AgentCache {
  private static final Logger log = LoggerFactory.getLogger(AgentCache.class);

  private final LoadingCache<AiAgentConfig, Object> cache;

  public AgentCache(AgentFactory agentFactory) {
    this.cache = CacheBuilder.newBuilder().build(new CacheLoader<AiAgentConfig, Object>() {
      @Override
      public Object load(@Nonnull AiAgentConfig config) throws Exception {
        log.debug("Loading new agent for config id: {}", config.getId());
        return agentFactory.buildAgent(config);
      }
    });
  }

  public Object getAgent(AiAgentConfig config) {
    try {
      return this.cache.get(config);
    } catch (ExecutionException e) {
      log.error("Error loading the agent from the cache", e);
      throw new RuntimeException("Failed to load agent", e.getCause());
    }
  }

  public void evictAgent(AiAgentConfig config) {
    this.cache.invalidate(config);
  }
}
