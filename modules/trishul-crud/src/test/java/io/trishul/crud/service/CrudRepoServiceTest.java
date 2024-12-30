package io.trishul.crud.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InOrder;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import io.trishul.base.types.base.pojo.Identified;
import io.trishul.base.types.base.pojo.Refresher;
import io.trishul.repo.jpa.repository.service.RepoService;
import io.trishul.test.model.DummyCrudEntity;
import io.trishul.test.model.DummyCrudEntityAccessor;
import io.trishul.test.model.DummyCrudEntityRefresher;
import io.trishul.test.repository.DummyCrudEntityRepository;

public class CrudRepoServiceTest {
  // Hack to mock instance of type Long parameterized Identified interface
  interface LongIdentified extends Identified<Long> {
  }

  private DummyCrudEntityRepository mRepo;
  private Refresher<DummyCrudEntity, DummyCrudEntityAccessor<?>> mRefresher;
  private RepoService<Long, DummyCrudEntity, DummyCrudEntityAccessor<?>> service;

  @BeforeEach
  public void init() {
    this.mRepo = mock(DummyCrudEntityRepository.class);
    this.mRefresher = mock(DummyCrudEntityRefresher.class);
    this.service = new CrudRepoService<>(this.mRepo, this.mRefresher);
  }

  @Test
  public void testExistsByIds_ReturnsTrue_WhenRepoExistsByIdsReturnTrue() {
    doReturn(true).when(this.mRepo).existsByIds(Set.of(1L, 2L));

    assertTrue(this.service.exists(Set.of(1L, 2L)));
  }

  @Test
  public void testExistsByIds_ReturnsFalse_WhenRepoExistsByIdsReturnFalse() {
    doReturn(false).when(this.mRepo).existsByIds(Set.of(1L, 2L));

    assertFalse(this.service.exists(Set.of(1L, 2L)));
  }

  @Test
  public void testExists_ReturnsTrue_WhenRepoExistsByIdReturnTrue() {
    doReturn(true).when(this.mRepo).existsById(1L);

    assertTrue(this.service.exists(1L));
  }

  @Test
  public void testExistsById_ReturnsFalse_WhenRepoExistsByIdReturnFalse() {
    doReturn(false).when(this.mRepo).existsByIds(Set.of(1L, 2L));

    assertFalse(this.service.exists(1L));
  }

  @Test
  public void testGetAll_BuildsAPageRequestAndReturnsPageFromJpaRepository() {
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
  public void testGetAll_ReturnsListOfItemsWithMatchingSpec() {
    final Specification<DummyCrudEntity> mSpec = mock(Specification.class);
    doReturn(List.of(new DummyCrudEntity(1L))).when(this.mRepo).findAll(mSpec);

    final List<DummyCrudEntity> entities = this.service.getAll(mSpec);

    assertEquals(List.of(new DummyCrudEntity(1L)), entities);
  }

  @Test
  public void testGetByIds_ReturnsNull_WhenProvidersIsNull() {
    assertNull(this.service.getByIds(null));
  }

  @Test
  public void testGetByIds_ReturnsListOfEntitiesWithNonNullProviderIdsFromRepository_WhenProvidersIsNotNull() {
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
  public void testGetByAccessorIds_ReturnsNull_WhenAccessorsAreNull() {
    assertNull(this.service.getByAccessorIds(null, accessor -> null));
  }

  @Test
  public void testGetByAccessorIds_ReturnsListOfEntitiesWithNonNullAccessorsIdsFromRepository_WhenAccessorsAreNotNull() {
    final List<DummyCrudEntity> mEntities = List.of(new DummyCrudEntity(1L));
    doReturn(mEntities).when(this.mRepo).findAllById(Set.of(1L));

    class DummyDummyCrudEntityAccessor implements DummyCrudEntityAccessor<DummyDummyCrudEntityAccessor> {
      @Override
      public DummyCrudEntity getDummyCrudEntity() {
        return null;
      }

      @Override
      public DummyDummyCrudEntityAccessor setDummyCrudEntity(DummyCrudEntity entity) {
        return this;
      }
    }

    final List<? extends DummyCrudEntityAccessor<?>> accessors
        = List.of(new DummyDummyCrudEntityAccessor(),
            new DummyDummyCrudEntityAccessor(), new DummyDummyCrudEntityAccessor());

            accessors.add(null);

    doReturn(new DummyCrudEntity(1L)).when(accessors.get(0)).getDummyCrudEntity();
    doReturn(new DummyCrudEntity()).when(accessors.get(1)).getDummyCrudEntity();
    doReturn(null).when(accessors.get(2)).getDummyCrudEntity();

    final List<DummyCrudEntity> entities
        = this.service.getByAccessorIds(accessors, accessor -> accessor.getDummyCrudEntity());

    final List<DummyCrudEntity> expected = List.of(new DummyCrudEntity(1L));
    assertEquals(expected, entities);
  }

  @Test
  public void testGet_ReturnsNull_WhenRepoReturnsEmptyOptional() {
    doReturn(Optional.empty()).when(this.mRepo).findById(1L);

    assertNull(this.service.get(1L));
  }

  @Test
  public void testGet_ReturnsEntity_WhenRepoReturnsEntityOptional() {
    doReturn(Optional.of(new DummyCrudEntity(1L))).when(this.mRepo).findById(1L);

    assertEquals(new DummyCrudEntity(1L), this.service.get(1L));
  }

  @Test
  public void testSaveAll_CallSavesInRepositoryAndFlushes() {
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
  public void testDeleteByIds_DelegatesToRepositoryAndReturnsDeleteCount() {
    doReturn(99).when(this.mRepo).deleteByIds(Set.of(1L, 2L));

    assertEquals(99L, this.service.delete(Set.of(1L, 2L)));
  }

  @Test
  public void testDelete_ReturnsCountFromRepoDelete() {
    doReturn(1).when(mRepo).deleteOneById(1L);

    long count = this.service.delete(1L);

    assertEquals(1L, count);
  }
}
