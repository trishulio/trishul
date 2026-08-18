package sh.trishul.ai.service.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.ai.service.skill.model.controller.AiSkillController;
import sh.trishul.ai.service.skill.model.repository.AiSkillRepository;
import sh.trishul.ai.service.skill.model.service.AiSkillService;
import sh.trishul.ai.skill.model.AiSkill;
import sh.trishul.ai.skill.model.AiSkillAccessor;
import sh.trishul.base.types.base.pojo.Refresher;
import sh.trishul.crud.controller.filter.AttributeFilter;
import sh.trishul.crud.service.LockService;
import sh.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;

class AiSkillAutoConfigurationTest {

  private AiSkillAutoConfiguration config;

  @BeforeEach
  void setUp() {
    config = new AiSkillAutoConfiguration();
  }

  @Test
  @SuppressWarnings("unchecked")
  void testAiSkillService_ReturnsNonNull() {
    LockService mockLock = mock(LockService.class);
    AiSkillRepository mockRepo = mock(AiSkillRepository.class);
    Refresher<AiSkill, AiSkillAccessor<?>> mockRefresher = mock(Refresher.class);

    AiSkillService result = config.aiSkillService(mockLock, mockRepo, mockRefresher);

    assertNotNull(result);
  }

  @Test
  void testAiSkillController_ReturnsNonNull() {
    AiSkillService mockService = mock(AiSkillService.class);
    AttributeFilter mockFilter = mock(AttributeFilter.class);

    AiSkillController result = config.aiSkillController(mockService, mockFilter);

    assertNotNull(result);
  }

  @Test
  void testAiSkillAccessorRefresher_LambdaCoverage() {
    AiSkillRepository mockRepo = mock(AiSkillRepository.class);
    AccessorRefresher<Long, AiSkillAccessor<?>, AiSkill> refresher
        = config.aiSkillAccessorRefresher(mockRepo);

    Long id = 1L;
    AiSkill skill = new AiSkill(id);
    when(mockRepo.findAllById(any())).thenReturn(List.of(skill));

    AiSkillAccessor<?> mockAccessor = mock(AiSkillAccessor.class);
    when(mockAccessor.getSkill()).thenReturn(new AiSkill(id));

    refresher.refreshAccessors(List.of(mockAccessor));

    verify(mockAccessor).setSkill(null);
    verify(mockAccessor).setSkill(skill);
    verify(mockRepo).findAllById(Set.of(id));
  }

  @Test
  @SuppressWarnings("unchecked")
  void testAiSkillRefresher_ReturnsNonNull() {
    AccessorRefresher<Long, AiSkillAccessor<?>, AiSkill> mockAccessorRefresher
        = mock(AccessorRefresher.class);

    Refresher<AiSkill, AiSkillAccessor<?>> result = config.aiSkillRefresher(mockAccessorRefresher);

    assertNotNull(result);
  }
}
