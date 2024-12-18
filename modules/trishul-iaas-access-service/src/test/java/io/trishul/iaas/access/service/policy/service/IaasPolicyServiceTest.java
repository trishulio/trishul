package io.trishul.iaas.access.service.policy.service;

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
import io.trishul.iaas.access.policy.model.BaseIaasPolicy;
import io.trishul.iaas.access.policy.model.IaasPolicy;
import io.trishul.iaas.access.policy.model.IaasPolicyAccessor;
import io.trishul.iaas.access.policy.model.UpdateIaasPolicy;
import io.trishul.iaas.repository.IaasRepository;
import io.trishul.test.types.StringSet;
import io.trishul.test.util.MockUtilProvider;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IaasPolicyServiceTest {
    private IaasPolicyService service;

    private UpdateService<String, IaasPolicy, BaseIaasPolicy, UpdateIaasPolicy> mUpdateService;
    private IaasRepository<String, IaasPolicy, BaseIaasPolicy, UpdateIaasPolicy> mIaasRepo;
    private LockService mLockService;

    @BeforeEach
    public void init() {
        mLockService = mock(LockService.class);
        mUpdateService =
                spy(
                        new SimpleUpdateService<>(
                                new MockUtilProvider(),
                                this.mLockService,
                                BaseIaasPolicy.class,
                                UpdateIaasPolicy.class,
                                IaasPolicy.class,
                                Set.of("createdAt")));

        interface IaasPolicyRepository
                extends IaasRepository<String, IaasPolicy, BaseIaasPolicy, UpdateIaasPolicy> {}
        mIaasRepo = mock(IaasPolicyRepository.class);

        service = new IaasPolicyService(mUpdateService, mIaasRepo);
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
                                        new IaasPolicy(
                                                (String)
                                                        inv.getArgument(0, Set.class)
                                                                .iterator()
                                                                .next())))
                .when(mIaasRepo)
                .get(anySet());

        IaasPolicy attachment = service.get("POLICY");

        assertEquals(new IaasPolicy("POLICY"), attachment);
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
                                        new IaasPolicy(
                                                (String)
                                                        inv.getArgument(0, Set.class)
                                                                .iterator()
                                                                .next())))
                .when(mIaasRepo)
                .get(anySet());

        List<IaasPolicy> attachments = service.getAll(Set.of("POLICY"));

        List<IaasPolicy> expected = List.of(new IaasPolicy("POLICY"));
        assertEquals(expected, attachments);
    }

    @Test
    public void testGetByIds_ReturnAttachmentsFromRepo() {
        doAnswer(
                        inv ->
                                List.of(
                                        new IaasPolicy(
                                                (String)
                                                        inv.getArgument(0, Set.class)
                                                                .iterator()
                                                                .next())))
                .when(mIaasRepo)
                .get(anySet());

        List<IaasPolicy> attachments = service.getByIds(Set.of(() -> "POLICY"));

        List<IaasPolicy> expected = List.of(new IaasPolicy("POLICY"));

        assertEquals(expected, attachments);
    }

    @Test
    public void testGetByAccessorIds_ReturnsAttachmentFromRepo() {
        doAnswer(
                        inv ->
                                List.of(
                                        new IaasPolicy(
                                                (String)
                                                        inv.getArgument(0, Set.class)
                                                                .iterator()
                                                                .next())))
                .when(mIaasRepo)
                .get(anySet());

        IaasPolicyAccessor accessor =
                new IaasPolicyAccessor() {
                    @Override
                    public final void setIaasPolicy(IaasPolicy attachment) {}

                    @Override
                    public IaasPolicy getIaasPolicy() {
                        return new IaasPolicy("POLICY");
                    }
                };
        List<IaasPolicy> attachments = service.getByAccessorIds(Set.of(accessor));

        List<IaasPolicy> expected = List.of(new IaasPolicy("POLICY"));
        assertEquals(expected, attachments);
    }

    @Test
    public void testAdd_ReturnsAddedRepoEntities_AfterSavingAddEntitiesFromUpdateService() {
        doAnswer(inv -> inv.getArgument(0, List.class)).when(mIaasRepo).add(anyList());

        List<BaseIaasPolicy> additions =
                List.of(
                        new IaasPolicy(
                                "POLICY_1",
                                "DOCUMENT_1",
                                "DESCRIPTION_1",
                                "RES_NAME_1",
                                "RES_ID_1",
                                LocalDateTime.of(2000, 1, 1, 0, 0),
                                LocalDateTime.of(2001, 1, 1, 0, 0)),
                        new IaasPolicy(
                                "POLICY_2",
                                "DOCUMENT_2",
                                "DESCRIPTION_2",
                                "RES_NAME_2",
                                "RES_ID_2",
                                LocalDateTime.of(2000, 2, 1, 0, 0),
                                LocalDateTime.of(2001, 2, 1, 0, 0)));

        List<IaasPolicy> attachments = service.add(additions);

        List<IaasPolicy> expected =
                List.of(
                        new IaasPolicy(
                                "POLICY_1",
                                "DOCUMENT_1",
                                "DESCRIPTION_1",
                                "RES_NAME_1",
                                "RES_ID_1",
                                null,
                                null),
                        new IaasPolicy(
                                "POLICY_2",
                                "DOCUMENT_2",
                                "DESCRIPTION_2",
                                "RES_NAME_2",
                                "RES_ID_2",
                                null,
                                null));

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

        List<UpdateIaasPolicy> updates =
                List.of(
                        new IaasPolicy(
                                "POLICY_1",
                                "DOCUMENT_1",
                                "DESCRIPTION_1",
                                "RES_NAME_1",
                                "RES_ID_1",
                                LocalDateTime.of(2000, 1, 1, 0, 0),
                                LocalDateTime.of(2001, 1, 1, 0, 0)),
                        new IaasPolicy(
                                "POLICY_2",
                                "DOCUMENT_2",
                                "DESCRIPTION_2",
                                "RES_NAME_2",
                                "RES_ID_2",
                                LocalDateTime.of(2000, 2, 1, 0, 0),
                                LocalDateTime.of(2001, 2, 1, 0, 0)));

        List<IaasPolicy> attachments = service.put(updates);

        List<IaasPolicy> expected =
                List.of(
                        new IaasPolicy(
                                "POLICY_1",
                                "DOCUMENT_1",
                                "DESCRIPTION_1",
                                "RES_NAME_1",
                                "RES_ID_1",
                                null,
                                null),
                        new IaasPolicy(
                                "POLICY_2",
                                "DOCUMENT_2",
                                "DESCRIPTION_2",
                                "RES_NAME_2",
                                "RES_ID_2",
                                null,
                                null));

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
                            Iterator<String> it = inv.getArgument(0, StringSet.class).iterator();
                            String id2 = it.next();
                            String id1 = it.next();

                            return List.of(
                                    new IaasPolicy(
                                            id1,
                                            "DOCUMENT_1",
                                            "DESCRIPTION_1",
                                            "RES_NAME_1",
                                            "RES_ID_1",
                                            LocalDateTime.of(2000, 1, 1, 0, 0),
                                            LocalDateTime.of(2001, 1, 1, 0, 0)),
                                    new IaasPolicy(
                                            id2,
                                            "DOCUMENT_2",
                                            "DESCRIPTION_2",
                                            "RES_NAME_2",
                                            "RES_ID_2",
                                            LocalDateTime.of(2000, 2, 1, 0, 0),
                                            LocalDateTime.of(2001, 2, 1, 0, 0)));
                        })
                .when(mIaasRepo)
                .get(anySet());

        List<UpdateIaasPolicy> updates =
                List.of(
                        new IaasPolicy(
                                "POLICY_1",
                                null,
                                null,
                                "RES_NAME_1_UPDATED",
                                "RES_ID_1_UPDATED",
                                LocalDateTime.of(2000, 1, 1, 0, 0),
                                LocalDateTime.of(2001, 1, 1, 0, 0)),
                        new IaasPolicy(
                                "POLICY_2",
                                "DOCUMENT_2_UPDATED",
                                "DESCRIPTION_2_UPDATED",
                                null,
                                null,
                                LocalDateTime.of(2000, 2, 1, 0, 0),
                                LocalDateTime.of(2001, 2, 1, 0, 0)));

        List<IaasPolicy> attachments = service.patch(updates);

        List<IaasPolicy> expected =
                List.of(
                        new IaasPolicy(
                                "POLICY_1",
                                "DOCUMENT_1",
                                "DESCRIPTION_1",
                                "RES_NAME_1_UPDATED",
                                "RES_ID_1_UPDATED",
                                null,
                                LocalDateTime.of(2001, 1, 1, 0, 0)),
                        new IaasPolicy(
                                "POLICY_2",
                                "DOCUMENT_2_UPDATED",
                                "DESCRIPTION_2_UPDATED",
                                "RES_NAME_2",
                                "RES_ID_2",
                                null,
                                LocalDateTime.of(2001, 2, 1, 0, 0)));

        assertEquals(expected, attachments);
        verify(mIaasRepo, times(1)).put(attachments);
        verify(mUpdateService).getPatchEntities(anyList(), eq(updates));
    }

    @Test
    public void testPatch_ReturnsNull_WhenArgIsNull() {
        assertNull(service.patch(null));
    }
}
