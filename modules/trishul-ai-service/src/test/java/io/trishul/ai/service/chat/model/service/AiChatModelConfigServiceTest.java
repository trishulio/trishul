package io.trishul.ai.service.chat.model.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.trishul.ai.chat.model.AiChatModelConfig;
import io.trishul.ai.chat.model.AiChatModelConfigAccessor;
import io.trishul.ai.chat.model.BaseAiChatModelConfig;
import io.trishul.ai.chat.model.UpdateAiChatModelConfig;
import io.trishul.crud.service.EntityMergerService;
import io.trishul.model.base.exception.EntityNotFoundException;
import io.trishul.repo.jpa.repository.service.RepoService;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;

class AiChatModelConfigServiceTest {

  private AiChatModelConfigService service;
  private EntityMergerService<Long, AiChatModelConfig, BaseAiChatModelConfig<?>, UpdateAiChatModelConfig<?>> mockMerger;
  private RepoService<Long, AiChatModelConfig, AiChatModelConfigAccessor<?>> mockRepoService;

  @BeforeEach
  @SuppressWarnings("unchecked")
  void setUp() {
    mockMerger = mock(EntityMergerService.class);
    mockRepoService = mock(RepoService.class);
    service = new AiChatModelConfigService(mockMerger, mockRepoService);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testGetChatModelConfigs_ReturnsPage() {
    Page<AiChatModelConfig> mockPage = new PageImpl<>(List.of(new AiChatModelConfig(1L)));
    when(mockRepoService.getAll(any(Specification.class), any(SortedSet.class), eq(true), eq(1),
        eq(10))).thenReturn(mockPage);

    Page<AiChatModelConfig> result = service.getChatModelConfigs(Set.of(1L), Set.of("name"),
        Set.of("openai"), true, 1, 10, new TreeSet<>(), true);

    assertEquals(mockPage, result);
  }

  @Test
  void testGet_ReturnsEntity() {
    AiChatModelConfig entity = new AiChatModelConfig(1L);
    when(mockRepoService.get(1L)).thenReturn(entity);

    AiChatModelConfig result = service.get(1L);

    assertEquals(entity, result);
  }

  @Test
  void testGetByIds_ReturnsEntities() {
    List<AiChatModelConfig> entities = List.of(new AiChatModelConfig(1L));
    when(mockRepoService.getByIds(any())).thenReturn(entities);
    List<AiChatModelConfig> result = service.getByIds(List.of(() -> 1L));
    assertEquals(entities, result);
  }

  @Test
  void testGetByAccessorIds_ReturnsEntities() {
    List<AiChatModelConfig> entities = List.of(new AiChatModelConfig(1L));
    when(mockRepoService.getByAccessorIds(any(), any())).thenReturn(entities);
    List<AiChatModelConfig> result = service.getByAccessorIds(List.of());
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
    List<AiChatModelConfig> entities = List.of(new AiChatModelConfig(1L));
    when(mockMerger.getAddEntities(any())).thenReturn(entities);
    when(mockRepoService.saveAll(entities)).thenReturn(entities);
    List<AiChatModelConfig> result = service.add(List.of());
    assertEquals(entities, result);
  }

  @Test
  void testAdd_ReturnsNull_WhenAdditionsIsNull() {
    assertNull(service.add(null));
  }

  @Test
  void testPut_ReturnsSavedEntities() {
    List<AiChatModelConfig> entities = List.of(new AiChatModelConfig(1L));
    when(mockRepoService.getByIds(any())).thenReturn(entities);
    when(mockMerger.getPutEntities(any(), any())).thenReturn(entities);
    when(mockRepoService.saveAll(entities)).thenReturn(entities);
    List<AiChatModelConfig> result = service.put(List.of());
    assertEquals(entities, result);
  }

  @Test
  void testPut_ReturnsNull_WhenUpdatesIsNull() {
    assertNull(service.put(null));
  }

  @Test
  void testPatch_ReturnsSavedEntities() {
    List<AiChatModelConfig> entities = List.of(new AiChatModelConfig(1L));
    List<UpdateAiChatModelConfig<?>> patches = List.of(mock(UpdateAiChatModelConfig.class));
    when(mockRepoService.getByIds(patches)).thenReturn(entities);
    when(mockMerger.getPatchEntities(entities, patches)).thenReturn(entities);
    when(mockRepoService.saveAll(entities)).thenReturn(entities);
    List<AiChatModelConfig> result = service.patch(patches);
    assertEquals(entities, result);
  }

  @Test
  void testPatch_ThrowsException_WhenSizeMismatch() {
    List<AiChatModelConfig> entities = List.of();
    List<UpdateAiChatModelConfig<?>> patches = List.of(mock(UpdateAiChatModelConfig.class));
    when(mockRepoService.getByIds(patches)).thenReturn(entities);
    assertThrows(EntityNotFoundException.class, () -> service.patch(patches));
  }

  @Test
  void testPatch_ReturnsNull_WhenPatchesIsNull() {
    assertNull(service.patch(null));
  }
}
