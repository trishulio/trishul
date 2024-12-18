package io.trishul.iaas.access.service.role.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.crud.service.LockService;
import io.trishul.crud.service.SimpleUpdateService;
import io.trishul.crud.service.UpdateService;
import io.trishul.iaas.access.role.model.BaseIaasRole;
import io.trishul.iaas.access.role.model.IaasRole;
import io.trishul.iaas.access.role.model.IaasRoleAccessor;
import io.trishul.iaas.access.role.model.UpdateIaasRole;
import io.trishul.iaas.repository.IaasRepository;
import io.trishul.test.util.MockUtilProvider;

public class IaasRoleServiceTest {
    private IaasRoleService service;
    private LockService mLockService;

    private UpdateService<String, IaasRole, BaseIaasRole, UpdateIaasRole> mUpdateService;

    private IaasRepository<String, IaasRole, BaseIaasRole, UpdateIaasRole> mIaasRepo;

    @BeforeEach
    public void init() {
        mLockService = mock(LockService.class);
        mUpdateService = spy(new SimpleUpdateService<>(new MockUtilProvider(), mLockService, BaseIaasRole.class, UpdateIaasRole.class, IaasRole.class, Set.of("createdAt")));
        mIaasRepo = mock(IaasRepository.class);

        service = new IaasRoleService(mUpdateService, mIaasRepo);
    }

