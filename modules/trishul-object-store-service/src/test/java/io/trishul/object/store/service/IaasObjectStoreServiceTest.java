package io.trishul.object.store.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import io.trishul.crud.service.LockService;
import io.trishul.crud.service.SimpleUpdateService;
import io.trishul.crud.service.UpdateService;
import io.trishul.iaas.repository.IaasRepository;
import io.trishul.object.store.model.BaseIaasObjectStore;
import io.trishul.object.store.model.IaasObjectStore;
import io.trishul.object.store.model.IaasObjectStoreAccessor;
import io.trishul.object.store.model.UpdateIaasObjectStore;
import io.trishul.test.util.MockUtilProvider;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IaasObjectStoreServiceTest {
    private IaasObjectStoreService service;

    private UpdateService<String, IaasObjectStore, BaseIaasObjectStore, UpdateIaasObjectStore>
            mUpdateService;
    private IaasRepository<String, IaasObjectStore, BaseIaasObjectStore, UpdateIaasObjectStore>
            mIaasRepo;
    private LockService mLockService;

    @BeforeEach
    public void init() {
        mLockService = mock(LockService.class);
        mUpdateService =
                spy(
                        new SimpleUpdateService<>(
                                new MockUtilProvider(),
                                mLockService,
                                BaseIaasObjectStore.class,
                                UpdateIaasObjectStore.class,
                                IaasObjectStore.class,
                                Set.of("createdAt")));
        mIaasRepo = mock(IaasRepository.class);

        service = new IaasObjectStoreService(mUpdateService, mIaasRepo);
    }

    @Test
    public void testExists_ReturnsTrue_WhenAllAttachmentsExists() {
        doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), true))
                .when(mIaasRepo)
                .exists(anySet());

        assertTrue(service.exists(Set.of("POLICY")));
    }

    @Test
    public void testExists_ReturnsFalse_WhenAllAttachmentsDoesNotExists() {
        doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), false))
                .when(mIaasRepo)
                .exists(anySet());

        assertFalse(service.exists(Set.of("POLICY")));
    }

    @Test
    public void testExist_ReturnsTrue_WhenAllAttachmentsExists() {
        doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), true))
                .when(mIaasRepo)
                .exists(anySet());

        assertTrue(service.exist("POLICY"));
    }

    @Test
    public void testExist_ReturnsFalse_WhenAllAttachmentsDoesNotExist() {
        doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), false))
                .when(mIaasRepo)
                .exists(anySet());

        assertFalse(service.exist("POLICY"));
    }

    @Test
    public void testDelete_Set_CallsRepoDeleteWithIds() {
        doReturn(9L).when(mIaasRepo).delete(Set.of("POLICY_1", "POLICY_2"));
        long deleteCount = service.delete(Set.of("POLICY_1", "POLICY_2"));

        assertEquals(9, deleteCount);
    }

    @Test
    public void testDelete_Id_CallsRepoDeleteWithIds() {
        doReturn(1L).when(mIaasRepo).delete(Set.of("POLICY"));

        long deleteCount = service.delete("POLICY");

        assertEquals(1, deleteCount);
    }

    @Test
    public void testGet_ReturnsAttachmentFromRepo() {
        doAnswer(
                        inv ->
                                List.of(
                                        new IaasObjectStore(
                                                (String)
                                                        inv.getArgument(0, Set.class)
                                                                .iterator()
                                                                .next())))
                .when(mIaasRepo)
                .get(anySet());

        IaasObjectStore attachment = service.get("POLICY");

        assertEquals(new IaasObjectStore("POLICY"), attachment);
    }

    @Test
    public void testGet_ReturnsNull_WhenRepoReturnsEmptyList() {
        doAnswer(inv -> List.of()).when(mIaasRepo).get(anySet());

        assertNull(service.get("POLICY"));
    }

    @Test
    public void testGetAll_ReturnsAttachmentFromRepo() {
        doAnswer(
                        inv ->
                                List.of(
                                        new IaasObjectStore(
                                                (String)
                                                        inv.getArgument(0, Set.class)
                                                                .iterator()
                                                                .next())))
                .when(mIaasRepo)
                .get(anySet());

        List<IaasObjectStore> attachments = service.getAll(Set.of("POLICY"));

        List<IaasObjectStore> expected = List.of(new IaasObjectStore("POLICY"));
        assertEquals(expected, attachments);
    }

    @Test
    public void testGetByIds_ReturnAttachmentsFromRepo() {
        doAnswer(
                        inv ->
                                List.of(
                                        new IaasObjectStore(
                                                (String)
                                                        inv.getArgument(0, Set.class)
                                                                .iterator()
                                                                .next())))
                .when(mIaasRepo)
                .get(anySet());

        List<IaasObjectStore> attachments = service.getByIds(Set.of(() -> "POLICY"));

        List<IaasObjectStore> expected = List.of(new IaasObjectStore("POLICY"));

        assertEquals(expected, attachments);
    }

    @Test
    public void testGetByAccessorIds_ReturnsAttachmentFromRepo() {
        doAnswer(
                        inv ->
                                List.of(
                                        new IaasObjectStore(
                                                (String)
                                                        inv.getArgument(0, Set.class)
                                                                .iterator()
                                                                .next())))
                .when(mIaasRepo)
                .get(anySet());

        IaasObjectStoreAccessor accessor =
                new IaasObjectStoreAccessor() {
                    @Override
                    public void setIaasObjectStore(IaasObjectStore attachment) {}

                    @Override
                    public IaasObjectStore getIaasObjectStore() {
                        return new IaasObjectStore("POLICY");
                    }
                };
        List<IaasObjectStore> attachments = service.getByAccessorIds(Set.of(accessor));

        List<IaasObjectStore> expected = List.of(new IaasObjectStore("POLICY"));
        assertEquals(expected, attachments);
    }

    @Test
    public void testAdd_ReturnsAddedRepoEntities_AfterSavingAddEntitiesFromUpdateService() {
        doAnswer(inv -> inv.getArgument(0, List.class)).when(mIaasRepo).add(anyList());

        List<BaseIaasObjectStore> additions =
                List.of(
                        new IaasObjectStore(
                                "OBJECT_STORE_1",
                                LocalDateTime.of(2000, 1, 1, 0, 0),
                                LocalDateTime.of(2001, 1, 1, 0, 0)),
                        new IaasObjectStore(
                                "OBJECT_STORE_2",
                                LocalDateTime.of(2000, 2, 1, 0, 0),
                                LocalDateTime.of(2001, 2, 1, 0, 0)));

        List<IaasObjectStore> attachments = service.add(additions);

        List<IaasObjectStore> expected =
                List.of(
                        new IaasObjectStore("OBJECT_STORE_1", null, null),
                        new IaasObjectStore("OBJECT_STORE_2", null, null));

        assertEquals(expected, attachments);
        verify(mIaasRepo, times(1)).add(attachments);
        verify(mUpdateService).getAddEntities(additions);
    }

    @Test
    public void testAdd_ReturnsNull_WhenArgIsNull() {
        assertNull(service.add(null));
    }

    @Test
    public void testPut_ReturnsPutRepoEntities_AfterSavingPutEntitiesFromUpdateService() {
        doAnswer(inv -> inv.getArgument(0, List.class)).when(mIaasRepo).put(anyList());

        List<UpdateIaasObjectStore> updates =
                List.of(
                        new IaasObjectStore(
                                "OBJECT_STORE_1",
                                LocalDateTime.of(2000, 1, 1, 0, 0),
                                LocalDateTime.of(2001, 1, 1, 0, 0)),
                        new IaasObjectStore(
                                "OBJECT_STORE_2",
                                LocalDateTime.of(2000, 2, 1, 0, 0),
                                LocalDateTime.of(2001, 2, 1, 0, 0)));

        List<IaasObjectStore> attachments = service.put(updates);

        List<IaasObjectStore> expected =
                List.of(
                        new IaasObjectStore("OBJECT_STORE_1", null, null),
                        new IaasObjectStore("OBJECT_STORE_2", null, null));

        assertEquals(expected, attachments);
        verify(mIaasRepo, times(1)).put(attachments);
        verify(mUpdateService).getPutEntities(null, updates);
    }

    @Test
    public void testPut_ReturnsNull_WhenArgIsNull() {
        assertNull(service.put(null));
    }

    @Test
    public void testPatch_ReturnsPatchRepoEntities_AfterSavingPatchEntitiesFromUpdateService() {
        doAnswer(inv -> inv.getArgument(0, List.class)).when(mIaasRepo).put(anyList());

        doAnswer(
                        inv -> {
                            Iterator<String> it = inv.getArgument(0, Set.class).iterator();
                            String id2 = it.next();
                            String id1 = it.next();

                            return List.of(
                                    new IaasObjectStore(
                                            "OBJECT_STORE_1",
                                            LocalDateTime.of(2000, 1, 1, 0, 0),
                                            LocalDateTime.of(2001, 1, 1, 0, 0)),
                                    new IaasObjectStore(
                                            "OBJECT_STORE_2",
                                            LocalDateTime.of(2000, 2, 1, 0, 0),
                                            LocalDateTime.of(2001, 2, 1, 0, 0)));
                        })
                .when(mIaasRepo)
                .get(anySet());

        List<UpdateIaasObjectStore> updates =
                List.of(
                        new IaasObjectStore(
                                "OBJECT_STORE_1",
                                LocalDateTime.of(2100, 1, 1, 0, 0),
                                LocalDateTime.of(2101, 1, 1, 0, 0)),
                        new IaasObjectStore(
                                "OBJECT_STORE_2",
                                LocalDateTime.of(2100, 2, 1, 0, 0),
                                LocalDateTime.of(2101, 2, 1, 0, 0)));

        List<IaasObjectStore> attachments = service.patch(updates);

        List<IaasObjectStore> expected =
                List.of(
                        new IaasObjectStore(
                                "OBJECT_STORE_1", null, LocalDateTime.of(2001, 1, 1, 0, 0)),
                        new IaasObjectStore(
                                "OBJECT_STORE_2", null, LocalDateTime.of(2001, 2, 1, 0, 0)));

        assertEquals(expected, attachments);
        verify(mIaasRepo, times(1)).put(attachments);
        verify(mUpdateService).getPatchEntities(anyList(), eq(updates));
    }

    @Test
    public void testPatch_ReturnsNull_WhenArgIsNull() {
        assertNull(service.patch(null));
    }
}
