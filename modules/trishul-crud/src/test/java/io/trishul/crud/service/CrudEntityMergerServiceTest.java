package io.trishul.crud.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.trishul.model.base.exception.ValidationException;
import io.trishul.test.model.BaseDummyCrudEntity;
import io.trishul.test.model.DummyCrudEntity;
import io.trishul.test.model.UpdateDummyCrudEntity;
import jakarta.persistence.OptimisticLockException;

public class CrudEntityMergerServiceTest {
  private EntityMergerService<Long, DummyCrudEntity, BaseDummyCrudEntity<?>, UpdateDummyCrudEntity<?>> service;
  private LockService mLockService;

  @BeforeEach
  public void init() {
    this.mLockService = new LockService();

    this.service = new CrudEntityMergerService<>(this.mLockService, BaseDummyCrudEntity.class,
        UpdateDummyCrudEntity.class, DummyCrudEntity.class,
        Set.of(BaseDummyCrudEntity.ATTR_EXCLUDED_VALUE));
  }

  @Test
  public void testGetAddEntities_ReturnsNull_WhenAdditionsAreNull() {
    assertNull(this.service.getAddEntities(null));
  }

  @Test
  public void testGetAddEntities_ReturnsListOfEntitiesWithBasePropertiesOnly_WhenAdditionsAreNotNull() {
    final List<BaseDummyCrudEntity<?>> additions = List.of(new DummyCrudEntity().setId(1L)
        .setValue("VALUE").setExcludedValue("EXCLUDED_VALUE").setVersion(1));

    final List<DummyCrudEntity> entities = this.service.getAddEntities(additions);

    final List<DummyCrudEntity> expected = List.of(new DummyCrudEntity().setValue("VALUE"));
    assertEquals(expected, entities);
  }

  @Test
  public void testGetPutEntities_ReturnsEmptyList_WhenUpdatesAreNull() {
    assertEquals(new ArrayList<>(), this.service.getPutEntities(null, null));
    assertEquals(new ArrayList<>(), this.service.getPutEntities(List.of(), null));
    assertEquals(new ArrayList<>(),
        this.service.getPutEntities(List.of(new DummyCrudEntity().setId(1L)), null));
  }

  @Test
  public void testGetPutEntities_ReturnsListOfEntitiesWithUpdateProperties_WhenUpdatesAreNotNull() {
    final List<DummyCrudEntity> existing = List.of(new DummyCrudEntity().setId(1L).setVersion(1));
    final List<UpdateDummyCrudEntity<?>> updates = List.of(
        new DummyCrudEntity().setId(1L).setValue("VALUE").setExcludedValue("EXCLUDED_VALUE")
            .setVersion(1),
        new DummyCrudEntity().setValue("VALUE").setExcludedValue("EXCLUDED_VALUE").setVersion(1));

    final List<DummyCrudEntity> entities = service.getPutEntities(existing, updates);

    final List<DummyCrudEntity> expected
        = List.of(new DummyCrudEntity().setId(1L).setValue("VALUE").setVersion(1),
            new DummyCrudEntity().setValue("VALUE"));
    assertEquals(expected, entities);
  }

  @Test
  public void testGetPutEntities_ReturnsNewEntities_WhenUpdateEntityIdDoesNotExistInExistingEntities() {
    final List<DummyCrudEntity> existing = List.of(new DummyCrudEntity().setId(1L));
    final List<UpdateDummyCrudEntity<?>> updates
        = List.of(new DummyCrudEntity().setId(2L), new DummyCrudEntity().setId(3L));

    assertEquals(updates, this.service.getPutEntities(existing, updates));
    assertEquals(updates, this.service.getPutEntities(null, updates));
  }

  @Test
  public void testGetPutEntities_ThrowsOptimisticLockException_WhenExistingEntityVersionIsDifferentFromUpdateVersion() {
    final List<DummyCrudEntity> existing = List.of(new DummyCrudEntity().setId(1L));
    final List<UpdateDummyCrudEntity<?>> updates = List.of(new DummyCrudEntity().setId(1L)
        .setValue("VALUE").setExcludedValue("EXCLUDED_VALUE").setVersion(1), new DummyCrudEntity());

    OptimisticLockException exception = assertThrows(OptimisticLockException.class,
        () -> this.service.getPutEntities(existing, updates));
    assertEquals("Cannot update entity of version: null with update payload of version: 1",
        exception.getMessage());
  }

