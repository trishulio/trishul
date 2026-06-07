package io.trishul.ai.guardrail.model;

import io.trishul.base.types.base.pojo.Refresher;
import io.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AiGuardrailRefresher implements Refresher<AiGuardrail, AiGuardrailAccessor<?>> {
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(AiGuardrailRefresher.class);

  private final AccessorRefresher<Long, AiGuardrailAccessor<?>, AiGuardrail> refresher;

  public AiGuardrailRefresher(
      AccessorRefresher<Long, AiGuardrailAccessor<?>, AiGuardrail> refresher) {
    this.refresher = refresher;
  }

  @Override
  public void refresh(Collection<AiGuardrail> entities) {
    // No nested entities to refresh
  }

  @Override
  public void refreshAccessors(Collection<? extends AiGuardrailAccessor<?>> accessors) {
    this.refresher.refreshAccessors(accessors);
  }
}
