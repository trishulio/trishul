package io.trishul.user.service.user.service.role.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import io.trishul.base.types.base.pojo.Identified;
import io.trishul.crud.service.UpdateService;
import io.trishul.model.base.exception.EntityNotFoundException;
import io.trishul.repo.jpa.repository.service.RepoService;
import io.trishul.user.role.model.BaseUserRole;
import io.trishul.user.role.model.UpdateUserRole;
import io.trishul.user.role.model.UserRole;
import io.trishul.user.role.model.UserRoleAccessor;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;

public class UserRoleServiceTest {
    private UserRoleService service;

    private UpdateService<Long, UserRole, BaseUserRole, UpdateUserRole> mUpdateService;
    private RepoService<Long, UserRole, UserRoleAccessor> mRepoService;

    @BeforeEach
    public void init() {
        interface UserRoleUpdateService
                extends UpdateService<Long, UserRole, BaseUserRole, UpdateUserRole> {}
        ;
        interface UserRoleRepoService extends RepoService<Long, UserRole, UserRoleAccessor> {}
        ;
        this.mUpdateService = mock(UserRoleUpdateService.class);
        this.mRepoService = mock(UserRoleRepoService.class);
        doAnswer(inv -> inv.getArgument(0)).when(this.mRepoService).saveAll(anyList());

        this.service = new UserRoleService(mUpdateService, mRepoService);
    }

    @Test
    public void testGetUserRoles_ReturnsEntitiesFromRepoService_WithCustomSpec() {
        @SuppressWarnings("unchecked")
        final ArgumentCaptor<Specification<UserRole>> captor =
                ArgumentCaptor.forClass(Specification.class);
        final Page<UserRole> mPage = new PageImpl<>(List.of(new UserRole(1L)));
        doReturn(mPage)
                .when(this.mRepoService)
                .getAll(
                        captor.capture(),
                        eq(new TreeSet<>(List.of("userRolename"))),
                        eq(true),
                        eq(1),
                        eq(100));

        final Page<UserRole> page =
                this.service.getUserRoles(
                        Set.of(1L),
                        Set.of(2L),
                        Set.of("name"),
                        new TreeSet<>(List.of("userRolename")),
                        true,
                        1,
                        100);

        final Page<UserRole> expected = new PageImpl<>(List.of(new UserRole(1L)));
        assertEquals(expected, page);

        // TODO: Pending testing for the specification
        captor.getValue();
    }

    @Test
    public void testGetUserRole_ReturnsUserRolePojo_WhenRepoServiceReturnsOptionalWithEntity() {
        doReturn(new UserRole(1L)).when(this.mRepoService).get(1L);

        final UserRole userRole = this.service.get(1L);

        assertEquals(new UserRole(1L), userRole);
    }

    @Test
    public void testGetByIds_CallsRepoService() {
        interface IdentifiedList extends List<Identified<Long>> {}
        ;
        ArgumentCaptor<List<? extends Identified<Long>>> captor =
                ArgumentCaptor.forClass(IdentifiedList.class);

        doReturn(List.of(new UserRole(1L))).when(mRepoService).getByIds(captor.capture());

        assertEquals(List.of(new UserRole(1L)), service.getByIds(List.of(() -> 1L)));
        assertEquals(1L, captor.getValue().get(0).getId());
    }

    @Test
    public void testGetByAccessorIds_CallsRepoService() {
        interface UserRoleAccessorToUserRoleFunction extends Function<UserRoleAccessor, UserRole> {}
        ;
        ArgumentCaptor<Function<UserRoleAccessor, UserRole>> captor =
                ArgumentCaptor.forClass(UserRoleAccessorToUserRoleFunction.class);

        List<? extends UserRoleAccessor> accessors =
                List.of(
                        new UserRoleAccessor() {
                            @Override
                            public void setRole(UserRole userRole) {}

                            @Override
                            public UserRole getRole() {
                                return new UserRole(1L);
                            }
                        });

        doReturn(List.of(new UserRole(1L)))
                .when(mRepoService)
                .getByAccessorIds(eq(accessors), captor.capture());

        assertEquals(List.of(new UserRole(1L)), service.getByAccessorIds(accessors));
        assertEquals(new UserRole(1L), captor.getValue().apply(accessors.get(0)));
    }

    @Test
    public void testExists_ReturnsTrue_WhenRepoServiceReturnsTrue() {
        doReturn(true).when(this.mRepoService).exists(Set.of(1L, 2L, 3L));

        assertTrue(this.service.exists(Set.of(1L, 2L, 3L)));
    }

    @Test
    public void testExists_ReturnsFalse_WhenRepoServiceReturnsFalse() {
        doReturn(true).when(this.mRepoService).exists(Set.of(1L, 2L, 3L));

        assertTrue(this.service.exists(Set.of(1L, 2L, 3L)));
    }