  @Test
  public void testGetPatchEntities_ReturnsExistingEntities_WhenUpdatesAreNull() {
    assertNull(this.service.getPatchEntities(null, null));
    assertEquals(List.of(), this.service.getPatchEntities(List.of(), null));
    assertEquals(List.of(new DummyCrudEntity().setId(1L)),
        this.service.getPatchEntities(List.of(new DummyCrudEntity().setId(1L)), null));
  }

  @Test
  public void testGetPatchEntities_ReturnsListOfEntitiesWithUpdateProperties_WhenPatchesAreNotNull() {
    final List<DummyCrudEntity> existing = List.of(new DummyCrudEntity().setId(1L)
        .setValue("OLD_VALUE").setExcludedValue("OLD_EXCLUDED_VALUE").setVersion(1));
    final List<UpdateDummyCrudEntity<?>> patches = List.of(new DummyCrudEntity().setId(1L)
        .setValue("VALUE").setExcludedValue("EXCLUDED_VALUE").setVersion(1));

    final List<DummyCrudEntity> entities = this.service.getPatchEntities(existing, patches);

    final List<DummyCrudEntity> expected
        = List.of(new DummyCrudEntity().setId(1L).setValue("VALUE").setVersion(1));
    assertEquals(expected, entities);
  }

  @Test
  public void testGetPatchEntities_ThrowsEntityNotFoundException_WhenUpdateEntityIdDoesNotExistInExistingEntities() {
    final List<DummyCrudEntity> existing = List.of(new DummyCrudEntity().setId(1L));
    final List<UpdateDummyCrudEntity<?>> patches
        = List.of(new DummyCrudEntity().setId(2L), new DummyCrudEntity().setId(3L));

    ValidationException exception1 = assertThrows(ValidationException.class,
        () -> this.service.getPatchEntities(existing, patches));
    assertEquals(
        "1. Cannot apply the patch with Id: 2 to an existing entity as it does not exist\n2. Cannot apply the patch with Id: 3 to an existing entity as it does not exist\n",
        exception1.getMessage());
    ValidationException exception2 = assertThrows(ValidationException.class,
        () -> this.service.getPatchEntities(null, patches));
    assertEquals(
        "1. Cannot apply the patch with Id: 2 to an existing entity as it does not exist\n2. Cannot apply the patch with Id: 3 to an existing entity as it does not exist\n",
        exception2.getMessage());
  }

  @Test
  public void testGetPatchEntities_ThrowsOptimisticLockException_WhenExistingEntityVersionIsDifferentFromUpdateVersion() {
    final List<DummyCrudEntity> existing = List.of(new DummyCrudEntity().setId(1L));
    final List<UpdateDummyCrudEntity<?>> patches
        = List.of(new DummyCrudEntity(1L, "VALUE", "EXCLUDED_VALUE", 1), new DummyCrudEntity());

    OptimisticLockException exception = assertThrows(OptimisticLockException.class,
        () -> this.service.getPatchEntities(existing, patches));
    assertEquals("Cannot update entity of version: null with update payload of version: 1",
        exception.getMessage());
  }

  @Test
  public void testGetPatchEntities_ThrowsDuplicateKeyException_WhenMultiplePatchesHaveSameId() {
    final List<DummyCrudEntity> existing = List.of(new DummyCrudEntity().setId(1L));
    final List<UpdateDummyCrudEntity<?>> patchesWithSameId
        = List.of(new DummyCrudEntity().setId(2L), new DummyCrudEntity().setId(2L));
    final List<UpdateDummyCrudEntity<?>> patchesWithSameNullIds
        = List.of(new DummyCrudEntity(), new DummyCrudEntity());

    IllegalStateException exception1 = assertThrows(IllegalStateException.class,
        () -> this.service.getPatchEntities(existing, patchesWithSameId));
    IllegalStateException exception2 = assertThrows(IllegalStateException.class,
        () -> this.service.getPatchEntities(existing, patchesWithSameNullIds));
  }

  @Test
  public void testGetPatchEntities_AppliesUpdateToEntityWithNullId_WhenPatchIdIsNull() {
    final List<DummyCrudEntity> existing = List.of(new DummyCrudEntity(1L).setValue("OLD_VALUE")
        .setExcludedValue("OLD_EXCLUDED_VALUE").setVersion(1));
    final List<UpdateDummyCrudEntity<?>> patches = List.of(
        new DummyCrudEntity(1L).setValue("VALUE").setExcludedValue("EXCLUDED_VALUE").setVersion(1));

    List<DummyCrudEntity> entities = this.service.getPatchEntities(existing, patches);

    final List<DummyCrudEntity> expected
        = List.of(new DummyCrudEntity(1L).setValue("VALUE").setVersion(1));
    assertEquals(expected, entities);
  }
}
