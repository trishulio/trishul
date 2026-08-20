package sh.trishul.ai.memory.model;

import java.util.Collection;

import sh.trishul.base.types.base.pojo.Refresher;
import sh.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;

public class AiChatMemoryConfigRefresher
    implements Refresher<AiChatMemoryConfig, AiChatMemoryConfigAccessor<?>> {
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
