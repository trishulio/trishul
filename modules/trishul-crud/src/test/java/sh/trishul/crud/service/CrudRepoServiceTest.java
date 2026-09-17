package sh.trishul.crud.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import sh.trishul.base.types.base.pojo.Archiveable;
import sh.trishul.base.types.base.pojo.Identified;
import sh.trishul.base.types.base.pojo.Refresher;
import sh.trishul.model.base.pojo.DeleteResult;
import sh.trishul.repo.jpa.repository.service.RepoService;
import sh.trishul.test.model.DummyCrudEntity;
import sh.trishul.test.model.DummyCrudEntityAccessor;
import sh.trishul.test.model.DummyCrudEntityRefresher;
import sh.trishul.test.repository.DummyCrudEntityRepository;

class CrudRepoServiceTest {
  // Hack to mock instance of type Long parameterized Identified interface
  interface LongIdentified extends Identified<Long> {
  }

  private DummyCrudEntityRepository mRepo;
  private Refresher<DummyCrudEntity, DummyCrudEntityAccessor<?>> mRefresher;
  private RepoService<Long, DummyCrudEntity, DummyCrudEntityAccessor<?>> service;

  @BeforeEach
  void init() {
    this.mRepo = mock(DummyCrudEntityRepository.class);
    this.mRefresher = mock(DummyCrudEntityRefresher.class);
    this.service = new CrudRepoService<>(this.mRepo, this.mRefresher);
  }

  @Test
  void testExistsByIds_ReturnsTrue_WhenRepoExistsByIdsReturnTrue() {
    doReturn(true).when(this.mRepo).existsByIds(Set.of(1L, 2L));

    assertTrue(this.service.exists(Set.of(1L, 2L)));
  }

  @Test
  void testExistsByIds_ReturnsFalse_WhenRepoExistsByIdsReturnFalse() {
    doReturn(false).when(this.mRepo).existsByIds(Set.of(1L, 2L));

    assertFalse(this.service.exists(Set.of(1L, 2L)));
  }

  @Test
  void testExists_ReturnsTrue_WhenRepoExistsByIdReturnTrue() {
    doReturn(true).when(this.mRepo).existsById(1L);

    assertTrue(this.service.exists(1L));
  }

  @Test
  void testExistsById_ReturnsFalse_WhenRepoExistsByIdReturnFalse() {
    doReturn(false).when(this.mRepo).existsByIds(Set.of(1L, 2L));

    assertFalse(this.service.exists(1L));
  }

  @Test
  void testGetAll_BuildsAPageRequestAndReturnsPageFromJpaRepository() {
    final Page<DummyCrudEntity> mPage = new PageImpl<>(List.of(new DummyCrudEntity(1L)));

    final Specification<DummyCrudEntity> mSpec = mock(Specification.class);
    final PageRequest expectedPageRequest
        = PageRequest.of(1, 100, Direction.DESC, "col_1", "col_2");
    doReturn(mPage).when(this.mRepo).findAll(mSpec, expectedPageRequest);

    final Page<DummyCrudEntity> page
        = this.service.getAll(mSpec, new TreeSet<>(List.of("col_1", "col_2")), false, 1, 100);

    final Page<DummyCrudEntity> expected = new PageImpl<>(List.of(new DummyCrudEntity(1L)));
    assertEquals(expected, page);
  }

  @Test
  void testGetAll_ReturnsListOfItemsWithMatchingSpec() {
    final Specification<DummyCrudEntity> mSpec = mock(Specification.class);
    doReturn(List.of(new DummyCrudEntity(1L))).when(this.mRepo).findAll(mSpec);

    final List<DummyCrudEntity> entities = this.service.getAll(mSpec);

    assertEquals(List.of(new DummyCrudEntity(1L)), entities);
  }

  @Test
  void testGetByIds_ReturnsEmptyList_WhenProvidersIsNull() {
    assertEquals(Collections.emptyList(), this.service.getByIds(null));
  }

  @Test
  void testGetByIds_ReturnsListOfEntitiesWithNonNullProviderIdsFromRepository_WhenProvidersIsNotNull() {
    final List<DummyCrudEntity> mEntities = List.of(new DummyCrudEntity(1L));
    doReturn(mEntities).when(this.mRepo).findAllById(Set.of(1L));

    final List<? extends Identified<Long>> idProviders
        = new ArrayList<>(List.of(mock(LongIdentified.class), mock(LongIdentified.class)));
    idProviders.add(null);

    doReturn(1L).when(idProviders.get(0)).getId();

    final List<DummyCrudEntity> entities = this.service.getByIds(idProviders);

    final List<DummyCrudEntity> expected = List.of(new DummyCrudEntity(1L));
    assertEquals(expected, entities);
  }

