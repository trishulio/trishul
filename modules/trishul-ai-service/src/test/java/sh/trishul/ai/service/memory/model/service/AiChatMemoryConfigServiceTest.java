package sh.trishul.ai.service.memory.model.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import sh.trishul.ai.memory.model.AiChatMemoryConfig;
import sh.trishul.ai.memory.model.AiChatMemoryConfigAccessor;
import sh.trishul.ai.memory.model.BaseAiChatMemoryConfig;
import sh.trishul.ai.memory.model.UpdateAiChatMemoryConfig;
import sh.trishul.crud.service.EntityMergerService;
import sh.trishul.model.base.exception.EntityNotFoundException;
import sh.trishul.model.base.pojo.DeleteResult;
import sh.trishul.repo.jpa.repository.service.RepoService;

class AiChatMemoryConfigServiceTest {

  private AiChatMemoryConfigService service;
  private EntityMergerService<Long, AiChatMemoryConfig, BaseAiChatMemoryConfig<?>, UpdateAiChatMemoryConfig<?>> mockMerger;
  private RepoService<Long, AiChatMemoryConfig, AiChatMemoryConfigAccessor<?>> mockRepoService;

  @BeforeEach
  @SuppressWarnings("unchecked")
  void setUp() {
    mockMerger = mock(EntityMergerService.class);
    mockRepoService = mock(RepoService.class);
    service = new AiChatMemoryConfigService(mockMerger, mockRepoService);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testGetChatMemoryConfigs_ReturnsPage() {
    Page<AiChatMemoryConfig> mockPage = new PageImpl<>(List.of(new AiChatMemoryConfig(1L)));
    when(mockRepoService.getAll(any(Specification.class), any(SortedSet.class), eq(true), eq(1),
        eq(10))).thenReturn(mockPage);

    Page<AiChatMemoryConfig> result
        = service.getChatMemoryConfigs(Set.of(1L), Set.of("name"), 1, 10, new TreeSet<>(), true);

    assertEquals(mockPage, result);
  }

  @Test
  void testGet_ReturnsEntity() {
    AiChatMemoryConfig entity = new AiChatMemoryConfig(1L);
    when(mockRepoService.get(1L)).thenReturn(entity);
    assertEquals(entity, service.get(1L));
  }

  @Test
  void testGetByIds_ReturnsEntities() {
    List<AiChatMemoryConfig> entities = List.of(new AiChatMemoryConfig(1L));
    when(mockRepoService.getByIds(any())).thenReturn(entities);
    assertEquals(entities, service.getByIds(List.of()));
  }

  @Test
  void testGetByAccessorIds_ReturnsEntities() {
    List<AiChatMemoryConfig> entities = List.of(new AiChatMemoryConfig(1L));
    when(mockRepoService.getByAccessorIds(any(), any())).thenAnswer(invocation -> {
      Function<AiChatMemoryConfigAccessor<?>, AiChatMemoryConfig> accessorFunction
          = invocation.getArgument(1);
      AiChatMemoryConfigAccessor<?> mockAccessor = mock(AiChatMemoryConfigAccessor.class);
      accessorFunction.apply(mockAccessor);
      return entities;
    });
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
    List<AiChatMemoryConfig> entities = List.of(new AiChatMemoryConfig(1L));
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
    List<AiChatMemoryConfig> entities = List.of(new AiChatMemoryConfig(1L));
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
    List<AiChatMemoryConfig> entities = List.of(new AiChatMemoryConfig(1L));
    List<UpdateAiChatMemoryConfig<?>> patches = List.of(mock(UpdateAiChatMemoryConfig.class));
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
    List<AiChatMemoryConfig> entities = List.of(new AiChatMemoryConfig(1L));
    List<UpdateAiChatMemoryConfig<?>> patches
        = List.of(mock(UpdateAiChatMemoryConfig.class), mock(UpdateAiChatMemoryConfig.class));
    when(mockRepoService.getByIds(patches)).thenReturn(entities);
    assertThrows(EntityNotFoundException.class, () -> service.patch(patches));
  }
}
