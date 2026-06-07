package io.trishul.ai.memory.model;

import io.trishul.base.types.base.pojo.Refresher;
import io.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AiChatMemoryConfigRefresher
    implements Refresher<AiChatMemoryConfig, AiChatMemoryConfigAccessor<?>> {
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(AiChatMemoryConfigRefresher.class);

  private final AccessorRefresher<Long, AiChatMemoryConfigAccessor<?>, AiChatMemoryConfig> refresher;

  public AiChatMemoryConfigRefresher(
      AccessorRefresher<Long, AiChatMemoryConfigAccessor<?>, AiChatMemoryConfig> refresher) {
    this.refresher = refresher;
  }

  @Override
  public void refresh(Collection<AiChatMemoryConfig> entities) {
    // No nested entities to refresh
  }

  @Override
  public void refreshAccessors(Collection<? extends AiChatMemoryConfigAccessor<?>> accessors) {
    this.refresher.refreshAccessors(accessors);
  }
}
