package sh.trishul.ai.guardrail.model;

import java.util.Collection;

import sh.trishul.base.types.base.pojo.Refresher;
import sh.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;

public class AiGuardrailRefresher implements Refresher<AiGuardrail, AiGuardrailAccessor<?>> {
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
