package sh.trishul.ai.service.skill.model.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.ai.skill.model.AiSkill;
import sh.trishul.ai.skill.model.AiSkillAccessor;
import sh.trishul.ai.skill.model.BaseAiSkill;
import sh.trishul.ai.skill.model.UpdateAiSkill;
import sh.trishul.crud.service.EntityMergerService;
import sh.trishul.model.base.exception.EntityNotFoundException;
import sh.trishul.model.base.pojo.DeleteResult;
import sh.trishul.repo.jpa.repository.service.RepoService;

class AiSkillServiceTest {

  private AiSkillService service;
  private EntityMergerService<Long, AiSkill, BaseAiSkill<?>, UpdateAiSkill<?>> mockMerger;
  private RepoService<Long, AiSkill, AiSkillAccessor<?>> mockRepoService;

  @BeforeEach
  @SuppressWarnings("unchecked")
  void setUp() {
    mockMerger = mock(EntityMergerService.class);
    mockRepoService = mock(RepoService.class);
    service = new AiSkillService(mockMerger, mockRepoService);
  }

  @Test
  void testGet_ReturnsEntity() {
    AiSkill entity = new AiSkill(1L);
    when(mockRepoService.get(1L)).thenReturn(entity);
    assertEquals(entity, service.get(1L));
  }

  @Test
  void testGetByIds_ReturnsEntities() {
    List<AiSkill> entities = List.of(new AiSkill(1L));
    when(mockRepoService.getByIds(any())).thenReturn(entities);
    assertEquals(entities, service.getByIds(List.of()));
  }

  @Test
  void testGetByAccessorIds_ReturnsEntities() {
    List<AiSkill> entities = List.of(new AiSkill(1L));
    when(mockRepoService.getByAccessorIds(any(), any())).thenReturn(entities);
    assertEquals(entities, service.getByAccessorIds(List.of()));
  }

  @Test
  void testExists_ReturnsTrue() {
    when(mockRepoService.exists(Set.of(1L))).thenReturn(true);
    assertTrue(service.exists(Set.of(1L)));
  }

  @Test
  void testExist_ReturnsTrue() {
    when(mockRepoService.exists(1L)).thenReturn(true);
    assertTrue(service.exist(1L));
  }

  @Test
  void testExists_ReturnsFalse() {
    when(mockRepoService.exists(Set.of(1L))).thenReturn(false);
    assertFalse(service.exists(Set.of(1L)));
  }

  @Test
  void testExist_ReturnsFalse() {
    when(mockRepoService.exists(1L)).thenReturn(false);
    assertFalse(service.exist(1L));
  }

  @Test
  void testDelete_ReturnsCount() {
    when(mockRepoService.delete(Set.of(1L))).thenReturn(new DeleteResult(1L));
    assertEquals(new DeleteResult(1L), service.delete(Set.of(1L)));
  }

  @Test
  void testDeleteSingle_ReturnsCount() {
    when(mockRepoService.delete(1L)).thenReturn(new DeleteResult(1L));
    assertEquals(new DeleteResult(1L), service.delete(1L));
  }

  @Test
  void testAdd_ReturnsSavedEntities() {
    List<AiSkill> entities = List.of(new AiSkill(1L));
    when(mockMerger.getAddEntities(any())).thenReturn(entities);
    when(mockRepoService.saveAll(entities)).thenReturn(entities);
    assertEquals(entities, service.add(List.of()));
  }

  @Test
  void testAdd_ReturnsNull_WhenAdditionsIsNull() {
    assertNull(service.add(null));
  }

  @Test
  void testPut_ReturnsSavedEntities() {
    List<AiSkill> entities = List.of(new AiSkill(1L));
    when(mockRepoService.getByIds(any())).thenReturn(entities);
    when(mockMerger.getPutEntities(any(), any())).thenReturn(entities);
    when(mockRepoService.saveAll(entities)).thenReturn(entities);
    assertEquals(entities, service.put(List.of()));
  }

  @Test
  void testPut_ReturnsNull_WhenUpdatesIsNull() {
    assertNull(service.put(null));
  }

  @Test
  void testPatch_ReturnsSavedEntities() {
    List<AiSkill> entities = List.of(new AiSkill(1L));
    List<UpdateAiSkill<?>> patches = List.of(mock(UpdateAiSkill.class));
    when(mockRepoService.getByIds(patches)).thenReturn(entities);
    when(mockMerger.getPatchEntities(entities, patches)).thenReturn(entities);
    when(mockRepoService.saveAll(entities)).thenReturn(entities);
    assertEquals(entities, service.patch(patches));
  }

  @Test
  void testPatch_ReturnsNull_WhenPatchesIsNull() {
    assertNull(service.patch(null));
  }

  @Test
  void testPatch_ThrowsException_WhenSizesMismatch() {
    List<AiSkill> entities = List.of(new AiSkill(1L));
    List<UpdateAiSkill<?>> patches = List.of(mock(UpdateAiSkill.class), mock(UpdateAiSkill.class));
    when(mockRepoService.getByIds(patches)).thenReturn(entities);
    assertThrows(EntityNotFoundException.class, () -> service.patch(patches));
  }
}
