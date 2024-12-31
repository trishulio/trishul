package io.trishul.model.base.pojo.refresher.accessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import io.trishul.base.types.base.pojo.Identified;
import io.trishul.model.base.exception.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccessorRefresherTest {
  class Entity implements Identified<Long> {
    private final Long id;

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
    public final void setEntity(Entity entity) {
      this.e = entity;
    }
  }

  private AccessorRefresher<Long, EntityAccessor, Entity> refresher;

  private Function<Iterable<Long>, List<Entity>> mEntityRetriever;

  @BeforeEach
  public void init() {
    mEntityRetriever = mock(Function.class);

    refresher = new AccessorRefresher<>(Entity.class, accessor -> accessor.getEntity(),
        (accessor, e) -> accessor.setEntity(e), mEntityRetriever);
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
        new Entity(3L), new Entity(1L), new Entity(2L));
    doReturn(repoEntities).when(mEntityRetriever).apply(Set.of(1L, 2L, 3L));

    List<EntityConsumer> consumers = List.of(new EntityConsumer(new Entity(1L)),
        new EntityConsumer(new Entity(2L)), new EntityConsumer(new Entity(3L)));

    refresher.refreshAccessors(consumers);

    assertSame(repoEntities.get(1), consumers.get(0).getEntity());
    assertSame(repoEntities.get(2), consumers.get(1).getEntity());
    assertSame(repoEntities.get(0), consumers.get(2).getEntity());
  }

  @Test
  public void testRefreshAccessors_IgnoresConsumerWithNullEntities() {
    List<Entity> repoEntities = List.of( // Unordered on purpose to capture any edge case
        new Entity(3L), new Entity(1L));
    doReturn(repoEntities).when(mEntityRetriever).apply(Set.of(1L, 3L));

    List<EntityConsumer> consumers = List.of(new EntityConsumer(new Entity(1L)),
        new EntityConsumer(null), new EntityConsumer(new Entity(3L)), new EntityConsumer(null));

    refresher.refreshAccessors(consumers);

    assertSame(repoEntities.get(1), consumers.get(0).getEntity());
    assertSame(null, consumers.get(1).getEntity());
    assertSame(repoEntities.get(0), consumers.get(2).getEntity());
    assertSame(null, consumers.get(3).getEntity());
  }

  @Test
  public void testRefreshAccessors_ThrowsException_WhenEntityRetrieverDoesNotReturnReferencedEntity() {
    List<Entity> repoEntities = List.of( // Unordered on purpose to capture any edge case
        new Entity(3L), new Entity(1L));
    doReturn(repoEntities).when(mEntityRetriever).apply(Set.of(1L, 2L, 3L));

    List<EntityConsumer> consumers = List.of(new EntityConsumer(new Entity(1L)),
        new EntityConsumer(new Entity(2L)), new EntityConsumer(new Entity(3L)));

    EntityNotFoundException exception
        = assertThrows(EntityNotFoundException.class, () -> refresher.refreshAccessors(consumers));

    assertEquals(
        "Cannot find all Entitys in Id-Set: [1, 2, 3]. Only found the ones with Ids: [3, 1]",
        exception.getMessage());
  }
}