  @Test
  void testGetByAccessorIds_ReturnsEmptyList_WhenAccessorsAreNull() {
    assertEquals(Collections.emptyList(), this.service.getByAccessorIds(null, accessor -> null));
  }

  @Test
  void testGetByAccessorIds_ReturnsListOfEntitiesWithNonNullAccessorsIdsFromRepository_WhenAccessorsAreNotNull() {
    final List<DummyCrudEntity> mEntities = List.of(new DummyCrudEntity(1L));
    doReturn(mEntities).when(this.mRepo).findAllById(Set.of(1L));

    class DummyDummyCrudEntityAccessor
        implements DummyCrudEntityAccessor<DummyDummyCrudEntityAccessor> {
      private final DummyCrudEntity entity;

      public DummyDummyCrudEntityAccessor(Long crudEntityId) {
        this(new DummyCrudEntity(crudEntityId));
      }

      public DummyDummyCrudEntityAccessor(DummyCrudEntity entity) {
        this.entity = entity;
      }

      @Override
      public DummyCrudEntity getDummyCrudEntity() {
        return this.entity;
      }

      @Override
      public DummyDummyCrudEntityAccessor setDummyCrudEntity(DummyCrudEntity entity) {
        return this;
      }
    }

    final List<? extends DummyCrudEntityAccessor<?>> accessors
        = new ArrayList<>(List.of(new DummyDummyCrudEntityAccessor(1L),
            new DummyDummyCrudEntityAccessor(new DummyCrudEntity()),
            new DummyDummyCrudEntityAccessor((DummyCrudEntity) null)));

    accessors.add(null);

    final List<DummyCrudEntity> entities
        = this.service.getByAccessorIds(accessors, accessor -> accessor.getDummyCrudEntity());

    final List<DummyCrudEntity> expected = List.of(new DummyCrudEntity(1L));
    assertEquals(expected, entities);
  }

  @Test
  void testGet_ReturnsNull_WhenRepoReturnsEmptyOptional() {
    doReturn(Optional.empty()).when(this.mRepo).findById(1L);

    assertNull(this.service.get(1L));
  }

  @Test
  void testGet_ReturnsEntity_WhenRepoReturnsEntityOptional() {
    doReturn(Optional.of(new DummyCrudEntity(1L))).when(this.mRepo).findById(1L);

    assertEquals(new DummyCrudEntity(1L), this.service.get(1L));
  }

  @Test
  void testSaveAll_CallSavesInRepositoryAndFlushes() {
    doAnswer(inv -> inv.getArgument(0)).when(this.mRepo).saveAll(any());

    final List<DummyCrudEntity> mEntities = List.of(new DummyCrudEntity(1L));
    final List<DummyCrudEntity> entities = this.service.saveAll(mEntities);

    assertEquals(List.of(new DummyCrudEntity(1L)), entities);

    final InOrder order = inOrder(this.mRefresher, this.mRepo);
    order.verify(this.mRefresher, times(1)).refresh(List.of(new DummyCrudEntity(1L)));
    order.verify(this.mRepo, times(1)).saveAll(List.of(new DummyCrudEntity(1L)));
    order.verify(this.mRepo, times(1)).flush();
  }

  @Test
  void testDeleteByIds_DelegatesToRepositoryAndReturnsDeleteCount() {
    doReturn(99).when(this.mRepo).deleteByIds(Set.of(1L, 2L));

    assertEquals(new DeleteResult(99L), this.service.delete(Set.of(1L, 2L)));
  }

  @Test
  void testDelete_ReturnsCountFromRepoDelete() {
    doReturn(1).when(mRepo).deleteOneById(1L);

    DeleteResult count = this.service.delete(1L);

    assertEquals(new DeleteResult(1L), count);
  }

  @Test
  void testArchive_WhenIdsNull_ReturnsZero() {
    DeleteResult result = this.service.archive((Set<Long>) null);

    assertEquals(new DeleteResult(0L), result);
    verify(this.mRepo, never()).findAllById(any());
  }

  @Test
  void testArchive_WhenIdsEmpty_ReturnsZero() {
    DeleteResult result = this.service.archive(Set.of());

    assertEquals(new DeleteResult(0L), result);
    verify(this.mRepo, never()).findAllById(any());
  }

