package io.trishul.ai.skill.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import io.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;
import java.util.List;
import org.junit.jupiter.api.Test;

class AiSkillRefresherTest {

  @Test
  @SuppressWarnings("unchecked")
  void testRefresh_DoesNothing() {
    AccessorRefresher<Long, AiSkillAccessor<?>, AiSkill> mockAccessorRefresher
        = mock(AccessorRefresher.class);
    AiSkillRefresher refresher = new AiSkillRefresher(mockAccessorRefresher);

    refresher.refresh(List.of(new AiSkill(1L)));
    verifyNoInteractions(mockAccessorRefresher);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testRefreshAccessors_DelegatesToAccessorRefresher() {
    AccessorRefresher<Long, AiSkillAccessor<?>, AiSkill> mockAccessorRefresher
        = mock(AccessorRefresher.class);
    AiSkillRefresher refresher = new AiSkillRefresher(mockAccessorRefresher);

    List<AiSkillAccessor<?>> accessors = List.of(mock(AiSkillAccessor.class));
    refresher.refreshAccessors(accessors);

    verify(mockAccessorRefresher).refreshAccessors(accessors);
  }
}
