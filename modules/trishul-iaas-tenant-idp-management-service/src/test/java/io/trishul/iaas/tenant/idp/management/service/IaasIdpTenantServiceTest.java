package io.trishul.iaas.tenant.idp.management.service;

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
import io.trishul.iaas.access.role.model.IaasRole;
import io.trishul.iaas.idp.tenant.model.BaseIaasIdpTenant;
import io.trishul.iaas.idp.tenant.model.IaasIdpTenant;
import io.trishul.iaas.idp.tenant.model.IaasIdpTenantAccessor;
import io.trishul.iaas.idp.tenant.model.UpdateIaasIdpTenant;
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

public class IaasIdpTenantServiceTest {
    private IaasIdpTenantService service;

    private UpdateService<String, IaasIdpTenant, BaseIaasIdpTenant, UpdateIaasIdpTenant>
            mUpdateService;
    private IaasRepository<String, IaasIdpTenant, BaseIaasIdpTenant, UpdateIaasIdpTenant> mIaasRepo;
    private LockService mLockService;

    @BeforeEach
    public void init() {
        mLockService = mock(LockService.class);
        mUpdateService =
                spy(
                        new SimpleUpdateService<>(
                                new MockUtilProvider(),
                                mLockService,
                                BaseIaasIdpTenant.class,
                                UpdateIaasIdpTenant.class,
                                IaasIdpTenant.class,
                                Set.of("createdAt")));
        interface IdpTenantRepository
                extends IaasRepository<
                        String, IaasIdpTenant, BaseIaasIdpTenant, UpdateIaasIdpTenant> {}
        mIaasRepo = mock(IdpTenantRepository.class);

        service = new IaasIdpTenantService(mUpdateService, mIaasRepo);
    }

