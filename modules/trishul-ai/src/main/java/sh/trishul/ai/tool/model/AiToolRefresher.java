package sh.trishul.ai.tool.model;

import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sh.trishul.base.types.base.pojo.Refresher;
import sh.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;

public class AiToolRefresher implements Refresher<AiTool, AiToolAccessor<?>> {
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(AiToolRefresher.class);

  private final AccessorRefresher<Long, AiToolAccessor<?>, AiTool> refresher;

  public AiToolRefresher(AccessorRefresher<Long, AiToolAccessor<?>, AiTool> refresher) {
    this.refresher = refresher;
  }

  @Override
  public void refresh(Collection<AiTool> entities) {
    // No nested entities
  }

  @Override
  public void refreshAccessors(Collection<? extends AiToolAccessor<?>> accessors) {
    this.refresher.refreshAccessors(accessors);
  }
}
