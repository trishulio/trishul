package io.trishul.tenant.service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;

import io.trishul.crud.service.LockService;

public class TenantServiceTest {
    private TenantService service;

    private Tenant mAdminTenant;
    private RepoService<UUID, Tenant, TenantAccessor> mRepoService;
    private TenantRepository mTenantRepo;
    private MigrationManager mMigrationMgr;
    private TenantIaasService mIaasService;
    private LockService mLockService;

    @BeforeEach
    public void init() {
        mAdminTenant = mock(Tenant.class);
        mTenantRepo = mock(TenantRepository.class);

        mMigrationMgr = mock(MigrationManager.class);
        mIaasService = mock(TenantIaasService.class);

        mRepoService = mock(RepoService.class);
        doAnswer(inv -> inv.getArgument(0)).when(mRepoService).saveAll(anyList());

        mLockService = mock(LockService.class);
        UpdateService<UUID, Tenant, BaseTenant, UpdateTenant> mUpdateService = new SimpleUpdateService<>(new MockUtilProvider(), mLockService, BaseTenant.class, UpdateTenant.class, Tenant.class, Set.of("createdAt"));

        this.service = new TenantService(mAdminTenant, mRepoService, mUpdateService, mTenantRepo, mMigrationMgr, mIaasService);
    }

