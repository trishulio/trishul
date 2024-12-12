package io.company.brewcraft.repository;

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
import io.company.brewcraft.repository.AccessorRefresher;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class AccessorRefresherTest {
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
        Entity getEntity();

        void setEntity(Entity entity);
    }

    class EntityConsumer implements EntityAccessor {
        private Entity e;

        public EntityConsumer(Entity e) {
            setEntity(e);
        }

        @Override
        public Entity getEntity() {
            return e;
        }

        @Override
        public void setEntity(Entity entity) {
            this.e = entity;
        }
    }

    interface EntityRepository extends JpaRepository<Entity, Long> {
    }

    private AccessorRefresher<Long, EntityAccessor, Entity> refresher;
    private EntityRepository mRepo;

    @BeforeEach
    public void init() {
        mRepo = mock(EntityRepository.class);

        refresher = new AccessorRefresher<Long, EntityAccessor, Entity>(
            Entity.class,
            accessor -> accessor.getEntity(),
            (accessor, e) -> accessor.setEntity(e),
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
            new Entity(2L)
        );
        doReturn(repoEntities).when(mRepo).findAllById(Set.of(1L, 2L, 3L));

        List<EntityConsumer> consumers = List.of(
            new EntityConsumer(new Entity(1L)),
            new EntityConsumer(new Entity(2L)),
            new EntityConsumer(new Entity(3L))
        );

        refresher.refreshAccessors(consumers);

        assertSame(repoEntities.get(1), consumers.get(0).getEntity());
        assertSame(repoEntities.get(2), consumers.get(1).getEntity());
        assertSame(repoEntities.get(0), consumers.get(2).getEntity());
    }

    @Test
    public void testRefreshAccessors_IgnoresConsumerWithNullEntities() {
        List<Entity> repoEntities = List.of( // Unordered on purpose to capture any edge case
            new Entity(3L),
            new Entity(1L)
        );
        doReturn(repoEntities).when(mRepo).findAllById(Set.of(1L, 3L));

        List<EntityConsumer> consumers = List.of(
            new EntityConsumer(new Entity(1L)),
            new EntityConsumer(null),
            new EntityConsumer(new Entity(3L)),
            new EntityConsumer(null)
        );

        refresher.refreshAccessors(consumers);

        assertSame(repoEntities.get(1), consumers.get(0).getEntity());
        assertSame(null, consumers.get(1).getEntity());
        assertSame(repoEntities.get(0), consumers.get(2).getEntity());
        assertSame(null, consumers.get(3).getEntity());
    }

    @Test
    public void testRefreshAccessors_ThrowsException_WhenEntityRetrieverDoesNotReturnReferencedEntity() {
        List<Entity> repoEntities = List.of( // Unordered on purpose to capture any edge case
            new Entity(3L),
            new Entity(1L)
        );
        doReturn(repoEntities).when(mRepo).findAllById(Set.of(1L, 2L, 3L));

        List<EntityConsumer> consumers = List.of(
            new EntityConsumer(new Entity(1L)),
            new EntityConsumer(new Entity(2L)),
            new EntityConsumer(new Entity(3L))
        );

        assertThrows(EntityNotFoundException.class, () -> refresher.refreshAccessors(consumers), "Cannot find all objects in Id-Set: [1, 2, 3]");
    }
}
