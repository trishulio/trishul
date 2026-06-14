package io.trishul.ai.service.agent.model.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.trishul.ai.agent.model.AiAgentConfig;
import io.trishul.ai.agent.model.AiAgentConfigAccessor;
import io.trishul.ai.agent.model.BaseAiAgentConfig;
import io.trishul.ai.agent.model.UpdateAiAgentConfig;
import io.trishul.crud.service.EntityMergerService;
import io.trishul.model.base.exception.EntityNotFoundException;
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

class AiAgentConfigServiceTest {

  private AiAgentConfigService service;
  private EntityMergerService<Long, AiAgentConfig, BaseAiAgentConfig<?>, UpdateAiAgentConfig<?>> mockMerger;
  private RepoService<Long, AiAgentConfig, AiAgentConfigAccessor<?>> mockRepoService;

  @BeforeEach
  @SuppressWarnings("unchecked")
  void setUp() {
    mockMerger = mock(EntityMergerService.class);
    mockRepoService = mock(RepoService.class);
    service = new AiAgentConfigService(mockMerger, mockRepoService);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testGetAgentConfigs_ReturnsPage() {
    Page<AiAgentConfig> mockPage = new PageImpl<>(List.of(new AiAgentConfig(1L)));
    when(mockRepoService.getAll(any(Specification.class), any(SortedSet.class), eq(true), eq(1),
        eq(10))).thenReturn(mockPage);

    Page<AiAgentConfig> result
        = service.getAgentConfigs(Set.of(1L), Set.of("name"), true, 1, 10, new TreeSet<>(), true);

    assertEquals(mockPage, result);
  }

  @Test
  void testGet_ReturnsEntity() {
    AiAgentConfig entity = new AiAgentConfig(1L);
    when(mockRepoService.get(1L)).thenReturn(entity);

    AiAgentConfig result = service.get(1L);

    assertEquals(entity, result);
  }

  @Test
  void testGetByIds_ReturnsEntities() {
    List<AiAgentConfig> entities = List.of(new AiAgentConfig(1L));
    when(mockRepoService.getByIds(any())).thenReturn(entities);

    List<AiAgentConfig> result = service.getByIds(List.of(() -> 1L));

    assertEquals(entities, result);
  }

  @Test
  void testGetByAccessorIds_ReturnsEntities() {
    List<AiAgentConfig> entities = List.of(new AiAgentConfig(1L));
    when(mockRepoService.getByAccessorIds(any(), any())).thenAnswer(invocation -> {
      Function<AiAgentConfigAccessor<?>, AiAgentConfig> accessorFunction
          = invocation.getArgument(1);
      AiAgentConfigAccessor<?> mockAccessor = mock(AiAgentConfigAccessor.class);
      accessorFunction.apply(mockAccessor);
      return entities;
    });

    List<AiAgentConfig> result = service.getByAccessorIds(List.of());

    assertEquals(entities, result);
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
    when(mockRepoService.delete(Set.of(1L))).thenReturn(1L);
    assertEquals(1L, service.delete(Set.of(1L)));
  }

  @Test
  void testDeleteSingle_ReturnsCount() {
    when(mockRepoService.delete(1L)).thenReturn(1L);
    assertEquals(1L, service.delete(1L));
  }

  @Test
  void testAdd_ReturnsSavedEntities() {
    List<AiAgentConfig> entities = List.of(new AiAgentConfig(1L));
    when(mockMerger.getAddEntities(any())).thenReturn(entities);
    when(mockRepoService.saveAll(entities)).thenReturn(entities);

    List<AiAgentConfig> result = service.add(List.of());

    assertEquals(entities, result);
  }

  @Test
  void testAdd_ReturnsNull_WhenAdditionsIsNull() {
    assertNull(service.add(null));
  }

  @Test
  void testPut_ReturnsSavedEntities() {
    List<AiAgentConfig> entities = List.of(new AiAgentConfig(1L));
    when(mockRepoService.getByIds(any())).thenReturn(entities);
    when(mockMerger.getPutEntities(any(), any())).thenReturn(entities);
    when(mockRepoService.saveAll(entities)).thenReturn(entities);

    List<AiAgentConfig> result = service.put(List.of());

    assertEquals(entities, result);
  }

  @Test
  void testPut_ReturnsNull_WhenUpdatesIsNull() {
    assertNull(service.put(null));
  }

  @Test
  void testPatch_ReturnsSavedEntities() {
    List<AiAgentConfig> entities = List.of(new AiAgentConfig(1L));
    List<UpdateAiAgentConfig<?>> patches = List.of(mock(UpdateAiAgentConfig.class));
    when(mockRepoService.getByIds(patches)).thenReturn(entities);
    when(mockMerger.getPatchEntities(entities, patches)).thenReturn(entities);
    when(mockRepoService.saveAll(entities)).thenReturn(entities);

    List<AiAgentConfig> result = service.patch(patches);

    assertEquals(entities, result);
  }

  @Test
  void testPatch_ThrowsException_WhenSizeMismatch() {
    List<AiAgentConfig> entities = List.of();
    List<UpdateAiAgentConfig<?>> patches = List.of(mock(UpdateAiAgentConfig.class));
    when(mockRepoService.getByIds(patches)).thenReturn(entities);

    assertThrows(EntityNotFoundException.class, () -> service.patch(patches));
  }

  @Test
  void testPatch_ReturnsNull_WhenPatchesIsNull() {
    assertNull(service.patch(null));
  }
}