  @Test
  void testArchive_WhenEntitiesAreArchiveable_SetsArchivedAndSavesAndReturnsCount() {
    DummyArchiveableEntity e1 = new DummyArchiveableEntity(1L);
    DummyArchiveableEntity e2 = new DummyArchiveableEntity(2L);
    List<DummyCrudEntity> entities = List.of(e1, e2);

    doReturn(entities).when(this.mRepo).findAllById(Set.of(1L, 2L));

    DeleteResult result = this.service.archive(Set.of(1L, 2L));

    assertEquals(new DeleteResult(2L), result);
    assertTrue(e1.isArchived());
    assertEquals(Boolean.TRUE, e1.getArchived());
    assertTrue(e2.isArchived());
    assertEquals("archived", Archiveable.ATTR_ARCHIVED);
    verify(this.mRepo).saveAll(entities);
    verify(this.mRepo).flush();
  }

  @Test
  void testArchive_WhenEntitiesNotArchiveable_DoesNotSaveAndReturnsZero() {
    DummyCrudEntity e1 = new DummyCrudEntity(1L);
    List<DummyCrudEntity> entities = List.of(e1);

    doReturn(entities).when(this.mRepo).findAllById(Set.of(1L));

    DeleteResult result = this.service.archive(Set.of(1L));

    assertEquals(new DeleteResult(0L), result);
    verify(this.mRepo, never()).saveAll(any());
    verify(this.mRepo, never()).flush();
  }

  @Test
  void testArchive_SingleId_WhenIdNull_ReturnsZero() {
    DeleteResult result = this.service.archive((Long) null);

    assertEquals(new DeleteResult(0L), result);
  }

  @Test
  void testArchive_SingleId_DelegatesToSet() {
    DummyArchiveableEntity e1 = new DummyArchiveableEntity(1L);
    doReturn(List.of(e1)).when(this.mRepo).findAllById(Set.of(1L));

    DeleteResult result = this.service.archive(1L);

    assertTrue(e1.isArchived());
    verify(this.mRepo).saveAll(List.of(e1));
    verify(this.mRepo).flush();
    assertEquals(new DeleteResult(1L), result);
  }

  @Test
  void testSearch_WhenQueryIsNull_ReturnsPageFromRepository() {
    ArgumentCaptor<Specification<DummyCrudEntity>> specCaptor
        = ArgumentCaptor.forClass(Specification.class);
    ArgumentCaptor<PageRequest> pageCaptor = ArgumentCaptor.forClass(PageRequest.class);
    Page<DummyCrudEntity> expectedPage = new PageImpl<>(List.of(new DummyCrudEntity(1L)));
    doReturn(expectedPage).when(this.mRepo).findAll(specCaptor.capture(), pageCaptor.capture());

    Page<DummyCrudEntity> result = this.service.search(null, new String[][] {{"name"}},
        new TreeSet<>(List.of("id")), true, 0, 10);

    assertEquals(expectedPage, result);
    assertEquals(PageRequest.of(0, 10, Direction.ASC, "id"), pageCaptor.getValue());

    Root<DummyCrudEntity> mRoot = mock(Root.class);
    CriteriaQuery<?> mQuery = mock(CriteriaQuery.class);
    CriteriaBuilder mCb = mock(CriteriaBuilder.class);
    Predicate mAndPred = mock(Predicate.class);
    doReturn(mAndPred).when(mCb).and(any(Predicate[].class));

    Predicate pred = specCaptor.getValue().toPredicate(mRoot, mQuery, mCb);
    assertNotNull(pred);
    verify(mCb, never()).like(any(), anyString());
  }

  @Test
  void testSearch_WhenQueryIsBlank_ReturnsPageFromRepository() {
    ArgumentCaptor<Specification<DummyCrudEntity>> specCaptor
        = ArgumentCaptor.forClass(Specification.class);
    Page<DummyCrudEntity> expectedPage = new PageImpl<>(List.of(new DummyCrudEntity(1L)));
    doReturn(expectedPage).when(this.mRepo).findAll(specCaptor.capture(), any(PageRequest.class));

    Page<DummyCrudEntity> result = this.service.search("   ", new String[][] {{"name"}},
        new TreeSet<>(List.of("id")), true, 0, 10);

    assertEquals(expectedPage, result);

    Root<DummyCrudEntity> mRoot = mock(Root.class);
    CriteriaQuery<?> mQuery = mock(CriteriaQuery.class);
    CriteriaBuilder mCb = mock(CriteriaBuilder.class);
    Predicate mAndPred = mock(Predicate.class);
    doReturn(mAndPred).when(mCb).and(any(Predicate[].class));

    Predicate pred = specCaptor.getValue().toPredicate(mRoot, mQuery, mCb);
    assertNotNull(pred);
    verify(mCb, never()).like(any(), anyString());
  }

