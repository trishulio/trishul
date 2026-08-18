package sh.trishul.ai.skill.model;

import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sh.trishul.base.types.base.pojo.Refresher;
import sh.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;

public class AiSkillRefresher implements Refresher<AiSkill, AiSkillAccessor<?>> {
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(AiSkillRefresher.class);

  private final AccessorRefresher<Long, AiSkillAccessor<?>, AiSkill> refresher;

  public AiSkillRefresher(AccessorRefresher<Long, AiSkillAccessor<?>, AiSkill> refresher) {
    this.refresher = refresher;
  }

  @Override
  public void refresh(Collection<AiSkill> entities) {
    // No nested entities
  }

  @Override
  public void refreshAccessors(Collection<? extends AiSkillAccessor<?>> accessors) {
    this.refresher.refreshAccessors(accessors);
  }
}
