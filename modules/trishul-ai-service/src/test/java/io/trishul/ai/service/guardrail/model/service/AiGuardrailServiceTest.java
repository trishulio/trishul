package io.trishul.ai.service.guardrail.model.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.trishul.ai.guardrail.model.AiGuardrail;
import io.trishul.ai.guardrail.model.AiGuardrailAccessor;
import io.trishul.ai.guardrail.model.BaseAiGuardrail;
import io.trishul.ai.guardrail.model.UpdateAiGuardrail;
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

class AiGuardrailServiceTest {

  private AiGuardrailService service;
  private EntityMergerService<Long, AiGuardrail, BaseAiGuardrail<?>, UpdateAiGuardrail<?>> mockMerger;
  private RepoService<Long, AiGuardrail, AiGuardrailAccessor<?>> mockRepoService;

  @BeforeEach
  @SuppressWarnings("unchecked")
  void setUp() {
    mockMerger = mock(EntityMergerService.class);
    mockRepoService = mock(RepoService.class);
    service = new AiGuardrailService(mockMerger, mockRepoService);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testGetGuardrails_ReturnsPage() {
    Page<AiGuardrail> mockPage = new PageImpl<>(List.of(new AiGuardrail(1L)));
    when(mockRepoService.getAll(any(Specification.class), any(SortedSet.class), eq(true), eq(1),
        eq(10))).thenReturn(mockPage);

    Page<AiGuardrail> result
        = service.getGuardrails(Set.of(1L), Set.of("name"), 1, 10, new TreeSet<>(), true);

    assertEquals(mockPage, result);
  }

  @Test
  void testGet_ReturnsEntity() {
    AiGuardrail entity = new AiGuardrail(1L);
    when(mockRepoService.get(1L)).thenReturn(entity);
    assertEquals(entity, service.get(1L));
  }

  @Test
  void testGetByIds_ReturnsEntities() {
    List<AiGuardrail> entities = List.of(new AiGuardrail(1L));
    when(mockRepoService.getByIds(any())).thenReturn(entities);
    assertEquals(entities, service.getByIds(List.of()));
  }

  @Test
  void testGetByAccessorIds_ReturnsEntities() {
    List<AiGuardrail> entities = List.of(new AiGuardrail(1L));
    when(mockRepoService.getByAccessorIds(any(), any())).thenAnswer(invocation -> {
      Function<AiGuardrailAccessor<?>, AiGuardrail> accessorFunction = invocation.getArgument(1);
      AiGuardrailAccessor<?> mockAccessor = mock(AiGuardrailAccessor.class);
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
    List<AiGuardrail> entities = List.of(new AiGuardrail(1L));
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
    List<AiGuardrail> entities = List.of(new AiGuardrail(1L));
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
    List<AiGuardrail> entities = List.of(new AiGuardrail(1L));
    List<UpdateAiGuardrail<?>> patches = List.of(mock(UpdateAiGuardrail.class));
    when(mockRepoService.getByIds(patches)).thenReturn(entities);
    when(mockMerger.getPatchEntities(entities, patches)).thenReturn(entities);
    when(mockRepoService.saveAll(entities)).thenReturn(entities);
    assertEquals(entities, service.patch(patches));
  }

  @Test
  void testPatch_ThrowsException_WhenSizeMismatch() {
    List<AiGuardrail> entities = List.of();
    List<UpdateAiGuardrail<?>> patches = List.of(mock(UpdateAiGuardrail.class));
    when(mockRepoService.getByIds(patches)).thenReturn(entities);

    assertThrows(EntityNotFoundException.class, () -> service.patch(patches));
  }

  @Test
  void testPatch_ReturnsNull_WhenPatchesIsNull() {
    assertNull(service.patch(null));
  }
}
