package sh.trishul.ai.chat.model;

import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sh.trishul.base.types.base.pojo.Refresher;
import sh.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;

public class AiChatModelConfigRefresher
    implements Refresher<AiChatModelConfig, AiChatModelConfigAccessor<?>> {
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(AiChatModelConfigRefresher.class);

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