    @Test
    public void testExist_ReturnsTrue_WhenRepoServiceReturnsTrue() {
        doReturn(true).when(this.mRepoService).exists(1L);

        assertTrue(this.service.exist(1L));
    }

    @Test
    public void testExist_ReturnsFalse_WhenRepoServiceReturnsFalse() {
        doReturn(true).when(this.mRepoService).exists(1L);

        assertTrue(this.service.exist(1L));
    }

    @Test
    public void testDelete_CallsRepoServiceDeleteBulk_WhenUserRoleExists() {
        doReturn(123L).when(this.mRepoService).delete(Set.of(1L, 2L, 3L));

        final long count = this.service.delete(Set.of(1L, 2L, 3L));
        assertEquals(123L, count);
    }

    @Test
    public void testDelete_CallsRepoServiceDelete_WhenUserRoleExists() {
        this.service.delete(1L);
        verify(this.mRepoService).delete(1L);
    }

    @Test
    public void testAdd_AddsUserRoleAndItemsAndSavesToRepo_WhenAdditionsAreNotNull() {
        doAnswer(inv -> inv.getArgument(0)).when(this.mUpdateService).getAddEntities(any());

        final BaseUserRole userRole1 = new UserRole(1L);
        final BaseUserRole userRole2 = new UserRole();

        final List<UserRole> added = this.service.add(List.of(userRole1, userRole2));

        final List<UserRole> expected = List.of(new UserRole(1L), new UserRole());

        assertEquals(expected, added);
        verify(this.mRepoService, times(1)).saveAll(added);
    }

    @Test
    public void testAdd_DoesNotCallRepoServiceAndReturnsNull_WhenAdditionsAreNull() {
        assertNull(this.service.add(null));
        verify(this.mRepoService, times(0)).saveAll(any());
    }

    @Test
    public void testPut_UpdatesUserRoleAndItemsAndSavesToRepo_WhenUpdatesAreNotNull() {
        doAnswer(inv -> inv.getArgument(1)).when(this.mUpdateService).getPutEntities(any(), any());

        final UpdateUserRole userRole1 = new UserRole(1L);
        final UpdateUserRole userRole2 = new UserRole(2L);

        doReturn(List.of(new UserRole(1L), new UserRole(2L)))
                .when(this.mRepoService)
                .getByIds(List.of(userRole1, userRole2));

        final List<UserRole> updated =
                this.service.put(List.of(userRole1, userRole2, new UserRole()));

        final List<UserRole> expected = List.of(new UserRole(1L), new UserRole(2L), new UserRole());

        assertEquals(expected, updated);
        verify(this.mRepoService, times(1)).saveAll(updated);
    }

    @Test
    public void testPut_DoesNotCallRepoServiceAndReturnsNull_WhenUpdatesAreNull() {
        assertNull(this.service.put(null));
        verify(this.mRepoService, times(0)).saveAll(any());
    }

    @Test
    public void testPatch_PatchesUserRoleAndItemsAndSavesToRepo_WhenPatchesAreNotNull() {
        doAnswer(inv -> inv.getArgument(1))
                .when(this.mUpdateService)
                .getPatchEntities(any(), any());

        final UpdateUserRole userRole1 = new UserRole(1L);
        final UpdateUserRole userRole2 = new UserRole(2L);

        doReturn(List.of(new UserRole(1L), new UserRole(2L)))
                .when(this.mRepoService)
                .getByIds(List.of(userRole1, userRole2));

        final List<UserRole> updated = this.service.patch(List.of(userRole1, userRole2));

        final List<UserRole> expected = List.of(new UserRole(1L), new UserRole(2L));

        assertEquals(expected, updated);
        verify(this.mRepoService, times(1)).saveAll(updated);
    }

    @Test
    public void testPatch_DoesNotCallRepoServiceAndReturnsNull_WhenPatchesAreNull() {
        assertNull(this.service.patch(null));
        verify(this.mRepoService, times(0)).saveAll(any());
    }

    @Test
    public void testPatch_ThrowsNotFoundException_WhenAllUserRolesDontExist() {
        doAnswer(inv -> inv.getArgument(1))
                .when(this.mUpdateService)
                .getPatchEntities(any(), any());

        final List<UpdateUserRole> updates =
                List.of(new UserRole(1L), new UserRole(2L), new UserRole(3L), new UserRole(4L));
        doReturn(List.of(new UserRole(1L), new UserRole(2L)))
                .when(this.mRepoService)
                .getByIds(updates);

        assertThrows(
                EntityNotFoundException.class,
                () -> this.service.patch(updates),
                "Cannot find userRoles with Ids: [3, 4]");
    }
}
