package sh.trishul.ai.chat.model;

import java.util.Collection;

import sh.trishul.base.types.base.pojo.Refresher;
import sh.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;

public class AiChatModelConfigRefresher
    implements Refresher<AiChatModelConfig, AiChatModelConfigAccessor<?>> {
  private final AccessorRefresher<Long, AiChatModelConfigAccessor<?>, AiChatModelConfig> refresher;

  public AiChatModelConfigRefresher(
      AccessorRefresher<Long, AiChatModelConfigAccessor<?>, AiChatModelConfig> refresher) {
    this.refresher = refresher;
  }

  @Override
  public void refresh(Collection<AiChatModelConfig> entities) {
    // No nested entities to refresh for chat model config
  }

  @Override
  public void refreshAccessors(Collection<? extends AiChatModelConfigAccessor<?>> accessors) {
    this.refresher.refreshAccessors(accessors);
  }
}
