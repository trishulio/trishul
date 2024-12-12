package io.company.brewcraft.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import io.company.brewcraft.model.Identified;
import io.company.brewcraft.repository.CollectionAccessorRefresher;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class CollectionAccessorRefresherTest {
    class Entity implements Identified<Long> {
        private Long id;

        public Entity(Long id) {
            this.id = id;
        }

        @Override
        public Long getId() {
            return id;
        }
    }

    interface EntityAccessor {
        List<Entity> getEntityList();

        void setEntityList(List<Entity> entity);
    }

    class EntityConsumer implements EntityAccessor {
        private List<Entity> e;

        public EntityConsumer(List<Entity> e) {
            setEntityList(e);
        }

        @Override
        public List<Entity> getEntityList() {
            return e;
        }

        @Override
        public void setEntityList(List<Entity> entity) {
            this.e = entity;
        }
    }

    interface EntityRepository extends JpaRepository<Entity, Long> {
    }

    private CollectionAccessorRefresher<Long, EntityAccessor, Entity> refresher;
    private EntityRepository mRepo;

    @BeforeEach
    public void init() {
        mRepo = mock(EntityRepository.class);

        refresher = new CollectionAccessorRefresher<Long, EntityAccessor, Entity>(
            Entity.class,
            accessor -> accessor.getEntityList(),
            (accessor, e) -> accessor.setEntityList(new ArrayList<Entity>(e)),
            ids -> mRepo.findAllById(ids)
        );
    }

    @Test
    public void testRefreshAccessors_DoesNothing_WhenAccessorsAreNull() {
        refresher.refreshAccessors(null);
    }

    @Test
    public void testRefreshAccessors_DoesNothing_WhenAccessorsAreEmptyCollection() {
        refresher.refreshAccessors(new ArrayList<>());
    }

    @Test
    public void testRefreshAccessors_ReplacesConsumerEntitiesWithMatchingRepoEntities_() {
        List<Entity> repoEntities = List.of( // Unordered on purpose to capture any edge case
            new Entity(3L),
            new Entity(1L),
            new Entity(2L),
            new Entity(5L),
            new Entity(4L)
        );
        doReturn(repoEntities).when(mRepo).findAllById(Set.of(1L, 2L, 3L, 4L, 5L));

        List<EntityConsumer> consumers = List.of(
            new EntityConsumer(List.of(new Entity(1L))),
            new EntityConsumer(List.of(new Entity(2L), new Entity(3L))),
            new EntityConsumer(List.of(new Entity(4L), new Entity(5L)))
        );

        refresher.refreshAccessors(consumers);

        assertEquals(1, consumers.get(0).getEntityList().size());
        assertSame(repoEntities.get(1), consumers.get(0).getEntityList().get(0));

        assertEquals(2, consumers.get(1).getEntityList().size());
        assertSame(repoEntities.get(2), consumers.get(1).getEntityList().get(0));
        assertSame(repoEntities.get(0), consumers.get(1).getEntityList().get(1));

        assertEquals(2, consumers.get(2).getEntityList().size());
        assertSame(repoEntities.get(4), consumers.get(2).getEntityList().get(0));
        assertSame(repoEntities.get(3), consumers.get(2).getEntityList().get(1));
    }

    @Test
    public void testRefreshAccessors_IgnoresConsumerWithNullEntities() {
        List<Entity> repoEntities = List.of( // Unordered on purpose to capture any edge case
            new Entity(3L),
            new Entity(1L)
        );
        doReturn(repoEntities).when(mRepo).findAllById(Set.of(1L, 3L));

        List<EntityConsumer> consumers = List.of(
            new EntityConsumer(List.of(new Entity(1L))),
            new EntityConsumer(null),
            new EntityConsumer(List.of(new Entity(3L))),
            new EntityConsumer(null)
        );

        refresher.refreshAccessors(consumers);

        assertEquals(1, consumers.get(0).getEntityList().size());
        assertSame(repoEntities.get(1), consumers.get(0).getEntityList().get(0));

        assertSame(null, consumers.get(1).getEntityList());

        assertEquals(1, consumers.get(0).getEntityList().size());
        assertSame(repoEntities.get(0), consumers.get(2).getEntityList().get(0));

        assertSame(null, consumers.get(3).getEntityList());
    }

    @Test
    public void testRefreshAccessors_IgnoresConsumerWithEntitiesContainingEmptyLists() {
        List<Entity> repoEntities = List.of( // Unordered on purpose to capture any edge case
            new Entity(3L),
            new Entity(1L)
        );
        doReturn(repoEntities).when(mRepo).findAllById(Set.of(1L, 3L));

        List<EntityConsumer> consumers = List.of(
            new EntityConsumer(List.of(new Entity(1L))),
            new EntityConsumer(List.of()),
            new EntityConsumer(List.of(new Entity(3L))),
            new EntityConsumer(List.of())
        );

        refresher.refreshAccessors(consumers);

        assertEquals(1, consumers.get(0).getEntityList().size());
        assertSame(repoEntities.get(1), consumers.get(0).getEntityList().get(0));

        assertSame(List.of(), consumers.get(1).getEntityList());

        assertEquals(1, consumers.get(0).getEntityList().size());
        assertSame(repoEntities.get(0), consumers.get(2).getEntityList().get(0));

        assertSame(List.of(), consumers.get(3).getEntityList());
    }

    @Test
    public void testRefreshAccessors_ThrowsException_WhenEntityRetrieverDoesNotReturnReferencedEntity() {
        List<Entity> repoEntities = List.of( // Unordered on purpose to capture any edge case
            new Entity(3L),
            new Entity(1L)
        );
        doReturn(repoEntities).when(mRepo).findAllById(Set.of(1L, 2L, 3L));

        List<EntityConsumer> consumers = List.of(
            new EntityConsumer(List.of(new Entity(1L))),
            new EntityConsumer(List.of(new Entity(2L))),
            new EntityConsumer(List.of(new Entity(3L)))
        );

        assertThrows(EntityNotFoundException.class, () -> refresher.refreshAccessors(consumers), "Cannot find all objects in Id-Set: [1, 2, 3]");
    }
}