    @Test
    public void testGetTenants_ReturnsEntitiesFromRepoService_WithCustomSpec() throws MalformedURLException {
        @SuppressWarnings("unchecked")
        final ArgumentCaptor<Specification<Tenant>> captor = ArgumentCaptor.forClass(Specification.class);
        final Page<Tenant> mPage = new PageImpl<>(List.of(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001"))));
        doReturn(mPage).when(this.mRepoService).getAll(captor.capture(), eq(new TreeSet<>(List.of("id"))), eq(true), eq(10), eq(20));

        final Page<Tenant> page = this.service.getAll(
            Set.of(UUID.fromString("00000000-0000-0000-0000-000000000001")), //ids
            Set.of("T1"),
            Set.of(new URL("http://localhost/")),
            true,
            new TreeSet<>(List.of("id")), //sort,
            true, //orderAscending,
            10, //page,
            20 //size
        );

        final Page<Tenant> expected = new PageImpl<>(List.of(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001"))));
        assertEquals(expected, page);

        // TODO: Pending testing for the specification
        captor.getValue();
    }

    @Test
    public void testGetTenant_ReturnsTenantPojo_WhenRepoServiceReturnsOptionalWithEntity() {
        doReturn(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001"))).when(this.mRepoService).get(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        final Tenant tenant = this.service.get(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        assertEquals(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")), tenant);
    }

    @Test
    public void testGetByIds_CallsRepoService() {
        ArgumentCaptor<List<? extends Identified<UUID>>> captor = ArgumentCaptor.forClass(List.class);

        doReturn(List.of(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")))).when(mRepoService).getByIds(captor.capture());

        assertEquals(List.of(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001"))), service.getByIds(List.of(() -> UUID.fromString("00000000-0000-0000-0000-000000000001"))));
        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000001"), captor.getValue().get(0).getId());
    }

    @Test
    public void testGetByAccessorIds_CallsRepoService() {
        ArgumentCaptor<Function<TenantAccessor, Tenant>> captor = ArgumentCaptor.forClass(Function.class);

        List<? extends TenantAccessor> accessors = List.of(new TenantAccessor() {
             @Override
             public void setTenant(Tenant tenant) {}
             @Override
             public Tenant getTenant() {
                 return new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001"));
             }
         });

        doReturn(List.of(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")))).when(mRepoService).getByAccessorIds(eq(accessors), captor.capture());

        assertEquals(List.of(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001"))), service.getByAccessorIds(accessors));
        assertEquals(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")), captor.getValue().apply(accessors.get(0)));
    }

    @Test
    public void testExists_ReturnsTrue_WhenRepoServiceReturnsTrue() {
        doReturn(true).when(this.mRepoService).exists(Set.of(UUID.fromString("00000000-0000-0000-0000-000000000001"), UUID.fromString("00000000-0000-0000-0000-000000000002"), UUID.fromString("00000000-0000-0000-0000-000000000003")));

        assertTrue(this.service.exists(Set.of(UUID.fromString("00000000-0000-0000-0000-000000000001"), UUID.fromString("00000000-0000-0000-0000-000000000002"), UUID.fromString("00000000-0000-0000-0000-000000000003"))));
    }

    @Test
    public void testExists_ReturnsFalse_WhenRepoServiceReturnsFalse() {
        doReturn(true).when(this.mRepoService).exists(Set.of(UUID.fromString("00000000-0000-0000-0000-000000000001"), UUID.fromString("00000000-0000-0000-0000-000000000002"), UUID.fromString("00000000-0000-0000-0000-000000000003")));

        assertTrue(this.service.exists(Set.of(UUID.fromString("00000000-0000-0000-0000-000000000001"), UUID.fromString("00000000-0000-0000-0000-000000000002"), UUID.fromString("00000000-0000-0000-0000-000000000003"))));
    }

    @Test
    public void testExist_ReturnsTrue_WhenRepoServiceReturnsTrue() {
        doReturn(true).when(this.mRepoService).exists(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        assertTrue(this.service.exist(UUID.fromString("00000000-0000-0000-0000-000000000001")));
    }

    @Test
    public void testExist_ReturnsFalse_WhenRepoServiceReturnsFalse() {
        doReturn(true).when(this.mRepoService).exists(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        assertTrue(this.service.exist(UUID.fromString("00000000-0000-0000-0000-000000000001")));
    }

    @Test
    public void testDelete_CallsRepoServiceDeleteBulk_WhenTenantExists() {
        doReturn(10L).when(this.mRepoService).delete(Set.of(UUID.fromString("00000000-0000-0000-0000-000000000001"), UUID.fromString("00000000-0000-0000-0000-000000000002"), UUID.fromString("00000000-0000-0000-0000-000000000003")));

        final long count = this.service.delete(Set.of(UUID.fromString("00000000-0000-0000-0000-000000000001"), UUID.fromString("00000000-0000-0000-0000-000000000002"), UUID.fromString("00000000-0000-0000-0000-000000000003")));
        assertEquals(10L, count);
    }

    @Test
    public void testDelete_CallsRepoServiceDelete_WhenTenantExists() {
        doAnswer(inv -> {
            Set<UUID> ids = inv.getArgument(0, Set.class);
            return ids.stream()
                      .map(Tenant::new)
                      .peek(tenant -> tenant.setIsReady(true))
                      .toList();
        }).when(mTenantRepo).findAllById(any());

        this.service.delete(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        Set<UUID> tenantIds = Set.of(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        verify(mIaasService, times(1)).delete(tenantIds);

        List<Tenant> expected = List.of(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));
        expected.get(0).setIsReady(false);
        verify(mRepoService, times(1)).saveAll(expected);
        verify(mRepoService).delete(Set.of(UUID.fromString("00000000-0000-0000-0000-000000000001")));
    }

    @Test
    public void testAdd_AddsTenantAndItemsAndSavesToRepo_WhenAdditionsAreNotNull() {
        final BaseTenant tenant1 = new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        final BaseTenant tenant2 = new Tenant();

        final List<Tenant> added = this.service.add(List.of(tenant1, tenant2));

        final List<Tenant> expected = List.of(
            new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")), new Tenant()
        );
        expected.forEach(tenant -> tenant.setIsReady(true));

        assertEquals(expected, added);
        verify(this.mRepoService, times(2)).saveAll(added);
        verify(mIaasService, times(1)).put(added);
    }

    @Test
    public void testAdd_DoesNotCallRepoServiceAndReturnsNull_WhenAdditionsAreNull() {
        assertNull(this.service.add(null));
        verify(this.mRepoService, times(0)).saveAll(any());
    }

    @Test
    public void testPut_UpdatesTenantAndItemsAndSavesToRepo_WhenUpdatesAreNotNull() {
        final UpdateTenant tenant1 = new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        final UpdateTenant tenant2 = new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000002"));

        doReturn(List.of(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")), new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000002")))).when(this.mRepoService).getByIds(List.of(tenant1, tenant2));

        final List<Tenant> updated = this.service.put(List.of(tenant1, tenant2, new Tenant()));

        final List<Tenant> expected = List.of(
            new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")), new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000002")), new Tenant()
        );
        expected.forEach(tenant -> tenant.setIsReady(true));

        assertEquals(expected, updated);
        verify(this.mRepoService, times(2)).saveAll(updated);
        verify(mIaasService, times(1)).put(updated);
    }

    @Test
    public void testPut_DoesNotCallRepoServiceAndReturnsNull_WhenUpdatesAreNull() {
        assertNull(this.service.put(null));
        verify(this.mRepoService, times(0)).saveAll(any());
    }

    @Test
    public void testPatch_PatchesTenantAndItemsAndSavesToRepo_WhenPatchesAreNotNull() {
        final UpdateTenant tenant1 = new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        final UpdateTenant tenant2 = new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000002"));

        doReturn(List.of(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")), new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000002")))).when(this.mRepoService).getByIds(List.of(tenant1, tenant2));

        final List<Tenant> updated = this.service.patch(List.of(tenant1, tenant2));

        final List<Tenant> expected = List.of(
            new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")), new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000002"))
        );
        expected.forEach(tenant -> tenant.setIsReady(true));

        assertEquals(expected, updated);
        verify(this.mRepoService, times(2)).saveAll(updated);
        verify(mIaasService, times(1)).put(updated);
    }

    @Test
    public void testPatch_DoesNotCallRepoServiceAndReturnsNull_WhenPatchesAreNull() {
        assertNull(this.service.patch(null));
        verify(this.mRepoService, times(0)).saveAll(any());
    }

    @Test
    public void testPatch_ThrowsNotFoundException_WhenAllTenantsDontExist() {
        final List<UpdateTenant> updates = List.of(
            new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")), new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000002")), new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000003")), new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000004"))
        );
        doReturn(List.of(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")), new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000002")))).when(this.mRepoService).getByIds(updates);

        assertThrows(EntityNotFoundException.class, () -> this.service.patch(updates), "Cannot find tenants with Ids: [3, 4]");
    }
}