  @Test
  @SuppressWarnings({"unchecked", "rawtypes"})
  void testSearch_WhenQueryHasMultipleTermsAndMultiplePaths_BuildsCompoundSpecification() {
    ArgumentCaptor<Specification<DummyCrudEntity>> specCaptor
        = ArgumentCaptor.forClass(Specification.class);
    ArgumentCaptor<PageRequest> pageCaptor = ArgumentCaptor.forClass(PageRequest.class);
    Page<DummyCrudEntity> expectedPage = new PageImpl<>(List.of(new DummyCrudEntity(1L)));
    doReturn(expectedPage).when(this.mRepo).findAll(specCaptor.capture(), pageCaptor.capture());

    Page<DummyCrudEntity> result = this.service.search("  term1 term2  ",
        new String[][] {{"value"}, {"excludedValue"}}, new TreeSet<>(List.of("id")), false, 1, 10);

    assertEquals(expectedPage, result);
    assertEquals(PageRequest.of(1, 10, Direction.DESC, "id"), pageCaptor.getValue());

    Root<DummyCrudEntity> mRoot = mock(Root.class);
    doReturn(DummyCrudEntity.class).when(mRoot).getJavaType();
    CriteriaQuery<?> mQuery = mock(CriteriaQuery.class);
    CriteriaBuilder mCb = mock(CriteriaBuilder.class);
    Path mPath = mock(Path.class);
    Expression<String> mLowerExpr = mock(Expression.class);
    Predicate mLikePred = mock(Predicate.class);
    Predicate mOrPred = mock(Predicate.class);
    Predicate mAndPred = mock(Predicate.class);

    doReturn(mPath).when(mRoot).get(any(String.class));
    doReturn(mLowerExpr).when(mCb).lower(any());
    doReturn(mLikePred).when(mCb).like(any(), anyString());
    doReturn(mOrPred).when(mCb).or(any(Predicate.class), any(Predicate.class));
    doReturn(mAndPred).when(mCb).and(any(Predicate.class), any(Predicate.class));
    doReturn(mAndPred).when(mCb).and(any(Predicate[].class));

    Predicate pred = specCaptor.getValue().toPredicate(mRoot, mQuery, mCb);
    assertNotNull(pred);
    verify(mCb, times(2)).like(any(), eq("%term1%"));
    verify(mCb, times(2)).like(any(), eq("%term2%"));
    verify(mCb, never()).like(any(), eq("%%"));
  }

  @Test
  @SuppressWarnings({"unchecked", "rawtypes"})
  void testSearch_WhenSinglePath_BuildsSpecification() {
    ArgumentCaptor<Specification<DummyCrudEntity>> specCaptor
        = ArgumentCaptor.forClass(Specification.class);
    Page<DummyCrudEntity> expectedPage = new PageImpl<>(List.of(new DummyCrudEntity(1L)));
    doReturn(expectedPage).when(this.mRepo).findAll(specCaptor.capture(), any(PageRequest.class));

    Page<DummyCrudEntity> result = this.service.search("term1", new String[][] {{"value"}},
        new TreeSet<>(List.of("id")), true, 0, 10);

    assertEquals(expectedPage, result);

    Root<DummyCrudEntity> mRoot = mock(Root.class);
    doReturn(DummyCrudEntity.class).when(mRoot).getJavaType();
    CriteriaQuery<?> mQuery = mock(CriteriaQuery.class);
    CriteriaBuilder mCb = mock(CriteriaBuilder.class);
    Path mPath = mock(Path.class);
    Expression<String> mLowerExpr = mock(Expression.class);
    Predicate mLikePred = mock(Predicate.class);
    Predicate mAndPred = mock(Predicate.class);

    doReturn(mPath).when(mRoot).get(any(String.class));
    doReturn(mLowerExpr).when(mCb).lower(any());
    doReturn(mLikePred).when(mCb).like(any(), anyString());
    doReturn(mAndPred).when(mCb).and(any(Predicate.class), any(Predicate.class));
    doReturn(mAndPred).when(mCb).and(any(Predicate[].class));

    Predicate pred = specCaptor.getValue().toPredicate(mRoot, mQuery, mCb);
    assertNotNull(pred);
    verify(mCb, times(1)).like(any(), eq("%term1%"));
  }

  private static class DummyArchiveableEntity extends DummyCrudEntity implements Archiveable {
    private Boolean archived;

    public DummyArchiveableEntity(Long id) {
      super(id);
    }

    @Override
    public Boolean isArchived() {
      return archived;
    }

    @Override
    public void setArchived(Boolean archived) {
      this.archived = archived;
    }
  }
}
