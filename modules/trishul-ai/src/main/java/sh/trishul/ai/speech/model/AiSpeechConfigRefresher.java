package sh.trishul.ai.speech.model;

import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sh.trishul.base.types.base.pojo.Refresher;
import sh.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;

public class AiSpeechConfigRefresher
    implements Refresher<AiSpeechConfig, AiSpeechConfigAccessor<?>> {
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(AiSpeechConfigRefresher.class);

  private final AccessorRefresher<Long, AiSpeechConfigAccessor<?>, AiSpeechConfig> refresher;

  public AiSpeechConfigRefresher(
      AccessorRefresher<Long, AiSpeechConfigAccessor<?>, AiSpeechConfig> refresher) {
    this.refresher = refresher;
  }

  @Override
  public void refresh(Collection<AiSpeechConfig> entities) {
    // No nested entities
  }

  @Override
  public void refreshAccessors(Collection<? extends AiSpeechConfigAccessor<?>> accessors) {
    this.refresher.refreshAccessors(accessors);
  }
}
