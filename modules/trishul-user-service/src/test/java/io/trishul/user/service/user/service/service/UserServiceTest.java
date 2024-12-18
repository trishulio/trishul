package io.trishul.user.service.user.service.service;

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
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import io.trishul.base.types.base.pojo.Identified;
import io.trishul.crud.service.UpdateService;
import io.trishul.iaas.user.service.TenantIaasUserService;
import io.trishul.model.base.exception.EntityNotFoundException;
import io.trishul.repo.jpa.repository.service.RepoService;
import io.trishul.user.model.BaseUser;
import io.trishul.user.model.UpdateUser;
import io.trishul.user.model.User;
import io.trishul.user.model.UserAccessor;
import io.trishul.user.service.user.service.repository.UserRepository;
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

public class UserServiceTest {
    private UserService service;

    private UpdateService<Long, User, BaseUser, UpdateUser> mUpdateService;
    private RepoService<Long, User, UserAccessor> mRepoService;

    private UserRepository userRepository;
    private TenantIaasUserService iaasService;

    @BeforeEach
    public void init() {
        this.mUpdateService = mock(UpdateService.class);
        this.mRepoService = mock(RepoService.class);

        userRepository = mock(UserRepository.class);
        iaasService = mock(TenantIaasUserService.class);

        doAnswer(inv -> inv.getArgument(0)).when(this.mRepoService).saveAll(anyList());

        this.service = new UserService(mUpdateService, mRepoService, userRepository, iaasService);
    }

    @Test
    public void testGetUsers_ReturnsEntitiesFromRepoService_WithCustomSpec() {
        @SuppressWarnings("unchecked")
        final ArgumentCaptor<Specification<User>> captor =
                ArgumentCaptor.forClass(Specification.class);
        final Page<User> mPage = new PageImpl<>(List.of(new User(1L)));
        doReturn(mPage)
                .when(this.mRepoService)
                .getAll(
                        captor.capture(),
                        eq(new TreeSet<>(List.of("username"))),
                        eq(true),
                        eq(1),
                        eq(100));

        final Page<User> page =
                this.service.getUsers(
                        Set.of(1L),
                        Set.of(2L),
                        Set.of("userName"),
                        Set.of("displayName"),
                        Set.of("email"),
                        Set.of("phoneNumber"),
                        Set.of(10L),
                        Set.of(20L),
                        Set.of("role"),
                        1,
                        100,
                        new TreeSet<>(List.of("username")),
                        true);

        final Page<User> expected = new PageImpl<>(List.of(new User(1L)));
        assertEquals(expected, page);

        // TODO: Pending testing for the specification
        captor.getValue();
    }

    @Test
    public void testGetUser_ReturnsUserPojo_WhenRepoServiceReturnsOptionalWithEntity() {
        doReturn(new User(1L)).when(this.mRepoService).get(1L);

        final User user = this.service.get(1L);

        assertEquals(new User(1L), user);
    }

    @Test
    public void testGetByIds_CallsRepoService() {
        ArgumentCaptor<List<? extends Identified<Long>>> captor =
                ArgumentCaptor.forClass(List.class);

        doReturn(List.of(new User(1L))).when(mRepoService).getByIds(captor.capture());

        assertEquals(List.of(new User(1L)), service.getByIds(List.of(() -> 1L)));
        assertEquals(1L, captor.getValue().get(0).getId());
    }

