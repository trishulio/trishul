package io.trishul.ai.service.speech.model.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.trishul.ai.speech.model.AiSpeechConfig;
import io.trishul.ai.speech.model.AiSpeechConfigAccessor;
import io.trishul.ai.speech.model.BaseAiSpeechConfig;
import io.trishul.ai.speech.model.UpdateAiSpeechConfig;
import io.trishul.crud.service.EntityMergerService;
import io.trishul.model.base.exception.EntityNotFoundException;
import io.trishul.model.base.pojo.DeleteResult;
import io.trishul.repo.jpa.repository.service.RepoService;
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

class AiSpeechConfigServiceTest {

  private AiSpeechConfigService service;
  private EntityMergerService<Long, AiSpeechConfig, BaseAiSpeechConfig<?>, UpdateAiSpeechConfig<?>> mockMerger;
  private RepoService<Long, AiSpeechConfig, AiSpeechConfigAccessor<?>> mockRepoService;

  @BeforeEach
  @SuppressWarnings("unchecked")
  void setUp() {
    mockMerger = mock(EntityMergerService.class);
    mockRepoService = mock(RepoService.class);
    service = new AiSpeechConfigService(mockMerger, mockRepoService);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testGetSpeechConfigs_ReturnsPage() {
    Page<AiSpeechConfig> mockPage = new PageImpl<>(List.of(new AiSpeechConfig(1L)));
    when(mockRepoService.getAll(any(Specification.class), any(SortedSet.class), eq(true), eq(1),
        eq(10))).thenReturn(mockPage);

    Page<AiSpeechConfig> result = service.getSpeechConfigs(Set.of(1L), Set.of("name"),
        Set.of("openai"), true, 1, 10, new TreeSet<>(), true);

    assertEquals(mockPage, result);
  }

  @Test
  void testGet_ReturnsEntity() {
    AiSpeechConfig entity = new AiSpeechConfig(1L);
    when(mockRepoService.get(1L)).thenReturn(entity);
    assertEquals(entity, service.get(1L));
  }

  @Test
  void testGetByIds_ReturnsEntities() {
    List<AiSpeechConfig> entities = List.of(new AiSpeechConfig(1L));
    when(mockRepoService.getByIds(any())).thenReturn(entities);
    assertEquals(entities, service.getByIds(List.of()));
  }

  @Test
  void testGetByAccessorIds_ReturnsEntities() {
    List<AiSpeechConfig> entities = List.of(new AiSpeechConfig(1L));
    when(mockRepoService.getByAccessorIds(any(), any())).thenAnswer(invocation -> {
      Function<AiSpeechConfigAccessor<?>, AiSpeechConfig> accessorFunction
          = invocation.getArgument(1);
      AiSpeechConfigAccessor<?> mockAccessor = mock(AiSpeechConfigAccessor.class);
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
    List<AiSpeechConfig> entities = List.of(new AiSpeechConfig(1L));
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
    List<AiSpeechConfig> entities = List.of(new AiSpeechConfig(1L));
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
    List<AiSpeechConfig> entities = List.of(new AiSpeechConfig(1L));
    List<UpdateAiSpeechConfig<?>> patches = List.of(mock(UpdateAiSpeechConfig.class));
    when(mockRepoService.getByIds(patches)).thenReturn(entities);
    when(mockMerger.getPatchEntities(entities, patches)).thenReturn(entities);
    when(mockRepoService.saveAll(entities)).thenReturn(entities);
    assertEquals(entities, service.patch(patches));
  }

  @Test
  void testPatch_ThrowsException_WhenSizeMismatch() {
    List<AiSpeechConfig> entities = List.of();
    List<UpdateAiSpeechConfig<?>> patches = List.of(mock(UpdateAiSpeechConfig.class));
    when(mockRepoService.getByIds(patches)).thenReturn(entities);

    assertThrows(EntityNotFoundException.class, () -> service.patch(patches));
  }

  @Test
  void testPatch_ReturnsNull_WhenPatchesIsNull() {
    assertNull(service.patch(null));
  }
}
