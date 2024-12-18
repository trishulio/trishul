package io.trishul.crud.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.trishul.base.types.validator.ValidationException;
import io.trishul.test.model.BaseDummyCrudEntity;
import io.trishul.test.model.DummyCrudEntity;
import io.trishul.test.model.UpdateDummyCrudEntity;
import io.trishul.test.util.MockUtilProvider;
import jakarta.persistence.OptimisticLockException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SimpleUpdateServiceTest {
    private UpdateService<Long, DummyCrudEntity, BaseDummyCrudEntity, UpdateDummyCrudEntity>
            service;
    private LockService mLockService;

    @BeforeEach
    public void init() {
        this.mLockService = new LockService(); // mock(LockService.class);

        this.service =
                new SimpleUpdateService<>(
                        new MockUtilProvider(),
                        this.mLockService,
                        BaseDummyCrudEntity.class,
                        UpdateDummyCrudEntity.class,
                        DummyCrudEntity.class,
                        Set.of(BaseDummyCrudEntity.ATTR_EXCLUDED_VALUE));
    }

    @Test
    public void testGetAddEntities_ReturnsNull_WhenAdditionsAreNull() {
        assertNull(this.service.getAddEntities(null));
    }

    @Test
    public void
            testGetAddEntities_ReturnsListOfEntitiesWithBasePropertiesOnly_WhenAdditionsAreNotNull() {
        final List<BaseDummyCrudEntity> additions =
                List.of(new DummyCrudEntity(1L, "VALUE", "EXCLUDED_VALUE", 1));

        final List<DummyCrudEntity> entities = this.service.getAddEntities(additions);

        final List<DummyCrudEntity> expected =
                List.of(new DummyCrudEntity(null, "VALUE", null, null));
        assertEquals(expected, entities);
    }

    @Test
    public void testGetPutEntities_ReturnsEmptyList_WhenUpdatesAreNull() {
        assertEquals(new ArrayList<>(), this.service.getPutEntities(null, null));
        assertEquals(new ArrayList<>(), this.service.getPutEntities(List.of(), null));
        assertEquals(
                new ArrayList<>(),
                this.service.getPutEntities(List.of(new DummyCrudEntity(1L)), null));
    }

    @Test
    public void
            testGetPutEntities_ReturnsListOfEntitiesWithUpdateProperties_WhenUpdatesAreNotNull() {
        final List<DummyCrudEntity> existing = List.of(new DummyCrudEntity(1L, null, null, 1));
        final List<UpdateDummyCrudEntity> updates =
                List.of(
                        new DummyCrudEntity(1L, "VALUE", "EXCLUDED_VALUE", 1),
                        new DummyCrudEntity(null, "VALUE", "EXCLUDED_VALUE", 1));

        final List<DummyCrudEntity> entities = this.service.getPutEntities(existing, updates);

        final List<DummyCrudEntity> expected =
                List.of(
                        new DummyCrudEntity(1L, "VALUE", null, 1),
                        new DummyCrudEntity(null, "VALUE", null, null));
        assertEquals(expected, entities);
    }

    @Test
    public void
            testGetPutEntities_ReturnsNewEntities_WhenUpdateEntityIdDoesNotExistInExistingEntities() {
        final List<DummyCrudEntity> existing = List.of(new DummyCrudEntity(1L));
        final List<UpdateDummyCrudEntity> updates =
                List.of(new DummyCrudEntity(2L), new DummyCrudEntity(3L));

        assertEquals(updates, this.service.getPutEntities(existing, updates));
        assertEquals(updates, this.service.getPutEntities(null, updates));
    }

    @Test
    public void
            testGetPutEntities_ThrowsOptimisticLockException_WhenExistingEntityVersionIsDifferentFromUpdateVersion() {
        final List<DummyCrudEntity> existing = List.of(new DummyCrudEntity(1L));
        final List<UpdateDummyCrudEntity> updates =
                List.of(
                        new DummyCrudEntity(1L, "VALUE", "EXCLUDED_VALUE", 1),
                        new DummyCrudEntity());

        assertThrows(
                OptimisticLockException.class,
                () -> this.service.getPutEntities(existing, updates),
                "Cannot update entity of version with: null with update payload of version: 1");
    }

    @Test
    public void testGetPatchEntities_ReturnsExistingEntities_WhenUpdatesAreNull() {
        assertNull(this.service.getPatchEntities(null, null));
        assertEquals(List.of(), this.service.getPatchEntities(List.of(), null));
        assertEquals(
                List.of(new DummyCrudEntity(1L)),
                this.service.getPatchEntities(List.of(new DummyCrudEntity(1L)), null));
    }

    @Test
    public void
            testGetPatchEntities_ReturnsListOfEntitiesWithUpdateProperties_WhenPatchesAreNotNull() {
        final List<DummyCrudEntity> existing =
                List.of(new DummyCrudEntity(1L, "OLD_VALUE", "OLD_EXCLUDED_VALUE", 1));
        final List<UpdateDummyCrudEntity> patches =
                List.of(new DummyCrudEntity(1L, "VALUE", "EXCLUDED_VALUE", 1));

        final List<DummyCrudEntity> entities = this.service.getPatchEntities(existing, patches);

        final List<DummyCrudEntity> expected = List.of(new DummyCrudEntity(1L, "VALUE", null, 1));
        assertEquals(expected, entities);
    }

    @Test
    public void
            testGetPatchEntities_ThrowsEntityNotFoundException_WhenUpdateEntityIdDoesNotExistInExistingEntities() {
        final List<DummyCrudEntity> existing = List.of(new DummyCrudEntity(1L));
        final List<UpdateDummyCrudEntity> patches =
                List.of(new DummyCrudEntity(2L), new DummyCrudEntity(3L));

        assertThrows(
                ValidationException.class,
                () -> this.service.getPatchEntities(existing, patches),
                "1. No existing DummyCrudEntity found with Id: 2.\n2. No existing DummyCrudEntity found with Id: 3.");
        assertThrows(
                ValidationException.class,
                () -> this.service.getPatchEntities(null, patches),
                "1. No existing DummyCrudEntity found with Id: 2.\n2. No existing DummyCrudEntity found with Id: 3.");
    }

    @Test
    public void
            testGetPatchEntities_ThrowsOptimisticLockException_WhenExistingEntityVersionIsDifferentFromUpdateVersion() {
        final List<DummyCrudEntity> existing = List.of(new DummyCrudEntity(1L));
        final List<UpdateDummyCrudEntity> patches =
                List.of(
                        new DummyCrudEntity(1L, "VALUE", "EXCLUDED_VALUE", 1),
                        new DummyCrudEntity());

        assertThrows(
                OptimisticLockException.class,
                () -> this.service.getPatchEntities(existing, patches),
                "Cannot update entity of version with: null with update payload of version: 1");
    }

    @Test
    public void testGetPatchEntities_ThrowsDuplicateKeyException_WhenMultiplePatchesHaveSameId() {
        final List<DummyCrudEntity> existing = List.of(new DummyCrudEntity(1L));
        final List<UpdateDummyCrudEntity> patchesWithSameId =
                List.of(new DummyCrudEntity(2L), new DummyCrudEntity(2L));
        final List<UpdateDummyCrudEntity> patchesWithSameNullIds =
                List.of(new DummyCrudEntity(), new DummyCrudEntity());

        assertThrows(
                IllegalStateException.class,
                () -> this.service.getPatchEntities(existing, patchesWithSameId));
        assertThrows(
                IllegalStateException.class,
                () -> this.service.getPatchEntities(existing, patchesWithSameNullIds));
    }

    @Test
    public void testGetPatchEntities_AppliesUpdateToEntityWithNullId_WhenPatchIdIsNull() {
        final List<DummyCrudEntity> existing =
                List.of(new DummyCrudEntity(null, "OLD_VALUE", "OLD_EXCLUDED_VALUE", 1));
        final List<UpdateDummyCrudEntity> patches =
                List.of(new DummyCrudEntity(null, "VALUE", "EXCLUDED_VALUE", 1));

        List<DummyCrudEntity> entities = this.service.getPatchEntities(existing, patches);

        final List<DummyCrudEntity> expected = List.of(new DummyCrudEntity(null, "VALUE", null, 1));
        assertEquals(expected, entities);
    }
}