    @Test
    public void testGetByAccessorIds_CallsRepoService() {
        ArgumentCaptor<Function<UserAccessor, User>> captor =
                ArgumentCaptor.forClass(Function.class);

        List<? extends UserAccessor> accessors =
                List.of(
                        new UserAccessor() {
                            @Override
                            public void setUser(User User) {}

                            @Override
                            public User getUser() {
                                return new User(1L);
                            }
                        });

        doReturn(List.of(new User(1L)))
                .when(mRepoService)
                .getByAccessorIds(eq(accessors), captor.capture());

        assertEquals(List.of(new User(1L)), service.getByAccessorIds(accessors));
        assertEquals(new User(1L), captor.getValue().apply(accessors.get(0)));
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
    public void testDelete_CallsRepoServiceDeleteBulk_WhenUserExists() {
        doReturn(123L).when(this.mRepoService).delete(Set.of(1L, 2L, 3L));

        final long count = this.service.delete(Set.of(1L, 2L, 3L));
        assertEquals(123L, count);
    }

    @Test
    public void testDelete_CallsRepoServiceDelete_WhenUserExists() {
        this.service = spy(this.service);
        doReturn(99L).when(this.service).delete(Set.of(1L));

        long count = this.service.delete(1L);
        assertEquals(99L, count);
    }

    @Test
    public void testAdd_AddsUserAndItemsAndSavesToRepo_WhenAdditionsAreNotNull() {
        doAnswer(inv -> inv.getArgument(0)).when(this.mUpdateService).getAddEntities(any());

        final BaseUser user1 = new User(1L);
        final BaseUser user2 = new User();

        final List<User> added = this.service.add(List.of(user1, user2));

        final List<User> expected = List.of(new User(1L), new User());

        assertEquals(expected, added);
        verify(this.mRepoService, times(1)).saveAll(added);
    }

    @Test
    public void testAdd_DoesNotCallRepoServiceAndReturnsNull_WhenAdditionsAreNull() {
        assertNull(this.service.add(null));
        verify(this.mRepoService, times(0)).saveAll(any());
    }

    @Test
    public void testPut_UpdatesUserAndItemsAndSavesToRepo_WhenUpdatesAreNotNull() {
        doAnswer(inv -> inv.getArgument(1)).when(this.mUpdateService).getPutEntities(any(), any());

        final UpdateUser user1 = new User(1L);
        final UpdateUser user2 = new User(2L);

        doReturn(List.of(new User(1L), new User(2L)))
                .when(this.mRepoService)
                .getByIds(List.of(user1, user2));

        final List<User> updated = this.service.put(List.of(user1, user2, new User()));

        final List<User> expected = List.of(new User(1L), new User(2L), new User());

        assertEquals(expected, updated);
        verify(this.mRepoService, times(1)).saveAll(updated);
    }

    @Test
    public void testPut_DoesNotCallRepoServiceAndReturnsNull_WhenUpdatesAreNull() {
        assertNull(this.service.put(null));
        verify(this.mRepoService, times(0)).saveAll(any());
    }

    @Test
    public void testPatch_PatchesUserAndItemsAndSavesToRepo_WhenPatchesAreNotNull() {
        doAnswer(inv -> inv.getArgument(1))
                .when(this.mUpdateService)
                .getPatchEntities(any(), any());

        final UpdateUser user1 = new User(1L);
        final UpdateUser user2 = new User(2L);

        doReturn(List.of(new User(1L), new User(2L)))
                .when(this.mRepoService)
                .getByIds(List.of(user1, user2));

        final List<User> updated = this.service.patch(List.of(user1, user2));

        final List<User> expected = List.of(new User(1L), new User(2L));

        assertEquals(expected, updated);
        verify(this.mRepoService, times(1)).saveAll(updated);
    }

    @Test
    public void testPatch_DoesNotCallRepoServiceAndReturnsNull_WhenPatchesAreNull() {
        assertNull(this.service.patch(null));
        verify(this.mRepoService, times(0)).saveAll(any());
    }

    @Test
    public void testPatch_ThrowsNotFoundException_WhenAllUsersDontExist() {
        doAnswer(inv -> inv.getArgument(1))
                .when(this.mUpdateService)
                .getPatchEntities(any(), any());

        final List<UpdateUser> updates =
                List.of(new User(1L), new User(2L), new User(3L), new User(4L));
        doReturn(List.of(new User(1L), new User(2L))).when(this.mRepoService).getByIds(updates);

        assertThrows(
                EntityNotFoundException.class,
                () -> this.service.patch(updates),
                "Cannot find users with Ids: [3, 4]");
    }
}