    @Test
    public void testExists_ReturnsTrue_WhenAllAttachmentsExists() {
        doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), true)).when(mIaasRepo).exists(anySet());

        assertTrue(service.exists(Set.of("TENANT")));
    }

    @Test
    public void testExists_ReturnsFalse_WhenAllAttachmentsDoesNotExists() {
        doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), false)).when(mIaasRepo).exists(anySet());

        assertFalse(service.exists(Set.of("TENANT")));
    }

    @Test
    public void testExist_ReturnsTrue_WhenAllAttachmentsExists() {
        doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), true)).when(mIaasRepo).exists(anySet());

        assertTrue(service.exist("TENANT"));
    }

    @Test
    public void testExist_ReturnsFalse_WhenAllAttachmentsDoesNotExist() {
        doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), false)).when(mIaasRepo).exists(anySet());

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
        doAnswer(inv -> List.of(new IaasRole((String) inv.getArgument(0, Set.class).iterator().next()))).when(mIaasRepo).get(anySet());

        IaasRole attachment = service.get("TENANT");

        assertEquals(new IaasRole("TENANT"), attachment);
    }

    @Test
    public void testGet_ReturnsNull_WhenRepoReturnsEmptyList() {
        doAnswer(inv -> List.of()).when(mIaasRepo).get(anySet());

        assertNull(service.get("TENANT"));
    }

    @Test
    public void testGetAll_ReturnsAttachmentFromRepo() {
        doAnswer(inv -> List.of(new IaasRole((String) inv.getArgument(0, Set.class).iterator().next()))).when(mIaasRepo).get(anySet());

        List<IaasRole> attachments = service.getAll(Set.of("TENANT"));

        List<IaasRole> expected = List.of(new IaasRole("TENANT"));
        assertEquals(expected, attachments);
    }

    @Test
    public void testGetByIds_ReturnAttachmentsFromRepo() {
        doAnswer(inv -> List.of(new IaasRole((String) inv.getArgument(0, Set.class).iterator().next()))).when(mIaasRepo).get(anySet());

        List<IaasRole> attachments = service.getByIds(Set.of(() -> "TENANT"));

        List<IaasRole> expected = List.of(new IaasRole("TENANT"));

        assertEquals(expected, attachments);
    }

    @Test
    public void testGetByAccessorIds_ReturnsAttachmentFromRepo() {
        doAnswer(inv -> List.of(new IaasRole((String) inv.getArgument(0, Set.class).iterator().next()))).when(mIaasRepo).get(anySet());

        IaasRoleAccessor accessor = new IaasRoleAccessor() {
            @Override
            public void setIaasRole(IaasRole attachment) {
            }
            @Override
            public IaasRole getIaasRole() {
                return new IaasRole("TENANT");
            }
        };
        List<IaasRole> attachments = service.getByAccessorIds(Set.of(accessor));

        List<IaasRole> expected = List.of(new IaasRole("TENANT"));
        assertEquals(expected, attachments);
    }

    @Test
    public void testAdd_ReturnsAddedRepoEntities_AfterSavingAddEntitiesFromUpdateService() {
        doAnswer(inv -> inv.getArgument(0, List.class)).when(mIaasRepo).add(anyList());

        List<BaseIaasRole> additions = List.of(
            new IaasRole("ROLE_1", "DESCRIPION_1", "DOCUMENT_1", "IAAS_RES_NAME_1", "IAAS_ID_1", LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2001, 1, 1, 0, 0), LocalDateTime.of(2002, 1, 1, 0, 0)),
            new IaasRole("ROLE_2", "DESCRIPION_2", "DOCUMENT_2", "IAAS_RES_NAME_2", "IAAS_ID_2", LocalDateTime.of(2000, 2, 1, 0, 0), LocalDateTime.of(2001, 2, 1, 0, 0), LocalDateTime.of(2002, 2, 1, 0, 0))
        );

        List<IaasRole> attachments = service.add(additions);

        List<IaasRole> expected = List.of(
            new IaasRole("ROLE_1", "DESCRIPION_1", "DOCUMENT_1", "IAAS_RES_NAME_1", "IAAS_ID_1", LocalDateTime.of(2000, 1, 1, 0, 0), null, null),
            new IaasRole("ROLE_2", "DESCRIPION_2", "DOCUMENT_2", "IAAS_RES_NAME_2", "IAAS_ID_2", LocalDateTime.of(2000, 2, 1, 0, 0), null, null)
        );

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

        List<UpdateIaasRole> updates = List.of(
            new IaasRole("ROLE_1", "DESCRIPION_1", "DOCUMENT_1", "IAAS_RES_NAME_1", "IAAS_ID_1", LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2001, 1, 1, 0, 0), LocalDateTime.of(2002, 1, 1, 0, 0)),
            new IaasRole("ROLE_2", "DESCRIPION_2", "DOCUMENT_2", "IAAS_RES_NAME_2", "IAAS_ID_2", LocalDateTime.of(2000, 2, 1, 0, 0), LocalDateTime.of(2001, 2, 1, 0, 0), LocalDateTime.of(2002, 2, 1, 0, 0))
        );

        List<IaasRole> attachments = service.put(updates);

        List<IaasRole> expected = List.of(
            new IaasRole("ROLE_1", "DESCRIPION_1", "DOCUMENT_1", "IAAS_RES_NAME_1", "IAAS_ID_1", LocalDateTime.of(2000, 1, 1, 0, 0), null, null),
            new IaasRole("ROLE_2", "DESCRIPION_2", "DOCUMENT_2", "IAAS_RES_NAME_2", "IAAS_ID_2", LocalDateTime.of(2000, 2, 1, 0, 0), null, null)
        );

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

        doAnswer(inv -> {
            Iterator<String> it = inv.getArgument(0, Set.class).iterator();
            String id2 = it.next();
            String id1 = it.next();

            return List.of(
                new IaasRole("ROLE_1", "DESCRIPION_1", "DOCUMENT_1", "IAAS_RES_NAME_1", "IAAS_ID_1", LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2001, 1, 1, 0, 0), LocalDateTime.of(2002, 1, 1, 0, 0)),
                new IaasRole("ROLE_2", "DESCRIPION_2", "DOCUMENT_2", "IAAS_RES_NAME_2", "IAAS_ID_2", LocalDateTime.of(2000, 2, 1, 0, 0), LocalDateTime.of(2001, 2, 1, 0, 0), LocalDateTime.of(2002, 2, 1, 0, 0))
            );
        }).when(mIaasRepo).get(anySet());

        List<UpdateIaasRole> updates = List.of(
                new IaasRole("ROLE_1", null, "DOCUMENT_1_UPDATED", null, null, LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2001, 1, 1, 0, 0), LocalDateTime.of(2002, 1, 1, 0, 0)),
                new IaasRole("ROLE_2", "DESCRIPION_2_UPDATED", null, null, "IAAS_ID_2_UPDATED", LocalDateTime.of(2000, 2, 1, 0, 0), LocalDateTime.of(2001, 2, 1, 0, 0), LocalDateTime.of(2002, 2, 1, 0, 0))
        );

        List<IaasRole> attachments = service.patch(updates);

        List<IaasRole> expected = List.of(
            new IaasRole("ROLE_1", "DESCRIPION_1", "DOCUMENT_1_UPDATED", "IAAS_RES_NAME_1", "IAAS_ID_1", LocalDateTime.of(2000, 1, 1, 0, 0), null, LocalDateTime.of(2002, 1, 1, 0, 0)),
            new IaasRole("ROLE_2", "DESCRIPION_2_UPDATED", "DOCUMENT_2", "IAAS_RES_NAME_2", "IAAS_ID_2_UPDATED", LocalDateTime.of(2000, 2, 1, 0, 0), null, LocalDateTime.of(2002, 2, 1, 0, 0))
        );

        assertEquals(expected, attachments);
        verify(mIaasRepo, times(1)).put(attachments);
        verify(mUpdateService).getPatchEntities(anyList(), eq(updates));
    }

    @Test
    public void testPatch_ReturnsNull_WhenArgIsNull() {
        assertNull(service.patch(null));
    }
}