    @Test
    public void testExists_ReturnsTrue_WhenAllAttachmentsExists() {
        doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), true))
                .when(mIaasRepo)
                .exists(anySet());

        assertTrue(service.exists(Set.of("TENANT")));
    }

    @Test
    public void testExists_ReturnsFalse_WhenAllAttachmentsDoesNotExists() {
        doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), false))
                .when(mIaasRepo)
                .exists(anySet());

        assertFalse(service.exists(Set.of("TENANT")));
    }

    @Test
    public void testExist_ReturnsTrue_WhenAllAttachmentsExists() {
        doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), true))
                .when(mIaasRepo)
                .exists(anySet());

        assertTrue(service.exist("TENANT"));
    }

    @Test
    public void testExist_ReturnsFalse_WhenAllAttachmentsDoesNotExist() {
        doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), false))
                .when(mIaasRepo)
                .exists(anySet());

        assertFalse(service.exist("TENANT"));
    }

    @Test
    public void testDelete_Set_CallsRepoDeleteWithIds() {
        doReturn(9L).when(mIaasRepo).delete(Set.of("TENANT_1", "TENANT_2"));
        long deleteCount = service.delete(Set.of("TENANT_1", "TENANT_2"));

        assertEquals(9, deleteCount);
    }

    @Test
    public void testDelete_Id_CallsRepoDeleteWithIds() {
        doReturn(1L).when(mIaasRepo).delete(Set.of("TENANT"));

        long deleteCount = service.delete("TENANT");

        assertEquals(1, deleteCount);
    }

    @Test
    public void testGet_ReturnsAttachmentFromRepo() {
        doAnswer(
                        inv ->
                                List.of(
                                        new IaasIdpTenant(
                                                (String)
                                                        inv.getArgument(0, Set.class)
                                                                .iterator()
                                                                .next())))
                .when(mIaasRepo)
                .get(anySet());

        IaasIdpTenant attachment = service.get("TENANT");

        assertEquals(new IaasIdpTenant("TENANT"), attachment);
    }

    @Test
    public void testGet_ReturnsNull_WhenRepoReturnsEmptyList() {
        doAnswer(inv -> List.of()).when(mIaasRepo).get(anySet());

        assertNull(service.get("TENANT"));
    }

    @Test
    public void testGetAll_ReturnsAttachmentFromRepo() {
        doAnswer(
                        inv ->
                                List.of(
                                        new IaasIdpTenant(
                                                (String)
                                                        inv.getArgument(0, Set.class)
                                                                .iterator()
                                                                .next())))
                .when(mIaasRepo)
                .get(anySet());

        List<IaasIdpTenant> attachments = service.getAll(Set.of("TENANT"));

        List<IaasIdpTenant> expected = List.of(new IaasIdpTenant("TENANT"));
        assertEquals(expected, attachments);
    }

    @Test
    public void testGetByIds_ReturnAttachmentsFromRepo() {
        doAnswer(
                        inv ->
                                List.of(
                                        new IaasIdpTenant(
                                                (String)
                                                        inv.getArgument(0, Set.class)
                                                                .iterator()
                                                                .next())))
                .when(mIaasRepo)
                .get(anySet());

        List<IaasIdpTenant> attachments = service.getByIds(Set.of(() -> "TENANT"));

        List<IaasIdpTenant> expected = List.of(new IaasIdpTenant("TENANT"));

        assertEquals(expected, attachments);
    }

    @Test
    public void testGetByAccessorIds_ReturnsAttachmentFromRepo() {
        doAnswer(
                        inv ->
                                List.of(
                                        new IaasIdpTenant(
                                                (String)
                                                        inv.getArgument(0, Set.class)
                                                                .iterator()
                                                                .next())))
                .when(mIaasRepo)
                .get(anySet());

        IaasIdpTenantAccessor accessor =
                new IaasIdpTenantAccessor() {
                    @Override
                    public final void setIdpTenant(IaasIdpTenant attachment) {}

                    @Override
                    public IaasIdpTenant getIdpTenant() {
                        return new IaasIdpTenant("TENANT");
                    }
                };
        List<IaasIdpTenant> attachments = service.getByAccessorIds(Set.of(accessor));

        List<IaasIdpTenant> expected = List.of(new IaasIdpTenant("TENANT"));
        assertEquals(expected, attachments);
    }

    @Test
    public void testAdd_ReturnsAddedRepoEntities_AfterSavingAddEntitiesFromUpdateService() {
        doAnswer(inv -> inv.getArgument(0, List.class)).when(mIaasRepo).add(anyList());

        List<BaseIaasIdpTenant> additions =
                List.of(
                        new IaasIdpTenant(
                                "TENANT_1",
                                new IaasRole("ROLE_1"),
                                "DESCRIPTION_1",
                                LocalDateTime.of(2000, 1, 1, 0, 0),
                                LocalDateTime.of(2001, 1, 1, 0, 0)),
                        new IaasIdpTenant(
                                "TENANT_2",
                                new IaasRole("ROLE_2"),
                                "DESCRIPTION_2",
                                LocalDateTime.of(2000, 2, 1, 0, 0),
                                LocalDateTime.of(2001, 2, 1, 0, 0)));

        List<IaasIdpTenant> attachments = service.add(additions);

        List<IaasIdpTenant> expected =
                List.of(
                        new IaasIdpTenant(
                                "TENANT_1", new IaasRole("ROLE_1"), "DESCRIPTION_1", null, null),
                        new IaasIdpTenant(
                                "TENANT_2", new IaasRole("ROLE_2"), "DESCRIPTION_2", null, null));

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

        List<UpdateIaasIdpTenant> updates =
                List.of(
                        new IaasIdpTenant(
                                "TENANT_1",
                                new IaasRole("ROLE_1"),
                                "DESCRIPTION_1",
                                LocalDateTime.of(2000, 1, 1, 0, 0),
                                LocalDateTime.of(2001, 1, 1, 0, 0)),
                        new IaasIdpTenant(
                                "TENANT_2",
                                new IaasRole("ROLE_2"),
                                "DESCRIPTION_2",
                                LocalDateTime.of(2000, 2, 1, 0, 0),
                                LocalDateTime.of(2001, 2, 1, 0, 0)));

        List<IaasIdpTenant> attachments = service.put(updates);

        List<IaasIdpTenant> expected =
                List.of(
                        new IaasIdpTenant(
                                "TENANT_1", new IaasRole("ROLE_1"), "DESCRIPTION_1", null, null),
                        new IaasIdpTenant(
                                "TENANT_2", new IaasRole("ROLE_2"), "DESCRIPTION_2", null, null));

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
                                    new IaasIdpTenant(
                                            id1,
                                            new IaasRole("ROLE_1"),
                                            "DESCRIPTION_1",
                                            LocalDateTime.of(2000, 1, 1, 0, 0),
                                            LocalDateTime.of(2001, 1, 1, 0, 0)),
                                    new IaasIdpTenant(
                                            id2,
                                            new IaasRole("ROLE_2"),
                                            "DESCRIPTION_2",
                                            LocalDateTime.of(2000, 2, 1, 0, 0),
                                            LocalDateTime.of(2001, 2, 1, 0, 0)));
                        })
                .when(mIaasRepo)
                .get(anySet());

        List<UpdateIaasIdpTenant> updates =
                List.of(
                        new IaasIdpTenant(
                                "TENANT_1",
                                null,
                                null,
                                LocalDateTime.of(2000, 1, 1, 0, 0),
                                LocalDateTime.of(2001, 1, 1, 0, 0)),
                        new IaasIdpTenant(
                                "TENANT_2",
                                new IaasRole("ROLE_2_UPDATED"),
                                "DESCRIPTION_2_UPDATED",
                                LocalDateTime.of(2000, 2, 1, 0, 0),
                                LocalDateTime.of(2001, 2, 1, 0, 0)));

        List<IaasIdpTenant> attachments = service.patch(updates);

        List<IaasIdpTenant> expected =
                List.of(
                        new IaasIdpTenant(
                                "TENANT_1",
                                new IaasRole("ROLE_1"),
                                "DESCRIPTION_1",
                                null,
                                LocalDateTime.of(2001, 1, 1, 0, 0)),
                        new IaasIdpTenant(
                                "TENANT_2",
                                new IaasRole("ROLE_2_UPDATED"),
                                "DESCRIPTION_2_UPDATED",
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
