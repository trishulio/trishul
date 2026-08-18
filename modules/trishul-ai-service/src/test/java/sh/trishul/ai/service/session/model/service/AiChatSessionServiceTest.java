package sh.trishul.ai.service.session.model.service;

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
import sh.trishul.ai.session.model.AiChatSession;
import sh.trishul.ai.session.model.AiChatSessionAccessor;
import sh.trishul.ai.session.model.BaseAiChatSession;
import sh.trishul.ai.session.model.UpdateAiChatSession;
import sh.trishul.crud.service.EntityMergerService;
import sh.trishul.model.base.exception.EntityNotFoundException;
import sh.trishul.model.base.pojo.DeleteResult;
import sh.trishul.repo.jpa.repository.service.RepoService;

class AiChatSessionServiceTest {

  private AiChatSessionService service;
  private EntityMergerService<Long, AiChatSession, BaseAiChatSession<?>, UpdateAiChatSession<?>> mockMerger;
  private RepoService<Long, AiChatSession, AiChatSessionAccessor<?>> mockRepoService;

  @BeforeEach
  @SuppressWarnings("unchecked")
  void setUp() {
    mockMerger = mock(EntityMergerService.class);
    mockRepoService = mock(RepoService.class);
    service = new AiChatSessionService(mockMerger, mockRepoService);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testGetChatSessions_ReturnsPage() {
    Page<AiChatSession> mockPage = new PageImpl<>(List.of(new AiChatSession(1L)));
    when(mockRepoService.getAll(any(Specification.class), any(SortedSet.class), eq(true), eq(1),
        eq(10))).thenReturn(mockPage);

    Page<AiChatSession> result = service.getChatSessions(Set.of(1L), Set.of("key"), Set.of("title"),
        true, Set.of(2L), 1, 10, new TreeSet<>(), true);

    assertEquals(mockPage, result);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testGetBySessionKey_ReturnsSession() {
    AiChatSession session = new AiChatSession(1L);
    session.setSessionKey("test-key");
    when(mockRepoService.getAll(any(Specification.class))).thenReturn(List.of(session));

    AiChatSession result = service.getBySessionKey("test-key");

    assertEquals(session, result);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testGetBySessionKey_ThrowsException_WhenNotFound() {
    when(mockRepoService.getAll(any(Specification.class))).thenReturn(List.of());

    assertThrows(EntityNotFoundException.class, () -> service.getBySessionKey("missing-key"));
  }

  @Test
  void testGet_ReturnsEntity() {
    AiChatSession entity = new AiChatSession(1L);
    when(mockRepoService.get(1L)).thenReturn(entity);
    assertEquals(entity, service.get(1L));
  }

  @Test
  void testGetByIds_ReturnsEntities() {
    List<AiChatSession> entities = List.of(new AiChatSession(1L));
    when(mockRepoService.getByIds(any())).thenReturn(entities);
    assertEquals(entities, service.getByIds(List.of()));
  }

  @Test
  void testGetByAccessorIds_ReturnsEntities() {
    List<AiChatSession> entities = List.of(new AiChatSession(1L));
    when(mockRepoService.getByAccessorIds(any(), any())).thenAnswer(invocation -> {
      Function<AiChatSessionAccessor<?>, AiChatSession> accessorFunction
          = invocation.getArgument(1);
      AiChatSessionAccessor<?> mockAccessor = mock(AiChatSessionAccessor.class);
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
    List<AiChatSession> entities = List.of(new AiChatSession(1L));
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
    List<AiChatSession> entities = List.of(new AiChatSession(1L));
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
    List<AiChatSession> entities = List.of(new AiChatSession(1L));
    List<UpdateAiChatSession<?>> patches = List.of(mock(UpdateAiChatSession.class));
    when(mockRepoService.getByIds(patches)).thenReturn(entities);
    when(mockMerger.getPatchEntities(entities, patches)).thenReturn(entities);
    when(mockRepoService.saveAll(entities)).thenReturn(entities);
    assertEquals(entities, service.patch(patches));
  }

  @Test
  void testPatch_ThrowsException_WhenSizeMismatch() {
    List<AiChatSession> entities = List.of();
    List<UpdateAiChatSession<?>> patches = List.of(mock(UpdateAiChatSession.class));
    when(mockRepoService.getByIds(patches)).thenReturn(entities);

    assertThrows(EntityNotFoundException.class, () -> service.patch(patches));
  }

  @Test
  void testPatch_ReturnsNull_WhenPatchesIsNull() {
    assertNull(service.patch(null));
  }
}
